package org.curcon.app.provider;

import java.util.Date;

import org.curcon.app.data.Currency;

public interface ExchangeRateProvider {

	Double getLatest(Currency base, Currency target);

	Double getHistorical(Date date, Currency base, Currency target);

	boolean isFixedBase();
}
