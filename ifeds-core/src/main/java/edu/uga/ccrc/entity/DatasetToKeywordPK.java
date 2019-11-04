package edu.uga.ccrc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DatasetToKeywordPK implements Serializable{
	
	private static final long serialVersionUID = 1140311804249672947L;

	@Column(name="dataset_id")
	Long datasetId;
	
	@Column(name="keyword_id")
	Long keywordId;

	public Long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public Long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}
	
}
