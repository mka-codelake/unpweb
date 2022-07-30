package de.codelake.unpweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.dto.UnitExtDto;
import de.codelake.unpweb.domain.mapper.EntityDtoMapper;
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

	public UnitDto findUnitById(final Long id) {
		return mapper.unitToUnitDto(repo.findById(id).orElseThrow(EntityNotFoundException::new));
	}

	public List<UnitDto> findUnits() {
		return repo.findAll().stream().map(mapper::unitToUnitDto).toList();
	}

	public List<UnitExtDto> findUnitsExt() {
		return repo.findAll().stream().map(mapper::unitToUnitExtDto).toList();
	}

	public UnitExtDto findUnitExtById(final Long id) {
		return mapper.unitToUnitExtDto(repo.findById(id).orElseThrow(EntityNotFoundException::new));
	}

}
