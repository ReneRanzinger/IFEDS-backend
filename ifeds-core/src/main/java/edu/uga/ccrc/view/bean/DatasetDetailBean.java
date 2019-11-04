package edu.uga.ccrc.view.bean;

import java.util.Set;

import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.entity.FundingSource;
import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.Paper;

public class DatasetDetailBean{
	
	private Long datasetId;
	
	private String datasetName;
	
	private String description;
	
	private SampleBean sample;
	
	private ProviderBean provider;
	
	private Set<ExperimentType> experimentTypeSet;
	
	private Set<Paper> papers;
	
	private Set<Keyword> keywords;
	
	private Set<FundingSource> fundingSource;
	
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
	
	public SampleBean getSample() {
		return sample;
	}

	public void setSample(SampleBean sample) {
		this.sample = sample;
	}

	public ProviderBean getProvider() {
		return provider;
	}

	public void setProvider(ProviderBean provider) {
		this.provider = provider;
	}

	public Set<ExperimentType> getExperimentTypeSet() {
		return experimentTypeSet;
	}

	public void setExperimentTypeSet(Set<ExperimentType> experimentTypeSet) {
		this.experimentTypeSet = experimentTypeSet;
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

	public Set<FundingSource> getFundingSource() {
		return fundingSource;
	}

	public void setFundingSource(Set<FundingSource> fundingSource) {
		this.fundingSource = fundingSource;
	}

	public Set<DataFileBean> getDataFiles() {
		return dataFiles;
	}

	public void setDataFiles(Set<DataFileBean> dataFiles) {
		this.dataFiles = dataFiles;
	}
}
