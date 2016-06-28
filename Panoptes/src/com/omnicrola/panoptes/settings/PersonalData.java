package com.omnicrola.panoptes.settings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.omnicrola.panoptes.data.IReadPersonalData;

@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalData implements IReadPersonalData {

	private String firstName = "";
	private String lastname = "";
	private String companyName = "";
	private String address = "";
	private String city = "";
	private String state = "";
	private String zip = "";
	private String phone = "";
	private String email = "";

	@Override
	public String getStreetAddress() {
		return this.address;
	}

	@Override
	public String getCity() {
		return this.city;
	}

	@Override
	public String getCompanyName() {
		return this.companyName;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getFullName() {
		return this.firstName + " " + this.lastname;
	}

	@Override
	public String getLastName() {
		return this.lastname;
	}

	@Override
	public String getPhone() {
		return this.phone;
	}

	@Override
	public String getState() {
		return this.state;
	}

	@Override
	public String getZip() {
		return this.zip;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
