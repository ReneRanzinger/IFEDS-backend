package edu.uga.ccrc.view.bean;

public class SampleTypeBean {
	
	private Long sampleTypeId;
	private String name;
	private String descriptor;
	private String url;
	
	public Long getSampleTypeId() {
		return sampleTypeId;
	}
	public void setSampleTypeId(Long sampleTypeId) {
		this.sampleTypeId = sampleTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "SampleTypeBean [sampleTypeId=" + sampleTypeId + ", name=" + name + ", descriptor=" + descriptor
				+ ", url=" + url + "]";
	}
	

}
