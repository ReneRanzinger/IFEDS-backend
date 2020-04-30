package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="settings", schema="core"
	)
public class Settings {
	
	public Settings(String key, String value) {
		// TODO Auto-generated constructor stub
		
		this.key = key;
		this.value = value;
	}

	protected Settings() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="settings_id", nullable=false)
	private Long sr_no;
	
	
	@Column(name="key", length=100, nullable=false)
	private String key;
	
	
	@Column(name="value", length=100, nullable=false)
	private String value;


	public Long getSr_no() {
		return sr_no;
	}


	public void setSr_no(Long sr_no) {
		this.sr_no = sr_no;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
	
	
}
