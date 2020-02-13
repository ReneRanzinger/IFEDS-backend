package edu.uga.ccrc.view.bean;

import edu.uga.ccrc.entity.DataFile;

public class DataFileBean{
	
	private Long dataFileId;
	
	private DataTypeBean dataType;
	
	private String origFileName;
	
	private String description;
	
	private Long data_file_size;
	
	public DataFileBean(DataFile f) {
		this.dataFileId=f.getDataFileId();
		this.dataType=new DataTypeBean(f.getDataType());
		this.origFileName=f.getOrigFileName();
		this.description=f.getDescription();
	}

	public Long getDataFileId() {
		return dataFileId;
	}

	public void setDataFileId(Long dataFileId) {
		this.dataFileId = dataFileId;
	}

	public DataTypeBean getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeBean dataType) {
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
