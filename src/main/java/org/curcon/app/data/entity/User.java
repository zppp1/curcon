package org.curcon.app.data.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.curcon.app.data.Country;
import org.curcon.app.web.validator.DateOfBirth;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@NotNull
	@Email
	private String email;
	@Size(min = 8, max = 100)
	private String password;
	@Size(min = 1, max = 100)
	private String name;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@NotNull
	@DateOfBirth
	private Date dateOfBirth;
	private String postalCode;
	private String street;
	private String city;
	private Country country;
	@Transient
	private String confirmPassword;
	@ElementCollection
	@CollectionTable(name = "USER_QUERY", joinColumns = @JoinColumn(name = "USER_ID") )
	private List<UserQuery> queries = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<UserQuery> getQueries() {
		return queries;
	}

	public void setQueries(List<UserQuery> queries) {
		this.queries = queries;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
