package org.curcon.app.web.validator;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;

import org.junit.Test;

public class DateOfBirthValidatorTest {

	@Test
	public void testValid() {
		DateOfBirthValidator validator = new DateOfBirthValidator();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 10);
		assertThat(validator.isValid(c.getTime(), null)).isTrue();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 40);
		assertThat(validator.isValid(c.getTime(), null)).isTrue();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 50);
		assertThat(validator.isValid(c.getTime(), null)).isTrue();
	}

	@Test
	public void testInvalid() {
		DateOfBirthValidator validator = new DateOfBirthValidator();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.DATE) + 2);
		assertThat(validator.isValid(c.getTime(), null)).isFalse();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 140);
		assertThat(validator.isValid(c.getTime(), null)).isFalse();
	}
}
