package org.curcon.app.provider.cache;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.curcon.app.data.Currency;
import org.curcon.app.provider.ExchangeRateProvider;
import org.curcon.app.provider.openexchangerates.OpenExchangeRatesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Component("cachedProvider")
@ConfigurationProperties(prefix = "exchangeratecache")
public class CachedExchangeRateProviderImpl implements CachedExchangeRateProvider {

	private final static Logger logger = LoggerFactory.getLogger(OpenExchangeRatesProvider.class);

	private int ttl;
	private int size;
	private Cache<ExchangeRateCacheKey, Double> cache;
	@Autowired
	private ExchangeRateProvider provider;

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@PostConstruct
	public void init() {
		cache = CacheBuilder.newBuilder().expireAfterWrite(ttl, TimeUnit.MINUTES).maximumSize(size).build();
	}

	@Override
	public Double getLatest(Currency base, Currency target) {
		Double rate = null;
		try {
			rate = cache.get(new ExchangeRateCacheKey(null, base, target), () -> provider.getLatest(base, target));
		} catch (ExecutionException e) {
			logger.error(e.getMessage(), e);
		}
		return rate;
	}

	@Override
	public Double getHistorical(Date date, Currency base, Currency target) {
		Double rate = null;
		try {
			rate = cache.get(new ExchangeRateCacheKey(date, base, target),
					() -> provider.getHistorical(date, base, target));
		} catch (ExecutionException e) {
			logger.error(e.getMessage(), e);
		}
		return rate;
	}

	@Override
	public boolean isFixedBase() {
		return provider.isFixedBase();
	}

}
