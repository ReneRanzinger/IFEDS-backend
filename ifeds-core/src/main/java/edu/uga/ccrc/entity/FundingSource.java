package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="funding_source", schema="core")
public class FundingSource {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="funding_source_id", nullable=false)
	private Long fundingSourceId;
	
	@Column(length=64, nullable = false, unique = true)
	private String name;
	
	@Column(length=16)
	private String abbreviation;
	
		
	@Column(length=256)
	private String url;
	
	public FundingSource() {
	}

	public FundingSource(String name, String abbreviation, String url) {
		this.name = name;
		this.url = url;
		this.abbreviation = abbreviation;
	}

	
	public Long getFundingSourceId() {
		return fundingSourceId;
	}

	public void setFundingSourceId(Long fundingSourceId) {
		this.fundingSourceId = fundingSourceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
