package de.codelake.unpweb.domain.dto;

import lombok.Data;

@Data
public class PersonExtDto {
	private Long id;
	private String name;
	private String initials;
	private String role;
	private PersonDto supervisor;
	private UnitDto belongsTo;
}
