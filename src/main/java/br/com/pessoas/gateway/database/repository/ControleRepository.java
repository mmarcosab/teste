package br.com.pessoas.gateway.database.repository;

import br.com.pessoas.gateway.database.data.PersonData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.stream.Stream;

@Repository
public interface ControleRepository extends JpaRepository<PersonData, Integer> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT p FROM PersonData p where p.age > 29")
    Stream<PersonData> getByAgeAfter30();

}