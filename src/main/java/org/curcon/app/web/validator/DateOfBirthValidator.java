package org.curcon.app.web.validator;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, Date> {

	@Override
	public void initialize(DateOfBirth date) {
	}

	@Override
	public boolean isValid(Date date, ConstraintValidatorContext ctx) {
		if (date == null) {
			return false;
		}
		// Let's assume people do not live longer than 130 years usually :)
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 130);
		if (date.before(c.getTime()) || date.after(new Date())) {
			return false;
		}
		return true;
	}

}
