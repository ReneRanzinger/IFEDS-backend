package edu.uga.ccrc.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.SampleType;


@Repository
public interface SampleTypeDAO extends CrudRepository<SampleType, Long>{
	
	public List<SampleType> findAll();
	
	@Query(value="SELECT * FROM core.sample_type where sample_type_id = ?1",nativeQuery=true)
	SampleType findSampleTypeById(Long sampleTypeId);
	

}
