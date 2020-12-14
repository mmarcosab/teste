package br.com.pessoas.gateway.database.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "controle")
public class Controle {

    @EmbeddedId
    private ControleId controle;

    @Column(name = "cod_situ_psst")
    private int codSituPsst;

    @Column(name = "dat_hor_atui")
    private Date datetime;

}
