 package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;

import edu.uga.ccrc.entity.Dataset;

public interface DatasetDAO extends CrudRepository<Dataset,Long>{
	
	Iterable<Dataset> findAll();
}
