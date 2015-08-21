package org.curcon.app.provider.openexchangerates;

import java.util.Map;

import org.curcon.app.data.Currency;

public class OpenExchangeRatesResponse {

	private Currency base;
	private Map<String, Double> rates;

	public Currency getBase() {
		return base;
	}

	public void setBase(Currency base) {
		this.base = base;
	}

	public Map<String, Double> getRates() {
		return rates;
	}

	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

}
