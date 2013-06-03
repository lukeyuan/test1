package com.example.android.contact1;

public class MyContactDetails extends ContactDetails{
	
	private String cardType;
	private String ID;
	
	public MyContactDetails(String name, String mobileNum, String pattenid, String gender,
			String position, String email, String address, String postcode, String cardType)
	{
		super(name, mobileNum, pattenid, gender, position, email, address, postcode);
		this.cardType = cardType;	
	}
	
	public String getCardType()
	{
		return cardType;
	}
	
	private String getID() {
		return ID;
	}


	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
}
