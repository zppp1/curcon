package org.curcon.app.provider.cache;

import java.util.Date;

import org.curcon.app.data.Currency;

public class ExchangeRateCacheKey {

	private final Date date;
	private final Currency base;
	private final Currency target;

	public ExchangeRateCacheKey(Date date, Currency base, Currency target) {
		super();
		this.date = date;
		this.base = base;
		this.target = target;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExchangeRateCacheKey other = (ExchangeRateCacheKey) obj;
		if (base != other.base)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (target != other.target)
			return false;
		return true;
	}

}
