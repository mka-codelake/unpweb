package de.codelake.unpweb.domain.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UnitExtDto {
	private Long id;
	private String name;
	private String abbreviation;
	private Set<PersonDto> members;
	private PersonDto director;
}
