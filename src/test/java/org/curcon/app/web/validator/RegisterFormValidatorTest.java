package org.curcon.app.web.validator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.curcon.app.data.UserRepository;
import org.curcon.app.data.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.MapBindingResult;

public class RegisterFormValidatorTest {

	private RegisterFormValidator validator;

	@Before
	public void setUp() {
		UserRepository repo = mock(UserRepository.class);
		when(repo.findByEmail("email1@test.com")).thenReturn(new User());
		validator = new RegisterFormValidator(repo);
	}

	@Test
	public void testValid() {
		User form = new User();
		form.setEmail("email12@test.com");
		form.setPassword("password123");
		form.setConfirmPassword("password123");
		MapBindingResult errors = new MapBindingResult(new HashMap<>(), "object");
		validator.validate(form, errors);
		assertThat(errors.hasErrors()).isFalse();
	}

	@Test
	public void testDuplicate() {
		User form = new User();
		form.setEmail("email1@test.com");
		form.setPassword("password123");
		form.setConfirmPassword("password123");
		MapBindingResult errors = new MapBindingResult(new HashMap<>(), "object");
		validator.validate(form, errors);
		assertThat(errors.hasErrors()).isTrue();
	}

	@Test
	public void testNonMatchingPasswords() {
		User form = new User();
		form.setEmail("email12@test.com");
		form.setPassword("password1234");
		form.setConfirmPassword("password123");
		MapBindingResult errors = new MapBindingResult(new HashMap<>(), "object");
		validator.validate(form, errors);
		assertThat(errors.hasErrors()).isTrue();
	}
}
