package de.codelake.unpweb.domain.dto;

import lombok.Data;

@Data
public class PersonDto {
	private Long id;
	private String name;
	private String initials;
	private String role;
}
