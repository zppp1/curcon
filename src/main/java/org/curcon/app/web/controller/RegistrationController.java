package org.curcon.app.web.controller;

import javax.validation.Valid;

import org.curcon.app.data.UserRepository;
import org.curcon.app.data.entity.User;
import org.curcon.app.web.validator.RegisterFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

	private final static Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RegisterFormValidator validator;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView show(@ModelAttribute("registerForm") User form) {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute("registerForm") @Valid User form, BindingResult result) {
		validator.validate(form, result);
		if (result.hasErrors()) {
			return new ModelAndView("login");
		}
		if (logger.isInfoEnabled()) {
			logger.info("Creating new user: " + form.getEmail());
		}
		userRepository.save(form);
		UserDetails principal = userDetailsService.loadUserByUsername(form.getEmail());
		if (principal != null) {
			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
		}
		return new ModelAndView("redirect:/home");
	}

}
