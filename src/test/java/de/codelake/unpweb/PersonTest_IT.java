package de.codelake.unpweb;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import de.codelake.unpweb.domain.dto.UnitDto;

public class PersonTest_IT extends AbstractTest_IT {

	@Test
	@DisplayName("GET all persons")
	public void readAllPersons() {
		@SuppressWarnings("unchecked")
		final var response = template.getForEntity("/persons", (Class<List<UnitDto>>) (Class<?>) List.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).hasSize(26);
	}

}
