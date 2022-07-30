package de.codelake.unpweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.service.UnitService;

@RestController
public class UnitController {

	private final UnitService service;

	public UnitController(final UnitService service) {
		this.service = service;
	}

	@GetMapping(path = "units/{id}")
	public ResponseEntity<UnitDto> getUnitById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.getUnitById(id));
	}

}
