package edu.uga.ccrc.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="dataset_to_paper", schema="core")
public class DatasetToPaper {
	
	@EmbeddedId
	private DatasetToPaperPK datasetToPaperPK;
	
	@ManyToOne
    @MapsId("dataset_id")
    @JoinColumn(name = "dataset_id")
    Dataset dataset;
 
    @ManyToOne
    @MapsId("paper_id")
    @JoinColumn(name = "paper_id")
    Paper paper;
	
	protected DatasetToPaper() {}

	public DatasetToPaperPK getDatasetToPaperPK() {
		return datasetToPaperPK;
	}

	public void setDatasetToPaperPK(DatasetToPaperPK datasetToPaperPK) {
		this.datasetToPaperPK = datasetToPaperPK;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}
}
