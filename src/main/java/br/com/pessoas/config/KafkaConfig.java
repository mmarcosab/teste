package br.com.pessoas.config;


import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.common.*;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaAdmin admin;
    private final MeterRegistry meterRegistry;

    private final Map<String, KafkaTemplate<?, ?>> kafkaTemplates;

    @Bean
    public AdminClient kafkaAdminClient() {
        return AdminClient.create(admin.getConfig());
    }

    @SuppressWarnings("deprecation") // Can be avoided by relying on Double.NaN for non doubles.
    @PostConstruct
    private void initMetrics() {
        final String kafkaPrefix = "kafka.";
        for (Map.Entry<String, KafkaTemplate<?, ?>> templateEntry : kafkaTemplates.entrySet()) {
            final String name = templateEntry.getKey();
            final KafkaTemplate<?, ?> kafkaTemplate = templateEntry.getValue();
            for (Metric metric : kafkaTemplate.metrics().values()) {
                final MetricName metricName = metric.metricName();
                final Gauge.Builder<Metric> gaugeBuilder = Gauge
                        .builder(kafkaPrefix + metricName.name(), metric, Metric::value) // <-- Here
                        .description(metricName.description());
                for (Map.Entry<String, String> tagEntry : metricName.tags().entrySet()) {
                    gaugeBuilder.tag(kafkaPrefix + tagEntry.getKey(), tagEntry.getValue());
                }
                gaugeBuilder.tag("bean", name);
                gaugeBuilder.register(meterRegistry);
            }
        }
    }

    @Bean
    public HealthIndicator kafkaHealthIndicator() {
        final DescribeClusterOptions describeClusterOptions = new DescribeClusterOptions().timeoutMs(1000);
        final AdminClient adminClient = kafkaAdminClient();
        return () -> {
            final DescribeClusterResult describeCluster = adminClient.describeCluster(describeClusterOptions);
            try {
                final String clusterId = describeCluster.clusterId().get();
                final int nodeCount = describeCluster.nodes().get().size();
                return Health.up()
                        .withDetail("clusterId", clusterId)
                        .withDetail("nodeCount", nodeCount)
                        .build();
            } catch (InterruptedException | ExecutionException e) {
                return Health.down()
                        .withException(e)
                        .build();
            }
        };

    }


}
