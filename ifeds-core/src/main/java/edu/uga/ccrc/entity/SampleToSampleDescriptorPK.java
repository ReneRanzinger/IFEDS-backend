package edu.uga.ccrc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SampleToSampleDescriptorPK implements Serializable{
	
	@ManyToOne(targetEntity = Sample.class)
	@JoinColumn(name="sample_id",referencedColumnName = "sample_id")
	private Sample sample;
	
	@ManyToOne(targetEntity = SampleDescriptor.class)
	@JoinColumn(name="sample_descriptor_id", referencedColumnName = "sample_descriptor_id")
	private SampleDescriptor sampleDescriptor;
	
	@Column(name="sample_descriptor_value", length=64, nullable=false)
	private String sampleDescriptorValue;
	
	public SampleToSampleDescriptorPK(Sample sample, SampleDescriptor sampleDescriptor, String sampleDescriptorValue) {
		this.sample = sample;
		this.sampleDescriptor = sampleDescriptor;
		this.sampleDescriptorValue = sampleDescriptorValue;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public SampleDescriptor getSampleDescriptor() {
		return sampleDescriptor;
	}

	public void setSampleDescriptor(SampleDescriptor sampleDescriptor) {
		this.sampleDescriptor = sampleDescriptor;
	}

	public String getSampleDescriptorValue() {
		return sampleDescriptorValue;
	}

	public void setSampleDescriptorValue(String sampleDescriptorValue) {
		this.sampleDescriptorValue = sampleDescriptorValue;
	}

}
