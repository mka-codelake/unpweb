package de.codelake.unpweb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.dto.UnitSlimDto;
import de.codelake.unpweb.service.UnitService;

@RestController
@RequestMapping("units")
public class UnitController {

	private final UnitService service;

	public UnitController(final UnitService service) {
		this.service = service;
	}

	@GetMapping(path = "/slim/{id}")
	public ResponseEntity<UnitSlimDto> findUnitSlimById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.findUnitSlimById(id));
	}

	@GetMapping(path = "/slim")
	public ResponseEntity<List<UnitSlimDto>> findUnitsSlim() {
		return ResponseEntity.ok(service.findUnitsSlim());
	}

	@GetMapping
	public ResponseEntity<List<UnitDto>> findUnits() {
		return ResponseEntity.ok(service.findUnits());
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<UnitDto> findUnitById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.findUnitById(id));
	}

	@GetMapping(path = "{id}/director")
	public ResponseEntity<PersonDto> findDirectorOfUnitById(@PathVariable(value = "id") final Long unitId) {
		return ResponseEntity.ok(service.findDirectorOfUnitById(unitId));
	}

	@GetMapping(path = "{id}/members")
	public ResponseEntity<List<PersonDto>> findMemebersOfUnitById(@PathVariable(value = "id") final Long unitId) {
		return ResponseEntity.ok(service.findMembersOfUnitById(unitId));
	}
}
