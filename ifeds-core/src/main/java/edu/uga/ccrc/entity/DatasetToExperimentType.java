package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="dataset_to_experiment_type", schema="core")
public class DatasetToExperimentType {
	
	@EmbeddedId
	private DatasetToExperimentTypePK datasetToExperimentTypePK;
	
	@ManyToOne
    @MapsId("dataset_id")
    @JoinColumn(name = "dataset_id")
    Dataset dataset;
 
    @ManyToOne
    @MapsId("experiment_type_id")
    @JoinColumn(name = "experiment_type_id")
    ExperimentType experimentType;
	
	@Column(length=1000)
	private String description;
	
	public DatasetToExperimentType() {}

	public DatasetToExperimentTypePK getDatasetToExperimentTypePK() {
		return datasetToExperimentTypePK;
	}

	public void setDatasetToExperimentTypePK(DatasetToExperimentTypePK datasetToExperimentTypePK) {
		this.datasetToExperimentTypePK = datasetToExperimentTypePK;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public ExperimentType getExperimentType() {
		return experimentType;
	}

	public void setExperimentType(ExperimentType experimentType) {
		this.experimentType = experimentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
