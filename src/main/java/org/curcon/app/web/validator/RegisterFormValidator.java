package org.curcon.app.web.validator;

import org.curcon.app.data.UserRepository;
import org.curcon.app.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class RegisterFormValidator {

	private final UserRepository userRepository;

	@Autowired
	public RegisterFormValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void validate(User form, Errors errors) {
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			errors.rejectValue("password", "passwords_do_not_match", "passwords do not match");
			errors.rejectValue("confirmPassword", "passwords_do_not_match", "passwords do not match");
		}
		User existing = userRepository.findByEmail(form.getEmail());
		if (existing != null) {
			errors.rejectValue("email", "email_already_exists", "email already exists");
		}
	}

}
