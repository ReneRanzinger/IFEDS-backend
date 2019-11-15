package edu.uga.ccrc.view.bean;

import edu.uga.ccrc.entity.ExperimentType;

public class DatasetToExperimentTypeBean {
	
	private ExperimentType experimentType;
	
	private String description;

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
