package edu.uga.ccrc.view.bean;

import edu.uga.ccrc.entity.Provider;

public class ProviderBean{
	
	private Long providerId;
	private String name;
	private String providerGroup;
	private String department;
	private String affiliation;
	private String url;
	private String contact;
	private String username;
	private String email;
	
	public ProviderBean() {}
	
	public ProviderBean(Provider p) {
		this.providerId=p.getProviderId();
		this.name=p.getName();
		this.providerGroup=p.getProviderGroup();
		this.department=p.getDepartment();
		this.affiliation=p.getAffiliation();
		this.url=p.getUrl();
		this.contact=p.getContact();
		this.username=p.getUsername();
		this.email=p.getEmail();
		
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
}
