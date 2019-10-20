 package edu.uga.ccrc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.uga.ccrc.entity.Dataset;

public interface DatasetDAO extends JpaRepository<Dataset,Long>{
	
	@Query(value="SELECT * FROM core.dataset where is_public = TRUE",nativeQuery=true)
	Iterable<Dataset> findPublicDatasets();
	
	@Query(value="SELECT * FROM core.dataset where is_public = TRUE OR provider_id = ?1",nativeQuery=true)
	Iterable<Dataset> findPublicAndProviderDatasets(Long pid);
}
