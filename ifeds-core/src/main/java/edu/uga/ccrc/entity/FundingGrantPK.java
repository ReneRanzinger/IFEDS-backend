package edu.uga.ccrc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FundingGrantPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="dataset_id")
	Long datasetId;
	
	@Column(name="funding_source_id")
	Long fundingSourceId;
	
	@Column(name = "grant_number", length = 64, nullable = false)
	private String grantNumber;

	public Long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public Long getFundingSourceId() {
		return fundingSourceId;
	}

	public void setFundingSourceId(Long fundingSourceId) {
		this.fundingSourceId = fundingSourceId;
	}

	public String getGrantNumber() {
		return grantNumber;
	}

	public void setGrantNumber(String grantNumber) {
		this.grantNumber = grantNumber;
	}
}
