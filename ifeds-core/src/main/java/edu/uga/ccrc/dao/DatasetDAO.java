 package edu.uga.ccrc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;

public interface DatasetDAO extends JpaRepository<Dataset,Long>{
	
	@Query(value="SELECT * FROM core.dataset where is_public = TRUE",nativeQuery=true)
	Iterable<Dataset> findPublicDatasets();
	
	@Query(value="SELECT * FROM core.dataset where is_public = TRUE OR provider_id = ?1",nativeQuery=true)
	Iterable<Dataset> findPublicAndProviderDatasets(Long pid);
	
	void deleteById(long id);

	List<Dataset> findByName(String name);
	
	List<Dataset> findByDatasetId(Long datasetId);
	
	@Query(value="SELECT * FROM core.dataset where dataset_id=?1 AND provider_id=?2",nativeQuery=true)
	Iterable<Dataset> checkforProviderDataset(Long did,Long pid);
	
	//List<Dataset> findByProvider(Provider provider);
}
