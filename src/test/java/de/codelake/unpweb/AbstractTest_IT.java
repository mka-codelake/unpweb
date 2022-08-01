package de.codelake.unpweb;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;

import de.codelake.unpweb.domain.mapper.EntityDtoMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest_IT {

	@Autowired
	protected TestRestTemplate template;

	@Autowired
	protected EntityDtoMapper mapper;

	protected HttpHeaders headers;

	@BeforeEach
	public void setUp() {
		headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
	}
}
