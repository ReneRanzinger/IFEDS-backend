package edu.uga.ccrc.view.bean;

public class SampleDescriptorBean {
	private Long sample_descriptor_id;
	private String name;
	private String descriptor;
	private String url;
	private String namespace;
	private String link_pattern;
	public Long getSample_descriptor_id() {
		return sample_descriptor_id;
	}
	public void setSample_descriptor_id(Long sample_descriptor_id) {
		this.sample_descriptor_id = sample_descriptor_id;
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
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getLink_pattern() {
		return link_pattern;
	}
	public void setLink_pattern(String link_pattern) {
		this.link_pattern = link_pattern;
	}
	

}
