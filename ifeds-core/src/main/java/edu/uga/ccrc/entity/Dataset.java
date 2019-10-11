package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="dataset", schema="core")
public class Dataset {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	protected Dataset() {}

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
}
