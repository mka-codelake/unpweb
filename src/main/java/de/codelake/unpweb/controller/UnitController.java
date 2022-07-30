package de.codelake.unpweb.controller;

import java.util.List;

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
	public ResponseEntity<UnitDto> findUnitById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.findUnitById(id));
	}

	@GetMapping(path = "units")
	public ResponseEntity<List<UnitDto>> findAllUnits() {
		return ResponseEntity.ok(service.findAllUnits());
	}
}
