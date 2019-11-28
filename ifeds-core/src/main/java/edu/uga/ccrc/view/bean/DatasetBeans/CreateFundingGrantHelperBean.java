package edu.uga.ccrc.view.bean.DatasetBeans;

public class CreateFundingGrantHelperBean {

		Long funding_source_id;
		
		String grant_number;
		
		String url;

		public Long getFunding_source_id() {
			return funding_source_id;
		}

		public void setFunding_source_id(Long funding_source_id) {
			this.funding_source_id = funding_source_id;
		}

		public String getGrant_number() {
			return grant_number;
		}

		public void setGrant_number(String grant_number) {
			this.grant_number = grant_number;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
}
