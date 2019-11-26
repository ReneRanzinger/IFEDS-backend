package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.DatasetToExperimentType;
import edu.uga.ccrc.entity.ExperimentType;

@Repository
public interface DatasetToExperimentTypeDAO extends CrudRepository<DatasetToExperimentType, Long> {
	
	

}

