package de.codelake.unpweb.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Person {

	@Id
	private Long id;

	private String name;
	private String initials;
	private String role;

	@OneToOne
	@EqualsAndHashCode.Exclude
	private Person supervisor;

	@ManyToOne
	@EqualsAndHashCode.Exclude
	private Unit belongsTo;

}
