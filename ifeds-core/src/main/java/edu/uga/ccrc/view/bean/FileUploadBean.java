package edu.uga.ccrc.view.bean;

public class FileUploadBean {

	private Long fileId;
	
	private String message;

	public FileUploadBean(Long dataFileId, String string) {
		// TODO Auto-generated constructor stub
		this.fileId = dataFileId;
		this.message = string;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
