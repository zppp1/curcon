package org.curcon.app.web.controller;

import java.util.Collections;
import java.util.List;

import org.curcon.app.data.UserRepository;
import org.curcon.app.data.entity.User;
import org.curcon.app.data.entity.UserQuery;
import org.curcon.app.provider.ExchangeRateProvider;
import org.curcon.app.web.form.QueryForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/home")
public class HomepageController {

	private static final int MAX_QUERIES_SIZE = 10;
	private final static Logger logger = LoggerFactory.getLogger(HomepageController.class);

	private final UserRepository userRepository;
	private final ExchangeRateProvider exchangeRateProvider;
	private final SecurityContextAccessor securityContextAccessor;

	@Autowired
	public HomepageController(UserRepository userRepository,
			@Qualifier("cachedProvider") ExchangeRateProvider exchangeRateProvider,
			SecurityContextAccessor securityContextAccessor) {
		this.userRepository = userRepository;
		this.exchangeRateProvider = exchangeRateProvider;
		this.securityContextAccessor = securityContextAccessor;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show(@ModelAttribute("query") QueryForm form) {
		String email = getUserEmail();
		if (logger.isDebugEnabled()) {
			logger.debug("Showing homepage for " + email);
		}
		User user = userRepository.findByEmail(email);
		List<UserQuery> queries = user.getQueries().subList(Math.max(0, user.getQueries().size() - MAX_QUERIES_SIZE),
				user.getQueries().size());
		Collections.reverse(queries);
		ModelMap modelMap = new ModelMap();
		form.setFixedBase(exchangeRateProvider.isFixedBase());
		modelMap.put("query", form);
		modelMap.put("user", user);
		modelMap.put("recentQueries", queries);
		return new ModelAndView("home", modelMap);
	}

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public String query(QueryForm form, RedirectAttributes redirectAttributes) {
		String email = getUserEmail();
		if (logger.isDebugEnabled()) {
			logger.debug("Running query for " + email + ": " + form.getBase() + " -> " + form.getTarget());
		}
		Double rate = form.isLatest() ? exchangeRateProvider.getLatest(form.getBase(), form.getTarget())
				: exchangeRateProvider.getHistorical(form.getDate(), form.getBase(), form.getTarget());
		if (rate == null) {
			return "redirect:/home?error";
		}
		UserQuery q = new UserQuery();
		q.setDate(form.getDate());
		q.setSource(form.getBase());
		q.setTarget(form.getTarget());
		q.setRate(rate);
		User user = userRepository.findByEmail(email);
		// Remove potential duplicates
		user.getQueries().remove(q);
		user.getQueries().add(q);
		userRepository.save(user);
		redirectAttributes.addFlashAttribute("query", form);
		return "redirect:/home";
	}

	private String getUserEmail() {
		return securityContextAccessor.getUserEmail();
	}

}
