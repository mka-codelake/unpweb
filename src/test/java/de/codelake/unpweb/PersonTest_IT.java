package de.codelake.unpweb;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.PersonSlimDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.dto.UnitSlimDto;
import de.codelake.unpweb.domain.model.Person;
import de.codelake.unpweb.domain.model.Unit;

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

	@Test
	@DisplayName("POST create new empty person")
	public void createEmptyPerson() {
		final HttpEntity<Void> request = new HttpEntity<>(null, headers);

		final ResponseEntity<PersonDto> response = template.postForEntity("/persons", request, PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		final PersonDto personDto = response.getBody();

		assertThat(personDto).isNotNull();
		assertThat(personDto.id()).isNotNull();
	}

	@Test
	@DisplayName("DELETE delete person without references - no integrity violation")
	public void deletePerson() {
		final HttpEntity<Void> request = new HttpEntity<>(null, headers);

		// create deletable Person
		ResponseEntity<PersonDto> response = template.postForEntity("/persons", request, PersonDto.class);

		final String personUrl = String.format("/persons/%d", response.getBody().id());

		// check for existence
		response = template.getForEntity(personUrl, PersonDto.class);
		assertThat(response.getBody()).isNotNull();

		// delete Person
		template.delete(personUrl);

		// check for not existence
		response = template.getForEntity(personUrl, PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DirtiesContext
	@Test
	@DisplayName("DELETE person")
	public void deletePersonWithReference() {
		final String endpoint = "/persons/1";
		// delete Unit
		template.delete(endpoint);

		// check for not existence
		final ResponseEntity<PersonDto> response = template.getForEntity(endpoint, PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = { "ABC" })
	@DisplayName("PUT update person attributes with new and empty value")
	public void updatePersonAttributes(final String value) {
		// *** This would handle the client somehow ***
		final String endpoint = "/persons/3";
		ResponseEntity<PersonDto> response = template.getForEntity(endpoint, PersonDto.class);
		PersonDto personDto = response.getBody();
		final Person person = mapper.personDtoToPerson(personDto);
		person.setName(value);
		person.setInitials(value);
		person.setRole(value);
		personDto = mapper.personToPersonDto(person);

		final HttpEntity<PersonDto> request = new HttpEntity<>(personDto, headers);

		// ***** Actual Test *****
		template.put(endpoint, request);

		response = template.getForEntity(endpoint, PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().name()).isNotNull();
		assertThat(response.getBody().name()).isEqualTo(value);
		assertThat(response.getBody().initials()).isNotNull();
		assertThat(response.getBody().initials()).isEqualTo(value);
		assertThat(response.getBody().role()).isNotNull();
		assertThat(response.getBody().role()).isEqualTo(value);
	}

	@Test
	@DisplayName("PUT update supervisor")
	public void updateSupervisor() {
		// *** This would handle the client somehow ***
		final Person newSupervisor = new Person();
		newSupervisor.setId(2l);
		final PersonDto newSupervisorDto = mapper.personToPersonDto(newSupervisor);

		final HttpEntity<PersonDto> request = new HttpEntity<>(newSupervisorDto, headers);

		// ***** Actual Test *****
		template.put("/persons/6/supervisor", request);

		final ResponseEntity<PersonDto> response = template.getForEntity("/persons/6", PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final PersonDto personDto = response.getBody();
		final PersonSlimDto supervisor = personDto.supervisor();
		assertThat(supervisor).isNotNull();
		assertThat(supervisor.id()).isEqualTo(2);
	}

	@Test
	@DisplayName("DELETE remove supervisor reference")
	public void removeSupervisor() {
		// ***** Actual Test *****
		template.delete("/persons/6/supervisor");

		final ResponseEntity<PersonDto> response = template.getForEntity("/persons/6", PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final PersonDto personDto = response.getBody();
		final PersonSlimDto supervisor = personDto.supervisor();
		assertThat(supervisor).isNull();
	}

	@Test
	@DisplayName("PUT update belongsTo")
	public void updateBelongsTo() {
		// *** This would handle the client somehow ***
		final Unit newBelongsTo = new Unit();
		newBelongsTo.setId(4l);
		final UnitDto newBelongsToDto = mapper.unitToUnitDto(newBelongsTo);

		final HttpEntity<UnitDto> request = new HttpEntity<>(newBelongsToDto, headers);

		// ***** Actual Test *****
		template.put("/persons/7/belongsto", request);

		final ResponseEntity<PersonDto> response = template.getForEntity("/persons/7", PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final PersonDto personDto = response.getBody();
		final UnitSlimDto belongsTo = personDto.belongsTo();
		assertThat(belongsTo).isNotNull();
		assertThat(belongsTo.id()).isEqualTo(4);
	}

	@Test
	@DisplayName("DELETE remove belongsTo reference")
	public void removeBelongsTo() {
		// ***** Actual Test *****
		template.delete("/persons/7/belongsto");

		final ResponseEntity<PersonDto> response = template.getForEntity("/persons/7", PersonDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final PersonDto personDto = response.getBody();
		final UnitSlimDto belongsTo = personDto.belongsTo();
		assertThat(belongsTo).isNull();
	}
}
