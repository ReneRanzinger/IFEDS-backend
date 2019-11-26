package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;

import edu.uga.ccrc.entity.DatasetToExperimentType;
import edu.uga.ccrc.entity.DatasetToKeyword;

public interface DatasetToKeywordDAO extends CrudRepository<DatasetToKeyword, Long> {

}
