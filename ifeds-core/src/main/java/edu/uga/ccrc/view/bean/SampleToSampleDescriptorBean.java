package edu.uga.ccrc.view.bean;

import edu.uga.ccrc.entity.SampleDescriptor;

public class SampleToSampleDescriptorBean {
	
	private SampleDescriptor sampleDescriptor;
	
	private String value;
	
	private String unitOfMeasurement;

	public SampleDescriptor getSampleDescriptor() {
		return sampleDescriptor;
	}

	public void setSampleDescriptor(SampleDescriptor sampleDescriptor) {
		this.sampleDescriptor = sampleDescriptor;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
}
