package edu.uga.ccrc.view.bean;

import java.util.List;

import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.entity.SampleToSampleDescriptor;

public class SampleBean{
	
	private Long sampleId;
	
	private String sampleTypeName;
	
	private Long sampleTypeId;
	
	private String name;
	
    private String description;
		
    private String url;
    
    private List<SampleToSampleDescriptorBean> sampleToSameDescriptors;
    
    public SampleBean() {}
    
    public SampleBean(Sample sample) {
    	this.sampleId=sample.getSampleId();
    	this.sampleTypeId = sample.getSampleType().getSampleTypeId();
		this.name=sample.getName();
		this.description=sample.getDescription();
		this.url=sample.getUrl();
    }

	public Long getSampleId() {
		return sampleId;
	}

	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
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

	

	@Override
	public String toString() {
		return "SampleBean [sampleId=" + sampleId + ", sampleTypeId=" + sampleTypeId + ", name=" + name
				+ ", description=" + description + ", url=" + url + "]";
	}

	public String getSampleTypeName() {
		return sampleTypeName;
	}

	public void setSampleTypeName(String sampleTypeName) {
		this.sampleTypeName = sampleTypeName;
	}

	public Long getSampleTypeId() {
		return sampleTypeId;
	}

	public void setSampleTypeId(Long sampleTypeId) {
		this.sampleTypeId = sampleTypeId;
	}

	public List<SampleToSampleDescriptorBean> getSampleToSameDescriptorBean() {
		return sampleToSameDescriptors;
	}

	public void setSampleToSameDescriptorBean(List<SampleToSampleDescriptorBean> sampleToSameDescriptors) {
		this.sampleToSameDescriptors = sampleToSameDescriptors;
	}


}
