package de.codelake.unpweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.mapper.EntityDtoMapper;
import de.codelake.unpweb.domain.model.Person;
import de.codelake.unpweb.domain.model.Unit;
import de.codelake.unpweb.domain.repository.UnitRepository;
import de.codelake.unpweb.exception.EntityNotFoundException;

@Service
public class UnitService {

	private final UnitRepository repo;
	private final EntityDtoMapper mapper;
	private final PersonService personService;

	public UnitService(final PersonService personService, final UnitRepository repo, final EntityDtoMapper mapper) {
		this.personService = personService;
		this.repo = repo;
		this.mapper = mapper;
	}

	public List<UnitDto> findAllUnits() {
		return repo.findAll().stream().map(mapper::unitToUnitDto).toList();
	}

	public UnitDto findUnitById(final Long id) {
		return mapper.unitToUnitDto(findUnit(id));
	}

	public PersonDto findDirectorOfUnitById(final Long unitId) {
		final Unit unit = findUnit(unitId);
		final Person director = Optional.ofNullable(unit.getDirector()).orElseThrow(EntityNotFoundException::new);
		return mapper.personToPersonDto(director);
	}

	public List<PersonDto> findMembersOfUnitById(final Long unitId) {
		final Unit unit = findUnit(unitId);
		return unit.getMembers().stream().map(mapper::personToPersonDto).toList();
	}

	public UnitDto saveNew() {
		return mapper.unitToUnitDto(repo.save(new Unit()));
	}

	public UnitDto save(final UnitDto unsavedUnitDto) {
		final Unit unsavedUnit = mapper.unitDtoToUnit(unsavedUnitDto);
		final Unit savedUnit = repo.save(unsavedUnit);
		//		personService.moveMembersToUnit(unsavedUnitDto.members(), savedUnit);
		return mapper.unitToUnitDto(savedUnit);
	}

	public UnitDto setDirector(final UnitDto unitDto) {
		//		Unit unit = repo.findById(unitDto.id()).orElseThrow(EntityNotFoundException::new);
		return mapper.unitToUnitDto(repo.save(mapper.unitDtoToUnit(unitDto)));
	}

	public UnitDto setDirector(final Long unitId, final PersonDto personDto) {
		final Unit unit = findUnit(unitId);
		final Person person = personService.findPerson(personDto.id());

		unit.setDirector(person);

		return mapper.unitToUnitDto(repo.save(unit));
	}

	public void update(final Long unitId, final UnitDto unitDto) {
		final Unit unitToUpdate = findUnit(unitId);
		unitToUpdate.setName(unitDto.name());
		unitToUpdate.setAbbreviation(unitDto.abbreviation());
		repo.save(unitToUpdate);
	}

	public void updateParentUnit(final Long unitId, final UnitDto parentUnitDto) {
		final Unit unitToUpdate = findUnit(unitId);
		final Unit parentUnit = mapper.unitDtoToUnit(parentUnitDto);
		unitToUpdate.setParentUnit(parentUnit);
		repo.save(unitToUpdate);
	}

	public void removeParentUnit(final Long unitId) {
		final Unit unitToUpdate = findUnit(unitId);
		unitToUpdate.setParentUnit(null);
		repo.save(unitToUpdate);
	}

	public void updateDirector(final Long unitId, final PersonDto directorDto) {
		final Unit unitToUpdate = findUnit(unitId);
		final Person director = mapper.personDtoToPerson(directorDto);
		unitToUpdate.setDirector(director);
		repo.save(unitToUpdate);
	}

	public void removeDirector(final Long unitId) {
		final Unit unitToUpdate = findUnit(unitId);
		unitToUpdate.setDirector(null);
		repo.save(unitToUpdate);
	}

	public void addMember(final Long unitId, final PersonDto newMemberDto) {
		personService.setBelongsTo(newMemberDto, new UnitDto(unitId, null, null, null, null, null));
	}

	public void removeMember(final Long unitId, final Long memberId) {
		personService.removeBelongsTo(memberId);
	}

	public void removeAllMembers(final Long unitId) {
		final Unit unit = findUnit(unitId);
		unit.getMembers().forEach(member -> personService.removeBelongsTo(member.getId()));
	}

	public void deleteUnit(final Long unitId) {
		// Integrity checks:
		final Unit unitToDelete = findUnit(unitId);
		// Remove parent unit reference
		final List<Unit> units = repo.findAllByParentUnit(unitToDelete);
		units.forEach(unit -> removeParentUnit(unit.getId()));
		// Remove belongsTo references
		unitToDelete.getMembers().forEach(member -> personService.removeBelongsTo(member.getId()));

		repo.deleteById(unitId);
	}

	private Unit findUnit(final Long unitId) {
		return repo.findById(unitId).orElseThrow(EntityNotFoundException::new);
	}

	public void removeDirectorFromAllUnitsIfExists(final Long personId) {
		final List<Unit> units = repo.findAllByDirectorId(personId);
		units.forEach(unit -> removeDirector(unit.getId()));
	}

}
