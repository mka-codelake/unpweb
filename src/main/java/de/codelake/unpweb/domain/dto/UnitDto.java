package de.codelake.unpweb.domain.dto;

import java.util.Set;

public record UnitDto(Long id, String name, String abbreviation, Set<PersonSlimDto> members, PersonSlimDto director) {
}
