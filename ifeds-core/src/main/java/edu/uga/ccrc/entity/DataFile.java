package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="data_file", schema="core"
	)
public class DataFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="data_file_id", nullable=false)
	private Long dataFileId;
	
	@ManyToOne
	@JoinColumn(name="dataset_id")
	private Dataset dataset;
	
	@ManyToOne
	@JoinColumn(name="data_type_id")
	private DataType dataType;
	
	@Column(name="original_file_name", length=64, nullable=false)
	private String origFileName;
	
	
	@Column(name="description", length=1000)
	private String description;
	
	@Column(name="data_file_size")
	private Long data_file_size;
	
	public DataFile() {}

	public Long getDataFileId() {
		return dataFileId;
	}

	public void setDataFileId(Long dataFileId) {
		this.dataFileId = dataFileId;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getOrigFileName() {
		return origFileName;
	}

	public void setOrigFileName(String origFileName) {
		this.origFileName = origFileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSize() {
		return data_file_size;
	}

	public void setSize(Long size) {
		this.data_file_size = size;
	}
}
