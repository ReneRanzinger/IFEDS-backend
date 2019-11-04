package edu.uga.ccrc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DatasetToPaperPK implements Serializable{
	
	private static final long serialVersionUID = 4852968480156595611L;

	@Column(name="dataset_id")
	Long datasetId;
	
	@Column(name="paper_id")
	Long paperId;

	public Long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
}
