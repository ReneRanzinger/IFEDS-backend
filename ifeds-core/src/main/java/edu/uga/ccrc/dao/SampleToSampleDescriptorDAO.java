package edu.uga.ccrc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.entity.SampleToSampleDescriptor;
import edu.uga.ccrc.entity.SampleToSampleDescriptorPK;

@Repository
public interface SampleToSampleDescriptorDAO extends CrudRepository<SampleToSampleDescriptor, SampleToSampleDescriptorPK>  {

	public SampleToSampleDescriptor save(SampleToSampleDescriptor sampleToSampleDescriptor);
	
	@Transactional 
	@Modifying //You need to add @Modifying to declare that it is an update query and you don't expect a result back from the DB
	@Query(value="DELETE FROM core.sample_to_sample_descriptor where sample_id = ?1",nativeQuery=true)
	public void deleteSampleToSampleDescriptorBySampleId(Long id);
	
	@Query(value="SELECT * FROM core.sample_to_sample_descriptor where sample_id = ?1",nativeQuery=true)
	public List<SampleToSampleDescriptor> findSamepleDescriptorsBySampleId(Long id);

	@Query(value="SELECT * FROM core.sample_to_sample_descriptor where sample_descriptor_id = ?1",nativeQuery=true)
	public List<SampleToSampleDescriptor> findBySampleDescriptorId(Long id);

	

}
