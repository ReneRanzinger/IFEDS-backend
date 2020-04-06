package edu.uga.ccrc.view.bean;

public class DatasetBean {
	
	private Long datasetId;
	
	private String datasetName;
	
	private String sampleName;
	
	private String providerName;
	
	private String description;
	
	private int num_of_files;

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

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNum_of_files() {
		return num_of_files;
	}

	public void setNum_of_files(int num_of_files) {
		this.num_of_files = num_of_files;
	}

}
