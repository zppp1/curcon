package org.curcon.app.web.form;

import java.util.Date;

import org.curcon.app.data.Currency;
import org.springframework.format.annotation.DateTimeFormat;

public class QueryForm {

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date date;
	private Currency base = Currency.USD;
	private Currency target = Currency.EUR;
	private boolean latest;
	private boolean fixedBase;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Currency getBase() {
		return fixedBase ? Currency.USD : base;
	}

	public void setBase(Currency base) {
		this.base = base;
	}

	public Currency getTarget() {
		return target;
	}

	public void setTarget(Currency target) {
		this.target = target;
	}

	public boolean isLatest() {
		return latest;
	}

	public void setLatest(boolean latest) {
		this.latest = latest;
	}

	public boolean isFixedBase() {
		return fixedBase;
	}

	public void setFixedBase(boolean fixedBase) {
		this.fixedBase = fixedBase;
	}

}
