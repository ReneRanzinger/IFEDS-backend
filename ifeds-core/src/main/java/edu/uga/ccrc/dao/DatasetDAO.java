 package edu.uga.ccrc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;

public interface DatasetDAO extends JpaRepository<Dataset,Long>{
	
	@Query(value="SELECT * FROM core.dataset where is_public = TRUE",nativeQuery=true)
	Iterable<Dataset> findPublicDatasets();
	
	@Query(value="SELECT * FROM core.dataset where is_public = TRUE OR provider_id = ?1",nativeQuery=true)
	Iterable<Dataset> findPublicAndProviderDatasets(Long pid);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM core.dataset WHERE dataset_id=:id",nativeQuery=true)
	void deleteById(Long id);

	@Query(value="SELECT * FROM core.dataset WHERE name=:name",nativeQuery=true)
	List<Dataset> findByDatasetName(String name);
	
	List<Dataset> findByDatasetId(Long datasetId);
	
	@Query(value="SELECT * FROM core.dataset where dataset_id=?1 AND (is_public=TRUE OR provider_id=?2)",nativeQuery = true)
	Iterable<Dataset> findPublicOrProviderDataset(Long did,Long pid);
	
	@Query(value="SELECT count(*) FROM core.dataset WHERE provider_id=?1",nativeQuery=true)
	int findByProvider(Long provider_id);
	
	
}
