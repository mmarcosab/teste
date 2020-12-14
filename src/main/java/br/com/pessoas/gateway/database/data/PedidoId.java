package br.com.pessoas.gateway.database.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class PedidoId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "num_idef_rgto_oper")
    private String numIdefRgtoOper;

    @Column(name = "num_idef_empt_oril")
    private String numIdefEmptOril;

    @Column(name = "cod_unic_crrl_even")
    private String codUnicCrrlEven;

}
