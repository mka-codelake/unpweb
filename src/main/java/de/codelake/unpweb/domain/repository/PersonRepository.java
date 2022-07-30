package de.codelake.unpweb.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.codelake.unpweb.domain.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}