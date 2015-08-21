package org.curcon.app.web.controller;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.curcon.app.data.Currency;
import org.curcon.app.data.UserRepository;
import org.curcon.app.data.entity.User;
import org.curcon.app.data.entity.UserQuery;
import org.curcon.app.provider.ExchangeRateProvider;
import org.curcon.app.web.form.QueryForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@RunWith(MockitoJUnitRunner.class)
public class HomepageControllerTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private ExchangeRateProvider exchangeRateProvider;
	@Mock
	private SecurityContextAccessor securityContextAccessor;
	private HomepageController controller;
	private User user;

	@Before
	public void setup() {
		when(securityContextAccessor.getUserEmail()).thenReturn("email1@aa.com");
		user = new User();
		when(userRepository.findByEmail(anyString())).thenReturn(user);
		controller = new HomepageController(userRepository, exchangeRateProvider, securityContextAccessor);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHome() throws Exception {
		ModelAndView mav = controller.show(new QueryForm());
		assertThat(mav.getViewName()).isEqualTo("home");
		assertThat(mav.getModelMap().get("user")).isEqualTo(user);
		List<UserQuery> queries = (List<UserQuery>) mav.getModelMap().get("recentQueries");
		assertThat(queries).isNotNull();
		assertThat(queries.size()).isEqualTo(0);
		for (int i = 0; i < 15; i++) {
			user.getQueries().add(new UserQuery());
		}
		mav = controller.show(new QueryForm());
		assertThat(mav.getViewName()).isEqualTo("home");
		assertThat(mav.getModelMap().get("user")).isEqualTo(user);
		queries = (List<UserQuery>) mav.getModelMap().get("recentQueries");
		assertThat(queries).isNotNull();
		assertThat(queries.size()).isEqualTo(10);
	}

	@Test
	public void testQuery() throws Exception {
		QueryForm form = new QueryForm();
		form.setDate(new Date());
		form.setBase(Currency.GBP);
		form.setTarget(Currency.EUR);
		String view = controller.query(form, new RedirectAttributesModelMap());
		assertThat(view).isEqualTo("redirect:/home");
		verify(userRepository).save(user);
		assertThat(user.getQueries().size()).isEqualTo(1);
	}
}
