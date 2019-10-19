package edu.uga.ccrc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.Sample;

@Repository
public interface SampleDAO extends CrudRepository<Sample, Integer> {

	 @Query(value = "SELECT * FROM core.Sample WHERE provider_id = :provider_id", nativeQuery = true)
	 public List<Sample> findSampleByProviderId(Long provider_id);
	
	
}

