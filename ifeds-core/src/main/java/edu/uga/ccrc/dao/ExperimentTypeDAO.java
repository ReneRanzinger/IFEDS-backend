package edu.uga.ccrc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.entity.Provider;

@Repository
public interface ExperimentTypeDAO extends CrudRepository<ExperimentType, Long> {

	@Query(value="SELECT * FROM core.experiment_type where name = :name",nativeQuery=true)
	List<ExperimentType> findAllByName(String name);
	
	

}
