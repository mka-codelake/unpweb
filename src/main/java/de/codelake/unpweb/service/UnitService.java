package de.codelake.unpweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.dto.UnitSlimDto;
import de.codelake.unpweb.domain.mapper.EntityDtoMapper;
import de.codelake.unpweb.domain.model.Person;
import de.codelake.unpweb.domain.model.Unit;
import de.codelake.unpweb.domain.repository.UnitRepository;
import de.codelake.unpweb.exception.EntityNotFoundException;

@Service
public class UnitService {

	private final UnitRepository repo;
	private final EntityDtoMapper mapper;

	public UnitService(final UnitRepository repo, final EntityDtoMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	public UnitSlimDto findUnitSlimById(final Long id) {
		return mapper.unitToUnitSlimDto(repo.findById(id).orElseThrow(EntityNotFoundException::new));
	}

	public List<UnitSlimDto> findUnitsSlim() {
		return repo.findAll().stream().map(mapper::unitToUnitSlimDto).toList();
	}

	public List<UnitDto> findUnits() {
		return repo.findAll().stream().map(mapper::unitToUnitDto).toList();
	}

	public UnitDto findUnitById(final Long id) {
		return mapper.unitToUnitDto(repo.findById(id).orElseThrow(EntityNotFoundException::new));
	}

	public PersonDto findDirectorOfUnitById(final Long unitId) {
		final Unit unit = repo.findById(unitId).orElseThrow(EntityNotFoundException::new);
		final Person director = Optional.ofNullable(unit.getDirector()).orElseThrow(EntityNotFoundException::new);
		return mapper.personToPersonDto(director);
	}

	public List<PersonDto> findMembersOfUnitById(final Long unitId) {
		final Unit unit = repo.findById(unitId).orElseThrow(EntityNotFoundException::new);
		return unit.getMembers().stream().map(mapper::personToPersonDto).toList();
	}

}
