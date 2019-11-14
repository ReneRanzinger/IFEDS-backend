package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="sample_to_sample_descriptor", schema="core")
public class SampleToSampleDescriptor {
	
	@EmbeddedId
	private SampleToSampleDescriptorPK sampleToSampleDescPK;
	
	@ManyToOne
    @MapsId("sample_id")
    @JoinColumn(name = "sample_id")
    Sample sample;
	
	@ManyToOne
    @MapsId("sample_descriptor_id")
    @JoinColumn(name = "sample_descriptor_id")
	SampleDescriptor sampleDescriptor;
	
	@Column(name="unit_of_measurement", length=256)
	private String unitOfMeasurement;
	
	protected SampleToSampleDescriptor() {}

	public SampleToSampleDescriptorPK getSampleToSampleDescPK() {
		return sampleToSampleDescPK;
	}

	public void setSampleToSampleDescPK(SampleToSampleDescriptorPK sampleToSampleDescPK) {
		this.sampleToSampleDescPK = sampleToSampleDescPK;
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

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	
}
