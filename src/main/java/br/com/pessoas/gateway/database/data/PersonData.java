package br.com.pessoas.gateway.database.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "person")
public class PersonData {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private int age;

    private String cpf;

    @Version
    private int controle;

}