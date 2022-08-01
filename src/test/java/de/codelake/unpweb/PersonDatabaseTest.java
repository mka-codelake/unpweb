package de.codelake.unpweb;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.codelake.unpweb.domain.model.Person;
import de.codelake.unpweb.domain.repository.PersonRepository;

@DataJpaTest
public class PersonDatabaseTest {

	@Autowired
	private PersonRepository personRepo;

	@Test
	public void testCountPersonTable() {
		assertThat(personRepo.count()).isEqualTo(26);
	}

	@Test
	public void testFindAllBySuperVisor() {
		final Person supervisor = personRepo.getReferenceById(1l);
		final List<Person> employees = personRepo.findAllBySupervisor(supervisor);

		assertThat(employees).isNotNull();
		assertThat(employees).hasSize(9);
	}
}
