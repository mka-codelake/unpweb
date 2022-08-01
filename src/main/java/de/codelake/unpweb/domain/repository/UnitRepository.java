package de.codelake.unpweb.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.codelake.unpweb.domain.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {

	List<Unit> findAllByParentUnit(Unit unit);

	List<Unit> findAllByDirectorId(Long personId);

}
