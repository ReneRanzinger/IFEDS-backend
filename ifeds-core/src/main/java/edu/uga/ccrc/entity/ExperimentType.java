package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="experiment_type", schema="core")
public class ExperimentType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="experiment_type_id", nullable=false)
	private Long experimentTypeId;
	
	@Column(length=256, nullable = false, unique = true)
	private String name;
	
	@Column(length=1000)
	private String description;
		
	@Column(length=256)
	private String url;
	
	//@OneToMany(mappedBy = "experimentType")
    //Set<DatasetToExperimentType> datasetToExperimentTypeSet;
	
	public ExperimentType() {}
	
	public ExperimentType(String name, String description, String url) {
		this.name = name;
		this.description = description;
		this.url = url;
	}

	public Long getExperimentTypeId() {
		return experimentTypeId;
	}

	public void setExperimentTypeId(Long experimentTypeId) {
		this.experimentTypeId = experimentTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
