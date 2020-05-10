package edu.uga.ccrc.view.bean;

public class PermissionsBean {
	
	public PermissionsBean() {
		
	}
	private String username;
	private String email;
	private Long provider_id;
	private String permission_level;
	private boolean active;
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
	public Long getProvider_id() {
		return provider_id;
	}
	public void setProvider_id(Long provider_id) {
		this.provider_id = provider_id;
	}
	public String getPermission_level() {
		return permission_level;
	}
	public void setPermission_level(String permission_level) {
		this.permission_level = permission_level;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}


}
