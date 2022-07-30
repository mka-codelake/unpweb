package de.codelake.unpweb.domain.mapper;

import org.mapstruct.Mapper;

import de.codelake.unpweb.domain.dto.PersonDto;
import de.codelake.unpweb.domain.dto.PersonSlimDto;
import de.codelake.unpweb.domain.dto.UnitDto;
import de.codelake.unpweb.domain.dto.UnitSlimDto;
import de.codelake.unpweb.domain.model.Person;
import de.codelake.unpweb.domain.model.Unit;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {

	UnitSlimDto unitToUnitSlimDto(Unit unit);

	Unit unitSlimDtoToUnit(UnitSlimDto unitDto);

	UnitDto unitToUnitDto(Unit unit);

	Unit unitDtoToUnit(UnitDto unitExtDto);

	PersonSlimDto personToPersonSlimDto(Person person);

	Person personSlimDtoToPerson(PersonSlimDto personDto);

	PersonDto personToPersonDto(Person person);

	Person personDtoToPerson(PersonDto personExtDto);
}
