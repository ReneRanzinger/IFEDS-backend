package edu.uga.ccrc.view.bean;

import java.io.Serializable;

public class JwtResponseBean implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String permission_level;
	
	public JwtResponseBean(String jwttoken, String permission_level) {
		this.jwttoken = jwttoken;
		this.permission_level = permission_level;
	}
	
	public String getToken() {
		return this.jwttoken;
	}

	public String getPermission_level() {
		return permission_level;
	}
}