package de.codelake.unpweb.controller;

import java.net.URI;
import java.util.List;

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
import de.codelake.unpweb.service.UnitService;

@RestController
@RequestMapping("units")
public class UnitController {

	private final UnitService service;

	public UnitController(final UnitService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<UnitDto>> findAllUnits() {
		return ResponseEntity.ok(service.findAllUnits());
	}

	@PostMapping
	public ResponseEntity<UnitDto> createNewUnit() {
		final UnitDto savedUnitDto = service.saveNew();
		return ResponseEntity.created(URI.create(String.format("units/%d", savedUnitDto.id()))).body(savedUnitDto);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<UnitDto> findUnitById(@PathVariable final Long id) {
		return ResponseEntity.ok(service.findUnitById(id));
	}

	@PutMapping(path = "{id}")
	public void updateUnit(@PathVariable(value = "id") final Long unitId, @RequestBody final UnitDto unitDto) {
		service.update(unitId, unitDto);
	}

	@DeleteMapping(path = "{id}")
	public void deleteUnit(@PathVariable(value = "id") final Long unitId) {
		service.deleteUnit(unitId);
	}

	@GetMapping(path = "{id}/director")
	public ResponseEntity<PersonDto> findDirectorOfUnitById(@PathVariable(value = "id") final Long unitId) {
		return ResponseEntity.ok(service.findDirectorOfUnitById(unitId));
	}

	@PutMapping(path = "{id}/director")
	public void updateUnitsDirector(@PathVariable(value = "id") final Long unitId,
			@RequestBody final PersonDto directorDto) {
		service.updateDirector(unitId, directorDto);
	}

	@DeleteMapping(path = "{id}/director")
	public void removeDirector(@PathVariable(value = "id") final Long unitId) {
		service.removeDirector(unitId);
	}

	@GetMapping(path = "{id}/members")
	public ResponseEntity<List<PersonDto>> findMemebersOfUnitById(@PathVariable(value = "id") final Long unitId) {
		return ResponseEntity.ok(service.findMembersOfUnitById(unitId));
	}

	@PutMapping(path = "{id}/members")
	public void addMember(@PathVariable(value = "id") final Long unitId, @RequestBody final PersonDto newMemberDto) {
		service.addMember(unitId, newMemberDto);
	}

	@DeleteMapping(path = "{id}/members")
	public void removeAllMember(@PathVariable(value = "id") final Long unitId) {
		service.removeAllMembers(unitId);
	}

	@DeleteMapping(path = "{unitId}/members/{memberId}")
	public void removeMember(@PathVariable final Long unitId, @PathVariable final Long memberId) {
		service.removeMember(unitId, memberId);
	}

	@PutMapping(path = "{id}/parent")
	public void updateUnitsParent(@PathVariable(value = "id") final Long unitId,
			@RequestBody final UnitDto parentUnitDto) {
		service.updateParentUnit(unitId, parentUnitDto);
	}

	@DeleteMapping(path = "{id}/parent")
	public void removeParentUnit(@PathVariable(value = "id") final Long unitId) {
		service.removeParentUnit(unitId);
	}
}
