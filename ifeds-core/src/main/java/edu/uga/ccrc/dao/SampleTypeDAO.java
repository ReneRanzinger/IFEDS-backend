package edu.uga.ccrc.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.SampleType;


@Repository
public interface SampleTypeDAO extends CrudRepository<SampleType, Long>{
	
	public List<SampleType> findAll();

}
