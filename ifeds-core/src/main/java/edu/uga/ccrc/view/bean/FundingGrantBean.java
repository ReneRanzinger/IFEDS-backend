package edu.uga.ccrc.view.bean;

import edu.uga.ccrc.entity.FundingSource;

public class FundingGrantBean {
	
	private FundingSource fundingSource;
	
	private String grantNumber;

	public FundingSource getFundingSource() {
		return fundingSource;
	}

	public void setFundingSource(FundingSource fundingSource) {
		this.fundingSource = fundingSource;
	}

	public String getGrantNumber() {
		return grantNumber;
	}

	public void setGrantNumber(String grantNumber) {
		this.grantNumber = grantNumber;
	}
}
