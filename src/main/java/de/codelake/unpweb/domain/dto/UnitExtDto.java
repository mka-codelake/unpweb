package de.codelake.unpweb.domain.dto;

import java.util.Set;

public record UnitExtDto(Long id, String name, String abbreviation, Set<PersonDto> members, PersonDto director) {
}
