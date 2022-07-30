package de.codelake.unpweb.domain.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Unit {

	@Id
	private Long id;

	private String name;
	private String abbreviation;

	@OneToOne
	@EqualsAndHashCode.Exclude
	private Unit parentUnit;

	@OneToMany(mappedBy = "belongsTo", fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private Set<Person> members;

	@OneToOne
	@EqualsAndHashCode.Exclude
	private Person director;

}
