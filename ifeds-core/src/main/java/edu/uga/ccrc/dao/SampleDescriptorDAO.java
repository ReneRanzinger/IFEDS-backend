package edu.uga.ccrc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.entity.SampleType;

@Repository
public interface SampleDescriptorDAO extends CrudRepository<SampleDescriptor, Long> {

	public List<SampleDescriptor> findAll();
	
	@Query(value="SELECT * FROM core.sample_descriptor where sample_descriptor_id = ?1",nativeQuery=true)
	SampleDescriptor findSampleDescriptorById(Long sampleSampleDescriptorId);
	
}
