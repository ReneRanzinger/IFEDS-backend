package edu.uga.ccrc.view.bean;

import edu.uga.ccrc.entity.DataType;

public class DataTypeBean{
	
	private Long dataTypeId;
	
	private String name;
	
	private String description;
	
	private String url;
	
	public DataTypeBean(DataType datatype) {
		this.dataTypeId=datatype.getDataTypeId();
		this.name=datatype.getName();
		this.description=datatype.getDescription();
		this.url=datatype.getUrl();
	}

	public Long getDataTypeId() {
		return dataTypeId;
	}

	public void setDataTypeId(Long dataTypeId) {
		this.dataTypeId = dataTypeId;
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
