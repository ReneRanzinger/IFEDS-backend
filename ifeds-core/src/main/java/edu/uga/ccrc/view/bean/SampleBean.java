package edu.uga.ccrc.view.bean;

import edu.uga.ccrc.entity.Sample;

public class SampleBean{
	
	private Long sampleId;
	
	private Long sample_type_id;
	
	private String name;
	
    private String description;
		
    private String url;
    
    public SampleBean() {}
    
    public SampleBean(Sample sample) {
    	this.sampleId=sample.getSampleId();
    	this.sample_type_id = sample.getSampleId();
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
		return "SampleBean [sampleId=" + sampleId + ", name=" + name + ", description=" + description + ", url=" + url + "]";
	}

	public Long getSample_type_id() {
		return sample_type_id;
	}

	public void setSample_type_id(Long sample_type_id) {
		this.sample_type_id = sample_type_id;
	}
}
