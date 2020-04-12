package edu.uga.ccrc.view.bean;

public class ChangePasswordBean {
	private String username;
	private String email;
	private String old_password;
	private String new_password;

	
	public ChangePasswordBean() {
		
	}
	
	public ChangePasswordBean(ChangePasswordBean u) {
		this.username = u.getUsername();
		this.email=u.getEmail();
		this.old_password=u.getOld_password();
		this.new_password=u.getNew_password();
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
	public String getOld_password() {
		return old_password;
	}
	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}
	public String getNew_password() {
		return new_password;
	}
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
}
