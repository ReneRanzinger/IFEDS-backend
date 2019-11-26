package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.Provider;

@Repository
public interface KeywordDAO extends CrudRepository<Keyword, Long> {
	
	

}
