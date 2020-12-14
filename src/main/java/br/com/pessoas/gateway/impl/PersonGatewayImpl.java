package br.com.pessoas.gateway.impl;

import br.com.pessoas.gateway.PersonGateway;
import br.com.pessoas.gateway.database.data.PersonData;
import br.com.pessoas.gateway.database.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PersonGatewayImpl implements PersonGateway {

    private final PersonRepository personRepository;

    @Override
    public Stream<PersonData> getPersons() {
        return personRepository.getByAgeAfter30();
    }

    @Override
    public void save(PersonData personData) {
        personRepository.save(personData);
    }
}
