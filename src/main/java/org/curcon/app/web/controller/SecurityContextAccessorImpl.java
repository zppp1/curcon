package org.curcon.app.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAccessorImpl implements SecurityContextAccessor {

	public String getUserEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
