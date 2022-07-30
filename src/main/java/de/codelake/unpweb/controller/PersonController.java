package de.codelake.unpweb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.service.PersonService;

@RestController
@RequestMapping("persons")
public class PersonController {

	private final PersonService service;

	public PersonController(final PersonService service) {
		this.service = service;
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<PersonDto> findPersonById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.findPersonById(id));
	}

	@GetMapping
	public ResponseEntity<List<PersonDto>> findPersons() {
		return ResponseEntity.ok(service.findPersons());
	}
}
