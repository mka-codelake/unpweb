package de.codelake.unpweb.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.codelake.unpweb.domain.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {

}
