package br.com.pessoas.gateway.database.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {

    @EmbeddedId
    private PedidoId controle;

    @Column(name = "vlr_limi_sald_deve")
    private BigDecimal valorLimiteSaldoDevedor;

    @Column(name = "vlr_gara_neoc_recb")
    private BigDecimal valorGarantiaNegociacaoRecebivel;

    @Version
    @Column(name = "num_ctrl_exeo_tare")
    private int numeroControleExecucaoTarefa;

}
