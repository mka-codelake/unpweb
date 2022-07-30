package de.codelake.unpweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.mapper.EntityDtoMapper;
import de.codelake.unpweb.domain.repository.PersonRepository;

@Service
public class PersonService {

	private final PersonRepository repo;
	private final EntityDtoMapper mapper;

	public PersonService(final PersonRepository repo, final EntityDtoMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	public PersonDto getPersonById(final Long id) {
		return mapper.personToPersonDto(repo.getReferenceById(id));
	}

	public List<PersonDto> getPersons() {
		return repo.findAll().stream().map(mapper::personToPersonDto).toList();
	}

}
