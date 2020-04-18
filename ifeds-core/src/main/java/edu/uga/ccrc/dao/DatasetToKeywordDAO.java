package edu.uga.ccrc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.DatasetToExperimentType;
import edu.uga.ccrc.entity.DatasetToKeyword;

@Repository
public interface DatasetToKeywordDAO extends CrudRepository<DatasetToKeyword, Long> {

	@Transactional
	@Modifying
	@Query(value="DELETE FROM core.dataset_to_keyword where dataset_id = ?1",nativeQuery=true)
	public void deleteDatasetToKeywordByDatasetId(Long id);

	@Query(value="SELECT * FROM core.dataset_to_keyword where keyword_id = ?1",nativeQuery=true)
	public List<DatasetToKeywordDAO> findKeywordPresent(Long id);
}
