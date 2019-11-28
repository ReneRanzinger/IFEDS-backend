package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "funding_grant", schema = "core")
public class FundingGrant {
	
	@EmbeddedId
	private FundingGrantPK fundingGrantPK;
	
	@ManyToOne
    @MapsId("dataset_id")
    @JoinColumn(name = "dataset_id")
    Dataset dataset;
 
    @ManyToOne
    @MapsId("funding_source_id")
    @JoinColumn(name = "funding_source_id")
    FundingSource fundingSource;
    
    @Column(length=256)
	private String url;
	
	public FundingGrant() {}

	public FundingGrantPK getFundingGrantPK() {
		return fundingGrantPK;
	}

	public void setFundingGrantPK(FundingGrantPK fundingGrantPK) {
		this.fundingGrantPK = fundingGrantPK;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public FundingSource getFundingSource() {
		return fundingSource;
	}

	public void setFundingSource(FundingSource fundingSource) {
		this.fundingSource = fundingSource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
