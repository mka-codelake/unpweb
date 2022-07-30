package de.codelake.unpweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.mapper.EntityDtoMapper;
import de.codelake.unpweb.domain.repository.UnitRepository;

@Service
public class UnitService {

	private final UnitRepository repo;
	private final EntityDtoMapper mapper;

	public UnitService(final UnitRepository repo, final EntityDtoMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	public UnitDto getUnitById(final Long id) {
		return mapper.unitToUnitDto(repo.getReferenceById(id));
	}

	public List<UnitDto> findAllUnits() {
		return repo.findAll().stream().map(mapper::unitToUnitDto).toList();
	}

}
