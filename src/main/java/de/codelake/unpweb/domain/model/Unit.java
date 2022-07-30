package de.codelake.unpweb.domain.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Unit {

	@Id
	private Long id;

	private String name;
	private String abbreviation;

	@OneToOne
	private Unit parentUnit;

	@OneToMany(mappedBy = "belongsTo", fetch = FetchType.LAZY)
	private Set<Person> members;

	@OneToOne
	private Person director;

}
