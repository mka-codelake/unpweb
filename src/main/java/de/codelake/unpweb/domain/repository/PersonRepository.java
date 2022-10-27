package de.codelake.unpweb.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.codelake.unpweb.domain.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	List<Person> findAllBySupervisor(Person personToDelete);

}
