package de.codelake.unpweb;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.codelake.unpweb.domain.dto.PersonDto;

public class PersonTest_IT extends AbstractTest_IT {

	@Test
	@DisplayName("GET all persons")
	public void readAllPersons() {
		@SuppressWarnings("unchecked")
		final var response = template.getForEntity("/persons", (Class<List<PersonDto>>) (Class<?>) List.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).hasSize(26);
	}

	@Test
	@DisplayName("GET single person")
	public void readSinglePerson() {
		final ResponseEntity<PersonDto> response = template.getForEntity("/persons/2", PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		final PersonDto personDto = response.getBody();

		assertThat(personDto).isNotNull();
		assertThat(personDto.name()).isEqualTo("George Wolf");
		assertThat(personDto.initials()).isEqualTo("GW");
		assertThat(personDto.role()).isEqualTo("Assistent");
		assertThat(personDto.supervisor()).isNotNull();
		assertThat(personDto.supervisor().id()).isEqualTo(1);
		assertThat(personDto.belongsTo()).isNotNull();
		assertThat(personDto.belongsTo().id()).isEqualTo(1);
	}

}
