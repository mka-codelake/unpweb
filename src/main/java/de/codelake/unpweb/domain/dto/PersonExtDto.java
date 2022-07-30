package de.codelake.unpweb.domain.dto;

public record PersonExtDto(Long id, String name, String initials, String role, PersonDto supervisor,
		UnitDto belongsTo) {
}
