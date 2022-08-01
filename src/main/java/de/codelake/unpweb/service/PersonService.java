package de.codelake.unpweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.mapper.EntityDtoMapper;
import de.codelake.unpweb.domain.model.Person;
import de.codelake.unpweb.domain.model.Unit;
import de.codelake.unpweb.domain.repository.PersonRepository;
import de.codelake.unpweb.exception.EntityNotFoundException;

@Service
public class PersonService {

	private final PersonRepository repo;
	private final EntityDtoMapper mapper;
	private final UnitService unitService;

	public PersonService(@Lazy final UnitService unitService, final PersonRepository repo,
			final EntityDtoMapper mapper) {
		this.unitService = unitService;
		this.repo = repo;
		this.mapper = mapper;
	}

	private Person findPerson(final Long id) {
		return repo.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	public List<PersonDto> findPersons() {
		return repo.findAll().stream().map(mapper::personToPersonDto).toList();
	}

	public PersonDto findPersonById(final Long id) {
		return mapper.personToPersonDto(findPerson(id));
	}

	public PersonDto findSupervisorOfPersonById(final Long personId) {
		final Person person = findPerson(personId);
		final Person supervisor = Optional.ofNullable(person.getSupervisor()).orElseThrow(EntityNotFoundException::new);
		return mapper.personToPersonDto(supervisor);
	}

	public UnitDto findBelongsToOfPersonById(final Long personId) {
		final Person person = findPerson(personId);
		final Unit belongsTo = Optional.ofNullable(person.getBelongsTo()).orElseThrow(EntityNotFoundException::new);
		return mapper.unitToUnitDto(belongsTo);
	}

	public PersonDto saveNew() {
		return mapper.personToPersonDto(repo.save(new Person()));
	}

	public void setBelongsTo(final PersonDto newMemberDto, final UnitDto unitDto) {
		final Person person = findPerson(newMemberDto.id());
		final Unit unit = mapper.unitDtoToUnit(unitDto);
		person.setBelongsTo(unit);
		repo.save(person);
	}

	public void removeBelongsTo(final Long personId) {
		final Person person = findPerson(personId);
		person.setBelongsTo(null);
		repo.save(person);
	}

	public void deletePerson(final Long personId) {
		// Integrity checks:
		final Person personToDelete = findPerson(personId);
		// Remove director reference
		unitService.removeDirectorFromAllUnitsIfExists(personId);
		// Remove supervisor reference
		final List<Person> employees = repo.findAllBySupervisor(personToDelete);
		employees.forEach(employee -> removeSupervisor(employee.getId()));

		repo.delete(personToDelete);
	}

	public void removeSupervisor(final Long personId) {
		final Person person = findPerson(personId);
		person.setSupervisor(null);
		repo.save(person);
	}

	public void update(final Long personId, final PersonDto personDto) {
		final Person personToUpdate = findPerson(personId);
		personToUpdate.setName(personDto.name());
		personToUpdate.setInitials(personDto.initials());
		personToUpdate.setRole(personDto.role());
		repo.save(personToUpdate);
	}

	public void updateSupervisor(final Long personId, final PersonDto supervisorDto) {
		final Person person = findPerson(personId);
		final Person supervisor = mapper.personDtoToPerson(supervisorDto);
		person.setSupervisor(supervisor);
		repo.save(person);
	}

	public void updateBelongsTo(final Long personId, final UnitDto belongsToDto) {
		final Person person = findPerson(personId);
		final Unit belongsTo = mapper.unitDtoToUnit(belongsToDto);
		person.setBelongsTo(belongsTo);
		repo.save(person);
	}
}
