package edu.uga.ccrc.view.bean;

import java.util.HashSet;
import java.util.Set;

import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.entity.SampleToSampleDescriptor;
import edu.uga.ccrc.entity.SampleType;

public class SampleWithDescriptorListBean {
	
	private Long sampleId;
	
	private String name;
	
	private long sample_type_id;
	
	private String sample_type_name;
	
	private String sample_type_description;
	
	private String sample_type_url;
	
    private String description;
		
    private String url;
    
    private Set<SampleToSampleDescriptorBean> sampleDescriptors;
    
    public SampleWithDescriptorListBean() {}
    
    public SampleWithDescriptorListBean(Sample sample) {
    	this.sampleId = sample.getSampleId();
    	this.name = sample.getName();
    	this.description = sample.getDescription();
    	this.url = sample.getUrl();
    	this.setSample_type_id(sample.getSampleType().getSampleTypeId());
    	this.setSample_type_name(sample.getSampleType().getName());
    	this.setSample_type_description(sample.getSampleType().getDescription());
    	this.setSample_type_url(sample.getSampleType().getUrl());
    	
    	sampleDescriptors=new HashSet<SampleToSampleDescriptorBean>();
    	Set<SampleToSampleDescriptor> ssd=sample.getSampleToSampleDescriptors();
    	for(SampleToSampleDescriptor s: ssd) {
    		SampleToSampleDescriptorBean b = new SampleToSampleDescriptorBean();
    		b.setSampleDescriptor(s.getSampleDescriptor());
    		b.setValue(s.getSampleToSampleDescPK().getSampleDescriptorValue());
    		b.setUnitOfMeasurement(s.getUnitOfMeasurement());
    		sampleDescriptors.add(b);
    	}
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

	public Set<SampleToSampleDescriptorBean> getSampleDescriptors() {
		return sampleDescriptors;
	}

	public void setSampleDescriptors(Set<SampleToSampleDescriptorBean> sampleDescriptors) {
		this.sampleDescriptors = sampleDescriptors;
	}

	public long getSample_type_id() {
		return sample_type_id;
	}

	public void setSample_type_id(Long long1) {
		this.sample_type_id = long1;
	}

	public String getSample_type_name() {
		return sample_type_name;
	}

	public void setSample_type_name(String sample_type_name) {
		this.sample_type_name = sample_type_name;
	}

	public String getSample_type_description() {
		return sample_type_description;
	}

	public void setSample_type_description(String sample_type_description) {
		this.sample_type_description = sample_type_description;
	}

	public String getSample_type_url() {
		return sample_type_url;
	}

	public void setSample_type_url(String sample_type_url) {
		this.sample_type_url = sample_type_url;
	}
    
    /*public void setDescriptor(Set<SampleToSampleDescriptor> sampleToSampleDesc){
		Set<SampleDescriptor> sd=new HashSet<SampleDescriptor>();
		for(SampleToSampleDescriptor ssd : sampleToSampleDesc) {
			sd.add(ssd.getSampleDescriptor());
		}
		setSampleDescriptors(sd);
	}*/

}
