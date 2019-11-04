package edu.uga.ccrc.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="dataset_to_keyword", schema="core")
public class DatasetToKeyword {
	
	@EmbeddedId
	private DatasetToKeywordPK datasetToKeywordPK;
	
	@ManyToOne
    @MapsId("dataset_id")
    @JoinColumn(name = "dataset_id")
    Dataset dataset;
 
    @ManyToOne
    @MapsId("keyword_id")
    @JoinColumn(name = "keyword_id")
    Keyword keyword;
	
	protected DatasetToKeyword() {}

	public DatasetToKeywordPK getDatasetToKeywordPK() {
		return datasetToKeywordPK;
	}

	public void setDatasetToKeywordPK(DatasetToKeywordPK datasetToKeywordPK) {
		this.datasetToKeywordPK = datasetToKeywordPK;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
}
