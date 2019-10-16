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
	
	public String getUserName()
	{
		return	 username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setProviderGroup(String providerGroup) {
		this.providerGroup = providerGroup;
	}
			
	public String getProviderGroup() {
		return providerGroup;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Integer getAuthTimeOut() {
		return authTimeOut;
	}

	public void setAuthTimeOut(Integer authTimeOut) {
		this.authTimeOut = authTimeOut;
	}

	
}
