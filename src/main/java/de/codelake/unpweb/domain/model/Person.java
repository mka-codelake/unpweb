package de.codelake.unpweb.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_person")
	@SequenceGenerator(name = "custom_person", initialValue = 27, allocationSize = 10)
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
