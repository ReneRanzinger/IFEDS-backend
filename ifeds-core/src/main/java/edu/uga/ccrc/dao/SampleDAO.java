package edu.uga.ccrc.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.view.bean.SampleBean;

@Repository
public interface SampleDAO extends CrudRepository<Sample, Long> {

	 @Query(value = "SELECT * FROM core.Sample WHERE provider_id = :provider_id", nativeQuery = true)
	 public List<Sample> findSampleByProviderId(Long provider_id);
	 
	
	 public Sample save(Sample newSample);
	 
	 @Transactional
	 @Modifying
	 @Query(value="DELETE FROM core.sample WHERE sample_id=:id",nativeQuery=true)
	 void deleteById(Long id);
	 
	 public Optional<Sample> findById(Long id);

	 
	 @Query(value = "SELECT * FROM core.Sample WHERE name = :name", nativeQuery = true)
	 public List<Object> findByName(String name);
	 
	
	 
	 
	
}

