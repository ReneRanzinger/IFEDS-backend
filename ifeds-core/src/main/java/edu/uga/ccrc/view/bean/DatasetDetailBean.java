 package edu.uga.ccrc.view.bean;

import java.util.Set;

import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.Paper;

public class DatasetDetailBean{
	
	private Long datasetId;
	
	private String datasetName;
	
	private String description;
	
	private SampleWithDescriptorListBean sample;
	
	private ProviderBean provider;
	
	private Set<DatasetToExperimentTypeBean> experimentTypes;
	
	private Set<Paper> papers;
	
	private Set<Keyword> keywords;
	
	private Set<FundingGrantBean> fundingSources;
	
	private Set<DataFileBean> dataFiles;

	public Long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

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
	
	public SampleWithDescriptorListBean getSample() {
		return sample;
	}

	public void setSample(SampleWithDescriptorListBean sample) {
		this.sample = sample;
	}

	public ProviderBean getProvider() {
		return provider;
	}

	public void setProvider(ProviderBean provider) {
		this.provider = provider;
	}

	public Set<DatasetToExperimentTypeBean> getExperimentTypes() {
		return experimentTypes;
	}

	public void setExperimentTypes(Set<DatasetToExperimentTypeBean> experimentTypes) {
		this.experimentTypes = experimentTypes;
	}

	public Set<Paper> getPapers() {
		return papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public Set<FundingGrantBean> getFundingSources() {
		return fundingSources;
	}

	public void setFundingSources(Set<FundingGrantBean> fundingSources) {
		this.fundingSources = fundingSources;
	}

	public Set<DataFileBean> getDataFiles() {
		return dataFiles;
	}

	public void setDataFiles(Set<DataFileBean> dataFiles) {
		this.dataFiles = dataFiles;
	}
}
