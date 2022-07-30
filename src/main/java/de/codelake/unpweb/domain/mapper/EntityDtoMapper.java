package de.codelake.unpweb.domain.mapper;

import org.mapstruct.Mapper;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.PersonExtDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.dto.UnitExtDto;
import de.codelake.unpweb.domain.model.Person;
import de.codelake.unpweb.domain.model.Unit;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {

	UnitDto unitToUnitDto(Unit unit);

	Unit unitDtoToUnit(UnitDto unitDto);

	UnitExtDto unitToUnitExtDto(Unit unit);

	Unit unitExtDtoToUnit(UnitExtDto unitExtDto);

	PersonDto personToPersonDto(Person person);

	Person personDtoToPerson(PersonDto personDto);

	PersonExtDto personToPersonExtDto(Person person);

	Person personExtDtoToPerson(PersonExtDto personExtDto);
}
