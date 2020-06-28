/**
 * @author Susan George
 */

package edu.uga.ccrc.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.uga.ccrc.view.bean.ProviderBean;

@Entity
@Table(name="provider",schema="core")
public class Provider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="provider_id")
	private Long providerId;
	
	@Column(length=64, nullable=false)
	private String name;
	
	@Column(name="provider_group", length=64)
	private String providerGroup;
	
	@Column(length=64)
	private String department;
	
	@Column(length=64)
	private String affiliation;
	
	@Column(length=256)
	private String url;
	
	@Column(length=32)
	private String contact;
	
	@Column(length=64, nullable=false, unique=true)
	private String username;
	
	@Column(length=64, nullable=false)
	private String password;
	
	@Column(length=32, nullable=false, unique=true)
	private String email;
		
	@Column(name="auth_time_out")
	private Integer authTimeOut;
	

	@Column(name="active")
	private boolean active = true;

	@Column(name="auth_token")
	private String reset_token;


	@OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    Set<Sample> samples;
	
	@OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    Set<Dataset> datasets;
	
	
	
	public Provider() {}


	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProviderGroup() {
		return providerGroup;
	}

	public void setProviderGroup(String providerGroup) {
		this.providerGroup = providerGroup;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getAuthTimeOut() {
		return authTimeOut;
	}

	public void setAuthTimeOut(Integer authTimeOut) {
		this.authTimeOut = authTimeOut;
	}

	public Set<Sample> getSamples() {
		return samples;
	}

	public void setSamples(Set<Sample> samples) {
		this.samples = samples;
	}

	public Set<Dataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(Set<Dataset> datasets) {
		this.datasets = datasets;
	}
	
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public String getReset_token() {
		return reset_token;
	}


	public void setReset_token(String reset_token) {
		this.reset_token = reset_token;
	}
}
