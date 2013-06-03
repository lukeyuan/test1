package com.example.android.contact1;

public class ContactDetails {
	public ContactDetails(String name, String mobileNum, String pattenid, String gender,
			String position, String email, String address, String postcode) {
		super();
		this.name = name;
		this.mobileNum = mobileNum;
		this.pattenid = pattenid;
		this.gender = gender;
		this.position = position;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
	}
	public ContactDetails(){
		
	}
	
	private String pattenid;
	private String name;
	private String gender;
	private String mobileNum;
	private String position;
	private String email;
	private String address;
	public String postcode;

	public String getPattenid() {
		return pattenid;
	}

	public void setPattenid(String pattenid) {
		this.pattenid = pattenid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	
}

