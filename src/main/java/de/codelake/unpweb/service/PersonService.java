package de.codelake.unpweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.PersonExtDto;
import de.codelake.unpweb.domain.mapper.EntityDtoMapper;
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

	public PersonDto findPersonById(final Long id) {
		return mapper.personToPersonDto(repo.findById(id).orElseThrow(EntityNotFoundException::new));
	}

	public List<PersonDto> findPersons() {
		return repo.findAll().stream().map(mapper::personToPersonDto).toList();
	}

	public List<PersonExtDto> findPersonsExt() {
		return repo.findAll().stream().map(mapper::personToPersonExtDto).toList();
	}

}
