package edu.uga.ccrc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SampleToSampleDescriptorPK implements Serializable{
	
	private static final long serialVersionUID = 6051344441187166882L;

	@Column(name="sample_id")
	private Long sampleId;
	
	@Column(name = "sample_descriptor_id")
	private Long sampleDescriptorId;
	
	@Column(name="sample_descriptor_value", length=64, nullable=false)
	private String sampleDescriptorValue;
	
	public SampleToSampleDescriptorPK() {}
	
	public SampleToSampleDescriptorPK(Long sampleId, Long sampleDescriptorId, String sampleDescriptorValue) {
		this.sampleId=sampleId;
		this.sampleDescriptorId=sampleDescriptorId;
		this.sampleDescriptorValue=sampleDescriptorValue;
	}

	public Long getSampleId() {
		return sampleId;
	}

	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}

	public Long getSampleDescriptorId() {
		return sampleDescriptorId;
	}

	public void setSampleDescriptorId(Long sampleDescriptorId) {
		this.sampleDescriptorId = sampleDescriptorId;
	}

	public String getSampleDescriptorValue() {
		return sampleDescriptorValue;
	}

	public void setSampleDescriptorValue(String sampleDescriptorValue) {
		this.sampleDescriptorValue = sampleDescriptorValue;
	}

}
