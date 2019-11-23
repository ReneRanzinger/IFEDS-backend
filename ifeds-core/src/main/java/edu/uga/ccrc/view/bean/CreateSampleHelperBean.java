package edu.uga.ccrc.view.bean;

import java.util.List;

import edu.uga.ccrc.entity.SampleDescriptor;

public class CreateSampleHelperBean {
	
	
	private Long sampleId;
	
	private Long sample_type_id;
	
    private List<CreateSampleToSampleDescriptorHelperBean> sample_descriptors;
    
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

	public List<CreateSampleToSampleDescriptorHelperBean> getSample_descriptors() {
		return sample_descriptors;
	}

	public void setSample_descriptors(List<CreateSampleToSampleDescriptorHelperBean> sample_descriptors) {
		System.out.println("In ser : "+sample_descriptors.toString());
		this.sample_descriptors = sample_descriptors;
	}



	/**
	 * @return the sample_descriptors
	 */


}
