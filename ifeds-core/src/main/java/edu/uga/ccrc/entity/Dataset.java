package edu.uga.ccrc.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="dataset", schema="core")
public class Dataset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dataset_id", nullable=false)
	private Long datasetId;
	
	@Column(length=256, nullable = false, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name="sample_id")
	private Sample sample;
	
	@ManyToOne
	@JoinColumn(name="provider_id")
	private Provider provider;
	
	@Column(length=1000)
	private String description;
	
	@Column(name="is_public",nullable=false)
	private Boolean isPublic;
	
	@OneToMany(mappedBy = "dataset")
    Set<DatasetToExperimentType> datasetToExperimentTypes;
	
	@OneToMany(mappedBy = "dataset")
    Set<DatasetToPaper> datasetToPapers;
	
	@OneToMany(mappedBy = "dataset")
    Set<DatasetToKeyword> datasetToKeywords;
	
	@OneToMany(mappedBy = "dataset")
    Set<FundingGrant> fundingGrant;
	
	@OneToMany(mappedBy = "dataset")
    Set<DataFile> dataFiles;

	public Dataset() {}

	public Long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Set<DatasetToExperimentType> getDatasetToExperimentTypes() {
		return datasetToExperimentTypes;
	}

	public void setDatasetToExperimentTypes(Set<DatasetToExperimentType> datasetToExperimentTypes) {
		this.datasetToExperimentTypes = datasetToExperimentTypes;
	}

	public Set<DatasetToPaper> getDatasetToPapers() {
		return datasetToPapers;
	}

	public void setDatasetToPapers(Set<DatasetToPaper> datasetToPapers) {
		this.datasetToPapers = datasetToPapers;
	}

	public Set<DatasetToKeyword> getDatasetToKeywords() {
		return datasetToKeywords;
	}

	public void setDatasetToKeywords(Set<DatasetToKeyword> datasetToKeywords) {
		this.datasetToKeywords = datasetToKeywords;
	}

	public Set<FundingGrant> getFundingGrant() {
		return fundingGrant;
	}

	public void setFundingGrant(Set<FundingGrant> fundingGrant) {
		this.fundingGrant = fundingGrant;
	}

	public Set<DataFile> getDataFiles() {
		return dataFiles;
	}

	public void setDataFiles(Set<DataFile> dataFiles) {
		this.dataFiles = dataFiles;
	}
}
