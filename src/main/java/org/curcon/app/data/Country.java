package org.curcon.app.data;

public enum Country {

	NN("Unspecified"),
	AU("Australia"),
	CN("China"),
	FR("France"),
	DE("Germany"),
	JP("Japan"),
	NO("Norway"),
	PL("Poland"),
	RU("Russia"),
	ES("Spain"),
	CH("Switzerland"),
	US("United States"),
	UK("United Kingdom");

	private final String value;

	private Country(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
