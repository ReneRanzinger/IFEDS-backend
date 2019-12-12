package edu.uga.ccrc.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.DatasetToExperimentType;
import edu.uga.ccrc.entity.ExperimentType;

@Repository
public interface DatasetToExperimentTypeDAO extends CrudRepository<DatasetToExperimentType, Long> {
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM core.dataset_to_experiment_type where dataset_id = ?1",nativeQuery=true)
	public void deleteDatasetToExperimentTypeByDatasetId(Long id);

}

