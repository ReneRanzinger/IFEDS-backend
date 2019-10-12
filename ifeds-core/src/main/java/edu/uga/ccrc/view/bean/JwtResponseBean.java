package edu.uga.ccrc.view.bean;

import java.io.Serializable;

public class JwtResponseBean implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	
	public JwtResponseBean(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	
	public String getToken() {
		return this.jwttoken;
	}
}