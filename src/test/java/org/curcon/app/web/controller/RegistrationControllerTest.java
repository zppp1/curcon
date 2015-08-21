package org.curcon.app.web.controller;

import static org.fest.assertions.Assertions.assertThat;

import org.curcon.app.CurConApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { CurConApplication.class, ControllerTestSecurityConfig.class })
@WebIntegrationTest({ "server.port=0" })
public class RegistrationControllerTest {

	@Value("${local.server.port}")
	protected int port;

	protected RestTemplate template = new TestRestTemplate();

	@Test
	public void testIndex() {
		String index = template.getForEntity(getBasePath(), String.class).getBody();
		assertThat(index).contains("<title>Currency Converter</title>");
	}

	@Test
	public void testRegisterValid() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("name", "John");
		map.add("email", "john@test.com");
		map.add("password", "pass1234");
		map.add("confirmPassword", "pass1234");
		map.add("dateOfBirth", "05-04-1970");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> register = template.postForEntity(getBasePath() + "/register", entity, String.class);
		assertThat(register.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(register.getHeaders().get("Location").isEmpty()).isFalse();
		assertThat(register.getHeaders().get("Location").get(0)).endsWith("/home");
	}

	protected String getBasePath() {
		return "http://localhost:" + port + "/";
	}

}
