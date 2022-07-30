package de.codelake.unpweb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.codelake.unpweb.domain.repository.PersonRepository;

@DataJpaTest
public class PersonDatabaseTest {

	@Autowired
	private PersonRepository personRepo;

	@Test
	public void testCountPersonTable() {
		assertThat(personRepo.count()).isEqualTo(26);
	}
}
