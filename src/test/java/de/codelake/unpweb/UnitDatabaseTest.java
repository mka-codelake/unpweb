package de.codelake.unpweb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.codelake.unpweb.domain.model.Unit;
import de.codelake.unpweb.domain.repository.UnitRepository;

@DataJpaTest
public class UnitDatabaseTest {

	@Autowired
	private UnitRepository unitRepo;

	@Test
	public void testCountUnitTable() {
		assertThat(unitRepo.count()).isEqualTo(8);
	}

	@Test
	public void testReadUnit() {
		final Unit unit = unitRepo.getReferenceById(2l);
		assertThat(unit).isNotNull();
		assertThat(unit.getParentUnit()).isNotNull();
		assertThat(unit.getAbbreviation()).isEqualTo("MA");
		assertThat(unit.getName()).isEqualTo("Management & Administration");
		assertThat(unit.getDirector()).isNotNull();
	}

	@Test
	public void testNoPartentUnit() {
		final Unit unit = unitRepo.getReferenceById(1l);
		assertThat(unit).isNotNull();
		assertThat(unit.getParentUnit()).isNull();
	}

	@Test
	public void testNoDirectorUnit() {
		final Unit unit = unitRepo.getReferenceById(5l);
		assertThat(unit).isNotNull();
		assertThat(unit.getDirector()).isNull();
	}
}
