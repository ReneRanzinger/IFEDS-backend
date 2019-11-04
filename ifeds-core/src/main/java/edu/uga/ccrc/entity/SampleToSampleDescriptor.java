package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sample_to_sample_descriptor", schema="core")
public class SampleToSampleDescriptor {
	
	@EmbeddedId
	private SampleToSampleDescriptorPK sampleToSampleDescPK;
	
	@Column(name="unit_of_measurement", length=256)
	private String unitOfMeasurement;
	
	protected SampleToSampleDescriptor() {}

	public SampleToSampleDescriptorPK getSampleToSampleDescPK() {
		return sampleToSampleDescPK;
	}

	public void setSampleToSampleDescPK(SampleToSampleDescriptorPK sampleToSampleDescPK) {
		this.sampleToSampleDescPK = sampleToSampleDescPK;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	
}
