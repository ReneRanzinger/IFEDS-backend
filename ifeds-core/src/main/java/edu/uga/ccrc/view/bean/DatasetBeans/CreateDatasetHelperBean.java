package edu.uga.ccrc.view.bean.DatasetBeans;

import java.util.List;

public class CreateDatasetHelperBean {
	
	public String datasetName;
	
	public String  description;
	
	public Long sampleIds;
	
	public boolean is_public;
	
	List<CreateDatasetToExperimentTypeHelperBean> experiment_types;
	
	List<CreateFundingGrantHelperBean> funding_grant;
	
	List<Long> keywordsIds;
	
	List<Long> paperIds;

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public List<CreateDatasetToExperimentTypeHelperBean> getExperiment_types() {
		return experiment_types;
	}

	public void setExperiment_types(List<CreateDatasetToExperimentTypeHelperBean> experiment_types) {
		this.experiment_types = experiment_types;
	}

	public List<CreateFundingGrantHelperBean> getFunding_grant() {
		return funding_grant;
	}

	public void setFunding_grant(List<CreateFundingGrantHelperBean> funding_sources) {
		this.funding_grant = funding_sources;
	}

	public List<Long> getKeywordsIds() {
		return keywordsIds;
	}

	public void setKeywordsIds(List<Long> keywordsIds) {
		this.keywordsIds = keywordsIds;
	}

	public List<Long> getPaperIds() {
		return paperIds;
	}

	public void setPaperIds(List<Long> paperIds) {
		this.paperIds = paperIds;
	}
	




	public Long getSampleIds() {
		return sampleIds;
	}

	public void setSampleIds(Long sampleIds) {
		this.sampleIds = sampleIds;
	}

	@Override
	public String toString() {
		return "CreateDatasetHelperBean [datasetName=" + datasetName + ", description=" + description + ", sampleIds="
				+ sampleIds + ", experiment_types=" + experiment_types + ", funding_sources=" + funding_grant
				+ ", keywordsIds=" + keywordsIds + ", paperIds=" + paperIds + "]";
	}

	public boolean isIs_public() {
		return is_public;
	}

	public void setIs_public(boolean is_public) {
		this.is_public = is_public;
	}

	
	
	

}
