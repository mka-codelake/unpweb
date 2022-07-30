package de.codelake.unpweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.PersonSlimDto;
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

	public PersonService(final PersonRepository repo, final EntityDtoMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	public PersonSlimDto findPersonSlimById(final Long id) {
		return mapper.personToPersonSlimDto(repo.findById(id).orElseThrow(EntityNotFoundException::new));
	}

	public List<PersonSlimDto> findPersonsSlim() {
		return repo.findAll().stream().map(mapper::personToPersonSlimDto).toList();
	}

	public List<PersonDto> findPersons() {
		return repo.findAll().stream().map(mapper::personToPersonDto).toList();
	}

	public PersonDto findPersonById(final Long id) {
		return mapper.personToPersonDto(repo.findById(id).orElseThrow(EntityNotFoundException::new));
	}

	public PersonDto findSupervisorOfPersonById(final Long personId) {
		final Person person = repo.findById(personId).orElseThrow(EntityNotFoundException::new);
		final Person supervisor = Optional.ofNullable(person.getSupervisor()).orElseThrow(EntityNotFoundException::new);
		return mapper.personToPersonDto(supervisor);
	}

	public UnitDto findBelongsToOfPersonById(final Long personId) {
		final Person person = repo.findById(personId).orElseThrow(EntityNotFoundException::new);
		final Unit belongsTo = Optional.ofNullable(person.getBelongsTo()).orElseThrow(EntityNotFoundException::new);
		return mapper.unitToUnitDto(belongsTo);
	}

}
