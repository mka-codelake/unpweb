package de.codelake.unpweb.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.service.PersonService;

@RestController
@RequestMapping("persons")
public class PersonController {

	private final PersonService service;

	public PersonController(final PersonService service) {
		this.service = service;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<PersonDto>> findPersons() {
		return ResponseEntity.ok(service.findPersons());
	}

	@PostMapping
	public ResponseEntity<PersonDto> createNewPerson() {
		final PersonDto personDto = service.saveNew();
		return ResponseEntity.created(URI.create(String.format("persons/%d", personDto.id()))).body(personDto);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<PersonDto> findPersonById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.findPersonById(id));
	}

	@PutMapping(path = "{id}")
	public void updatePerson(@PathVariable(value = "id") final Long personId, @RequestBody final PersonDto personDto) {
		service.update(personId, personDto);
	}

	@DeleteMapping(path = "{id}")
	public void deletePerson(@PathVariable(value = "id") final Long personId) {
		service.deletePerson(personId);
	}

	@GetMapping(path = "{id}/supervisor")
	public ResponseEntity<PersonDto> findSupervisorOfPersonById(@PathVariable(name = "id") final Long personId) {
		return ResponseEntity.ok(service.findSupervisorOfPersonById(personId));
	}

	@PutMapping(path = "{id}/supervisor")
	public void updateSupervisor(@PathVariable(value = "id") final Long personId,
			@RequestBody final PersonDto supervisorDto) {
		service.updateSupervisor(personId, supervisorDto);
	}

	@DeleteMapping(path = "{id}/supervisor")
	public void removeSupervisor(@PathVariable(value = "id") final Long personId) {
		service.removeSupervisor(personId);
	}

	@GetMapping(path = "{id}/belongsto")
	public ResponseEntity<UnitDto> findBelongsToOfPersonById(@PathVariable(name = "id") final Long personId) {
		return ResponseEntity.ok(service.findBelongsToOfPersonById(personId));
	}

	@PutMapping(path = "{id}/belongsto")
	public void updateBelongsTo(@PathVariable(value = "id") final Long personId,
			@RequestBody final UnitDto belongsToDto) {
		service.updateBelongsTo(personId, belongsToDto);
	}

	@DeleteMapping(path = "{id}/belongsto")
	public void removeBelongsTo(@PathVariable(value = "id") final Long personId) {
		service.removeBelongsTo(personId);
	}
}
