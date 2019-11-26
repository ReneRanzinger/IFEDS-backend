package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.entity.Provider;

@Repository
public interface ExperimentTypeDAO extends CrudRepository<ExperimentType, Long> {
	
	

}
