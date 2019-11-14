package edu.uga.ccrc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DatasetToExperimentTypePK implements Serializable{
	
	private static final long serialVersionUID = -781291215757310907L;

	@Column(name="dataset_id")
	Long datasetId;
	
	@Column(name="experiment_type_id")
	Long experimentTypeId;

	public Long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public Long getExperimentTypeId() {
		return experimentTypeId;
	}

	public void setExperimentTypeId(Long experimentTypeId) {
		this.experimentTypeId = experimentTypeId;
	}	
}
