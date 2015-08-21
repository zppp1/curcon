package org.curcon.app.provider.openexchangerates;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.curcon.app.data.Currency;
import org.curcon.app.provider.ExchangeRateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix = "openexchangerates")
public class OpenExchangeRatesProvider implements ExchangeRateProvider {

	private final static Logger logger = LoggerFactory.getLogger(OpenExchangeRatesProvider.class);

	private static final String LATEST_URL = "http://openexchangerates.org/api/latest.json?app_id={appid}&base={base}";
	private static final String HISTORICAL_URL = "http://openexchangerates.org/api/historical/{date}.json?app_id={appid}&base={base}";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");

	private String appid;
	private boolean fixedBase;

	@Override
	public Double getHistorical(Date date, Currency base, Currency target) {
		return get(HISTORICAL_URL, date, base, target);
	}

	@Override
	public Double getLatest(Currency base, Currency target) {
		return get(LATEST_URL, new Date(), base, target);
	}

	private Double get(String url, Date date, Currency base, Currency target) {
		if (logger.isInfoEnabled()) {
			logger.info("Calling OpenExchangeRates API " + url + " : " + base + " -> " + target);
		}
		RestTemplate template = new RestTemplate();
		OpenExchangeRatesResponse rsp = new OpenExchangeRatesResponse();
		Map<String, Object> variables = new HashMap<>();
		variables.put("date", DATE_FORMAT.format(date));
		variables.put("appid", appid);
		variables.put("base", base);
		try {
			rsp = template.getForObject(url, OpenExchangeRatesResponse.class, variables);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("OpenExchangeRates API Reponse Received");
		}
		return rsp.getRates() != null ? rsp.getRates().get(target.name()) : null;
	}

	@Override
	public boolean isFixedBase() {
		return fixedBase;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setFixedBase(boolean fixedBase) {
		this.fixedBase = fixedBase;
	}

}
