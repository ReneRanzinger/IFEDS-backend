package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="permissions", schema="core")
public class Permissions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="permissions_id")
	private Long permissions_id;
	
	@OneToOne
	@JoinColumn(name="provider_id")
	private Provider provider;

	
	@Column(length=256)
	private String permission_level;


	public Long getPermissions_id() {
		return permissions_id;
	}


	public void setPermissions_id(Long permissions_id) {
		this.permissions_id = permissions_id;
	}


	public Provider getProvider() {
		return provider;
	}


	public void setProvider(Provider provider) {
		this.provider = provider;
	}


	public String getPermission_level() {
		return permission_level;
	}


	public void setPermission_level(String permission_level) {
		this.permission_level = permission_level;
	}


	@Override
	public String toString() {
		return "Permissions [permissions_id=" + permissions_id + ", provider=" + provider + ", permission_level="
				+ permission_level + "]";
	}

}
