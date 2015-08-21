package org.curcon.app.data.entity;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.curcon.app.data.Currency;
import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class UserQuery {

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date date;
	private Currency source;
	private Currency target;
	private double rate;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Currency getSource() {
		return source;
	}

	public void setSource(Currency source) {
		this.source = source;
	}

	public Currency getTarget() {
		return target;
	}

	public void setTarget(Currency target) {
		this.target = target;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
		UserQuery other = (UserQuery) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		if (source != other.source)
			return false;
		if (target != other.target)
			return false;
		return true;
	}

}
