package edu.uga.ccrc.view.bean;

import java.util.List;

public class CreateSampleHelperBean {
	
	
	private Long sampleId;
	
	private Long sample_type_id;
	
    private List<Long> sample_descriptor_id;
    
	private String name;
	
    private String description;
		
    private String url;

	/**
	 * @return the sampleId
	 */
	public Long getSampleId() {
		return sampleId;
	}

	/**
	 * @param sampleId the sampleId to set
	 */
	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}

	/**
	 * @return the sample_type_id
	 */
	public Long getSample_type_id() {
		return sample_type_id;
	}

	/**
	 * @param sample_type_id the sample_type_id to set
	 */
	public void setSample_type_id(Long sample_type_id) {
		this.sample_type_id = sample_type_id;
	}

	/**
	 * @return the sample_descriptor_id
	 */
	public List<Long> getSample_descriptor_id() {
		return sample_descriptor_id;
	}

	/**
	 * @param sample_descriptor_id the sample_descriptor_id to set
	 */
	public void setSample_descriptor_id(List<Long> sample_descriptor_id) {
		this.sample_descriptor_id = sample_descriptor_id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "CreateSampleBeanHelper [sampleId=" + sampleId + ", sample_type_id=" + sample_type_id
				+ ", sample_descriptor_id=" + sample_descriptor_id + ", name=" + name + ", description=" + description
				+ ", url=" + url + "]";
	}
    

}
