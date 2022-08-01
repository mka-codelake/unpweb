package de.codelake.unpweb.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.codelake.unpweb.domain.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	List<Person> findAllBySupervisor(Person personToDelete);

}
