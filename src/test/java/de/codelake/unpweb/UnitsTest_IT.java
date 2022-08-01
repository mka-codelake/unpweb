package de.codelake.unpweb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

public class UnitsTest_IT extends AbstractTest_IT {

	@Test
	@DisplayName("GET all units")
	public void readAllUnits() {
		@SuppressWarnings("unchecked")
		final var response = template.getForEntity("/units",
				(Class<List<UnitDto>>) (Class<?>) List.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).hasSize(8);
	}

	@Test
	@DisplayName("GET single unit without parent unit")
	public void readSingleUnit_WithoutParentUnit() {
		final ResponseEntity<UnitDto> response = template.getForEntity("/units/1", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		final UnitDto unitDto = response.getBody();

		assertThat(unitDto).isNotNull();
		assertThat(unitDto.name()).isEqualTo("Example Corporation");
		assertThat(unitDto.abbreviation()).isEqualTo("EC");
		assertThat(unitDto.parentUnit()).isNull();
		assertThat(unitDto.director()).isNotNull();
		assertThat(unitDto.director().id()).isEqualTo(1);
		assertThat(unitDto.members()).hasSize(2);
	}

	@Test
	@DisplayName("GET single unit with parent unit")
	public void readSingleUnit_WithParentUnit() {
		final ResponseEntity<UnitDto> response = template.getForEntity("/units/2", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		final UnitDto unitDto = response.getBody();

		assertThat(unitDto).isNotNull();
		assertThat(unitDto.name()).isEqualTo("Management & Administration");
		assertThat(unitDto.abbreviation()).isEqualTo("MA");
		assertThat(unitDto.parentUnit()).isNotNull();
		assertThat(unitDto.parentUnit().id()).isEqualTo(1);
		assertThat(unitDto.director()).isNotNull();
		assertThat(unitDto.director().id()).isEqualTo(3);
		assertThat(unitDto.members()).hasSize(3);
	}

	@Test
	@DisplayName("GET single unit which does not exist")
	public void readSingleUnit_DoesNotExist() {
		final ResponseEntity<UnitDto> response = template.getForEntity("/units/2000", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("POST empty unit")
	public void createEmptyUnit() {
		final HttpEntity<Void> request = new HttpEntity<>(null, headers);

		final ResponseEntity<UnitDto> response = template.postForEntity("/units", request, UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().id()).isNotNull();
	}

	@Test
	@DisplayName("DELETE unit")
	public void deleteUnit() {
		final HttpEntity<Void> request = new HttpEntity<>(null, headers);

		// create deletable Unit
		ResponseEntity<UnitDto> response = template.postForEntity("/units", request, UnitDto.class);

		final String unitUrl = String.format("/units/%d", response.getBody().id());

		// check for existence
		response = template.getForEntity(unitUrl, UnitDto.class);
		assertThat(response.getBody()).isNotNull();

		// delete Unit
		template.delete(unitUrl);

		// check for not existence
		response = template.getForEntity(unitUrl, UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DirtiesContext
	@Test
	@DisplayName("DELETE unit")
	public void deleteUnitWithReference() {
		// delete Unit
		template.delete("/units/1");

		// check for not existence
		final ResponseEntity<UnitDto> response = template.getForEntity("/units/1", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("PUT update unit name")
	public void updateUnitName() {
		final String newUnitName = "NewUnitName";

		// *** This would handle the client somehow ***
		ResponseEntity<UnitDto> response = template.getForEntity("/units/2", UnitDto.class);
		UnitDto unitDto = response.getBody();
		final Unit unit = mapper.unitDtoToUnit(unitDto);
		unit.setName(newUnitName);
		unitDto = mapper.unitToUnitDto(unit);

		final HttpEntity<UnitDto> request = new HttpEntity<>(unitDto, headers);

		// ***** Actual Test *****
		template.put("/units/2", request);

		response = template.getForEntity("/units/2", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().name()).isNotNull();
		assertThat(response.getBody().name()).isEqualTo(newUnitName);
	}

	@Test
	@DisplayName("PUT update unit name with empty value")
	public void deleteUnitName() {
		final String newUnitName = "";

		// *** This would handle the client somehow ***
		ResponseEntity<UnitDto> response = template.getForEntity("/units/2", UnitDto.class);
		UnitDto unitDto = response.getBody();
		final Unit unit = mapper.unitDtoToUnit(unitDto);
		unit.setName(newUnitName);
		unitDto = mapper.unitToUnitDto(unit);

		final HttpEntity<UnitDto> request = new HttpEntity<>(unitDto, headers);

		// ***** Actual Test *****
		template.put("/units/2", request);

		response = template.getForEntity("/units/2", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().name()).isNotNull();
		assertThat(response.getBody().name()).isEqualTo(newUnitName);
	}

	@Test
	@DisplayName("PUT update unit abbreviation")
	public void updateUnitAbbreviation() {
		final String newUnitAbbr = "NewUnitAbbr";

		// *** This would handle the client somehow ***
		ResponseEntity<UnitDto> response = template.getForEntity("/units/3", UnitDto.class);
		UnitDto unitDto = response.getBody();
		final Unit unit = mapper.unitDtoToUnit(unitDto);
		unit.setAbbreviation(newUnitAbbr);
		unitDto = mapper.unitToUnitDto(unit);

		final HttpEntity<UnitDto> request = new HttpEntity<>(unitDto, headers);

		// ***** Actual Test *****
		template.put("/units/3", request);

		response = template.getForEntity("/units/3", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().abbreviation()).isNotNull();
		assertThat(response.getBody().abbreviation()).isEqualTo(newUnitAbbr);
	}

	@Test
	@DisplayName("PUT update unit abbreviation with empty value")
	public void deleteUnitAbbreviation() {
		final String newUnitAbbr = "";

		// *** This would handle the client somehow ***
		ResponseEntity<UnitDto> response = template.getForEntity("/units/3", UnitDto.class);
		UnitDto unitDto = response.getBody();
		final Unit unit = mapper.unitDtoToUnit(unitDto);
		unit.setAbbreviation(newUnitAbbr);
		unitDto = mapper.unitToUnitDto(unit);

		final HttpEntity<UnitDto> request = new HttpEntity<>(unitDto, headers);

		// ***** Actual Test *****
		template.put("/units/3", request);

		response = template.getForEntity("/units/3", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().abbreviation()).isNotNull();
		assertThat(response.getBody().abbreviation()).isEqualTo(newUnitAbbr);
	}

	@Test
	@DisplayName("PUT update units parent unit")
	public void setParentUnit() {
		// *** This would handle the client somehow ***
		final Unit unit = new Unit();
		unit.setId(2l);
		UnitDto unitDto = mapper.unitToUnitDto(unit);

		final HttpEntity<UnitDto> request = new HttpEntity<>(unitDto, headers);

		// ***** Actual Test *****
		template.put("/units/4/parent", request);

		final ResponseEntity<UnitDto> response = template.getForEntity("/units/4", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		unitDto = response.getBody();
		final UnitSlimDto parentUnit = unitDto.parentUnit();
		assertThat(parentUnit).isNotNull();
		assertThat(parentUnit.id()).isEqualTo(2);
	}

	@Test
	@DisplayName("DELETE remove parent unit reference")
	public void removeParentUnit() {
		// ***** Actual Test *****
		template.delete("/units/5/parent");

		final ResponseEntity<UnitDto> response = template.getForEntity("/units/5", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final UnitDto unitDto = response.getBody();
		final UnitSlimDto parentUnit = unitDto.parentUnit();
		assertThat(parentUnit).isNull();
	}

	@Test
	@DisplayName("PUT update units director")
	public void setDirector() {
		// *** This would handle the client somehow ***
		final Person person = new Person();
		person.setId(6l);
		final PersonDto personDto = mapper.personToPersonDto(person);

		final HttpEntity<PersonDto> request = new HttpEntity<>(personDto, headers);

		// ***** Actual Test *****
		template.put("/units/6/director", request);

		final ResponseEntity<UnitDto> response = template.getForEntity("/units/6", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final UnitDto unitDto = response.getBody();
		final PersonSlimDto director = unitDto.director();
		assertThat(director).isNotNull();
		assertThat(director.id()).isEqualTo(6);
	}

	@Test
	@DisplayName("DELETE remove director reference")
	public void removeDirector() {
		// ***** Actual Test *****
		template.delete("/units/6/director");

		final ResponseEntity<UnitDto> response = template.getForEntity("/units/6", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final UnitDto unitDto = response.getBody();
		final PersonSlimDto director = unitDto.director();
		assertThat(director).isNull();
	}

	@Test
	@DisplayName("PUT add single member to unit")
	public void addMemberToUnit() {
		// *** This would handle the client somehow ***
		final Person person = new Person();
		person.setId(8l);
		final PersonDto personDto = mapper.personToPersonDto(person);

		final HttpEntity<PersonDto> request = new HttpEntity<>(personDto, headers);

		// ***** Actual Test *****
		template.put("/units/7/members", request);

		final ResponseEntity<UnitDto> response = template.getForEntity("/units/7", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final UnitDto unitDto = response.getBody();
		final Set<PersonSlimDto> members = unitDto.members();
		assertThat(members).isNotNull();
		assertTrue(members.stream().anyMatch(member -> member.id().equals(person.getId())));
	}

	@Test
	@DisplayName("DELETE remove single member")
	public void removeMemeber() {
		// ***** Actual Test *****
		template.delete("/units/7/members/22");

		final ResponseEntity<UnitDto> response = template.getForEntity("/units/7", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final UnitDto unitDto = response.getBody();
		final Set<PersonSlimDto> members = unitDto.members();
		assertThat(members).isNotNull();
		assertFalse(members.stream().anyMatch(member -> member.id().equals(22l)));
	}

	@DirtiesContext
	@Test
	@DisplayName("DELETE remove all members")
	public void removeAllMemeber() {
		// ***** Actual Test *****
		template.delete("/units/7/members");

		final ResponseEntity<UnitDto> response = template.getForEntity("/units/7", UnitDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

		final UnitDto unitDto = response.getBody();
		final Set<PersonSlimDto> members = unitDto.members();
		assertThat(members).isNotNull();
		assertThat(members).isEmpty();
	}
}
