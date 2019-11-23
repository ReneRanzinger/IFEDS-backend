package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;

import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.entity.SampleToSampleDescriptor;
import edu.uga.ccrc.entity.SampleToSampleDescriptorPK;

public interface SampleToSampleDescriptorDAO extends CrudRepository<SampleToSampleDescriptor, Long>  {

	public SampleToSampleDescriptor save(SampleToSampleDescriptor sampleToSampleDescriptor);
	

}
