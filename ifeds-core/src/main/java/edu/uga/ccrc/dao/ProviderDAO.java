package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.Provider;

@Repository
public interface ProviderDAO extends CrudRepository<Provider, Integer> {
		
		Provider findByUsername(String username);
	}

