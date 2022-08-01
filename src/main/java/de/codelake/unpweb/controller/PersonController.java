package de.codelake.unpweb.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.PersonSlimDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.service.PersonService;

@RestController
@RequestMapping("persons")
public class PersonController {

	private final PersonService service;

	public PersonController(final PersonService service) {
		this.service = service;
	}

	@GetMapping(path = "/slim/{id}")
	public ResponseEntity<PersonSlimDto> findPersonSlimById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.findPersonSlimById(id));
	}

	@GetMapping(path = "/slim")
	public ResponseEntity<List<PersonSlimDto>> findPersonsSlim() {
		return ResponseEntity.ok(service.findPersonsSlim());
	}

	@GetMapping
	public ResponseEntity<List<PersonDto>> findPersons() {
		return ResponseEntity.ok(service.findPersons());
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<PersonDto> findPersonById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.findPersonById(id));
	}

	@GetMapping(path = "{id}/supervisor")
	public ResponseEntity<PersonDto> findSupervisorOfPersonById(@PathVariable(name = "id") final Long personId) {
		return ResponseEntity.ok(service.findSupervisorOfPersonById(personId));
	}

	@GetMapping(path = "{id}/belongsto")
	public ResponseEntity<UnitDto> findBelongsToOfPersonById(@PathVariable(name = "id") final Long personId) {
		return ResponseEntity.ok(service.findBelongsToOfPersonById(personId));
	}

	@PostMapping
	public ResponseEntity<PersonDto> createNewPerson() {
		final PersonDto personDto = service.saveNew();
		return ResponseEntity.created(URI.create(String.format("persons/%d", personDto.id()))).body(personDto);
	}
}
