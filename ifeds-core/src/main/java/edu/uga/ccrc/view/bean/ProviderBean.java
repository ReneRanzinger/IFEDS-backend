package edu.uga.ccrc.view.bean;

public class ProviderBean {
	
	private Long providerId;
	private String name;
	private String providerGroup;
	private String department;
	private String affiliation;
	private String url;
	private String contact;
	private String username;
	private String password;
	private String email;
	private String authToken;
	private Integer authTimeOut;
	
	public void setUserName(String username)
	{
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
			

}
