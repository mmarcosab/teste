package br.com.pessoas.usecase.impl;

import br.com.pessoas.gateway.PersonGateway;
import br.com.pessoas.gateway.database.data.Pedido;
import br.com.pessoas.gateway.database.data.PersonData;
import br.com.pessoas.gateway.database.repository.PedidoRepository;
import br.com.pessoas.usecase.UseCasePrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class UseCasePrincipalImpl implements UseCasePrincipal {

    private final PersonGateway personGateway;
    private final PedidoRepository pedidoRepository;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void execute(){

//        Stream<PersonData> persons = personGateway.getPersons();
//        persons.forEach(person -> {
//            log.info(person.getName());
//            PersonData p = new PersonData();
//            p.setAge(10);
//            p.setCpf("11771111111");
//            p.setName("Testeeeeeeeee");
//            personGateway.save(p);
//        });


        Stream<Pedido> pedidos = pedidoRepository.getPedidos();

    }

}
