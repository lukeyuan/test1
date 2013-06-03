package com.adroid.homework;

public class Information {
	public int _id;
	public String userName;
	public boolean sex;
	public String phone;
	public String job;
	public String email;
	public String address;
	public int postNum;
	public int model;
	
	public  Information(String userName, boolean sex, String phone, String job,
			String email, String address, int postNum, int model)
	{
		this.userName = userName;
		this.sex = sex;
		this.phone = phone;
		this.job = job;
		this.email = email;
		this.address = address;
		this.postNum = postNum;
		this.model = model;
	}
}

class InformationSelf extends Information
{

	public int cardType;
	
	public InformationSelf(String userName, boolean sex, String phone,
			String job, String email, String address, int postNum, int model, int cardType) {
		super(userName, sex, phone, job, email, address, postNum, model);
		// TODO Auto-generated constructor stub
		this.cardType = cardType;
	}
	}