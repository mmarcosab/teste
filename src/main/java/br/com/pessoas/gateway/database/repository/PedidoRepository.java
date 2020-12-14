package br.com.pessoas.gateway.database.repository;

import br.com.pessoas.gateway.database.data.Pedido;
import br.com.pessoas.gateway.database.data.PedidoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.stream.Stream;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, PedidoId> {

//    @Query(value = "SELECT  " +
//            "  pedido.num_idef_rgto_oper, " +
//            "  pedido.num_idef_empt_oril, " +
//            "        pedido.cod_unic_crrl_even " +
//            "FROM  " +
//            "  pedido pedido " +
//            "   INNER JOIN ( " +
//            "      SELECT  " +
//            "        controle.num_idef_empt_oril, " +
//            "        controle.cod_unic_crrl_even, " +
//            "        controle.cod_situ_psst " +
//            "      FROM controle controle " +
//            "                                INNER JOIN ( " +
//            "           SELECT  " +
//            "             ctrl.num_idef_empt_oril, " +
//            "             ctrl.cod_unic_crrl_even, " +
//            "             max(ctrl.dat_hor_atui) dat_hor_atui " +
//            "           FROM  " +
//            "             controle ctrl " +
//            "           GROUP BY  " +
//            "             ctrl.num_idef_empt_oril, " +
//            "             ctrl.cod_unic_crrl_even " +
//            "           ) hst " +
//            "        ON  controle.num_idef_empt_oril = hst.num_idef_empt_oril " +
//            "        AND controle.cod_unic_crrl_even = hst.cod_unic_crrl_even " +
//            "        AND controle.dat_hor_atui = hst.dat_hor_atui " +
//            "      ) contr " +
//            "      ON  pedido.num_idef_empt_oril = contr.num_idef_empt_oril " +
//            "      AND pedido.cod_unic_crrl_even = contr.cod_unic_crrl_even " +
//            "WHERE  " +
//            " contr.cod_situ_psst = 1; ", nativeQuery = true)
//    Stream<Pedido> getPedidos();


    @Modifying
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query(value = " select pedido.num_idef_rgto_oper, pedido.num_idef_empt_oril, pedido.cod_unic_crrl_even " +
            " from pedido " +
            " where exists ( " +
            " select controle.num_idef_empt_oril, controle.cod_unic_crrl_even, controle.cod_situ_psst " +
            " from controle " +
            " where exists ( " +
            " select ctrl.num_idef_empt_oril, ctrl.cod_unic_crrl_even, max(ctrl.dat_hor_atui) dat_hor_atui " +
            " from controle ctrl " +
            " where controle.num_idef_empt_oril = ctrl.num_idef_empt_oril " +
            " and controle.cod_unic_crrl_even = ctrl.cod_unic_crrl_even " +
            " and controle.dat_hor_atui = ctrl.dat_hor_atui " +
            " group by ctrl.num_idef_empt_oril, ctrl.cod_unic_crrl_even " +
            " ) " +
            " where pedido.num_idef_empt_oril = controle.num_idef_empt_oril " +
            " and pedido.cod_unic_crrl_even = controle.cod_unic_crrl_even " +
            " and controle.cod_situ_psst=1 " +
            ") ")
    Stream<Pedido> getPedidos();

}