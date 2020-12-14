package br.com.pessoas.gateway;

import br.com.pessoas.gateway.database.data.PersonData;

import java.util.stream.Stream;

public interface PersonGateway {
   Stream<PersonData> getPersons();
   void save(PersonData personData);
}
