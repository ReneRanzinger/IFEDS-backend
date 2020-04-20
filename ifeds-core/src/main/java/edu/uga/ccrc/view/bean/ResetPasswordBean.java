package edu.uga.ccrc.view.bean;

public class ResetPasswordBean {
	private String username;
	private String email;
	private String new_password;
	private String confnew_password;
	
	public ResetPasswordBean(){
		
	}
	
	public ResetPasswordBean(ResetPasswordBean r) {
		this.username = r.getUsername();
		this.email = r.getEmail();
		this.new_password=r.getNew_password();
		this.confnew_password=r.getConfNew_password();
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
	public String getNew_password() {
		return new_password;
	}
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
	public String getConfNew_password() {
		return confnew_password;
	}
	public void setConfNew_password(String confnew_password) {
		this.confnew_password = confnew_password;
	}

	@Override
	public String toString() {
		return "ResetPasswordBean [username=" + username + ", email=" + email + ", new_password=" + new_password
				+ ", confnew_password=" + confnew_password + "]";
	}
}
