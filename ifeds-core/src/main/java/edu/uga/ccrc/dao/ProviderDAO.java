package edu.uga.ccrc.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;

@Repository
public interface ProviderDAO extends CrudRepository<Provider, Integer> {
		
		Provider findByUsername(String username);
		
		Provider findByEmail(String email);

		@Query(value = "SELECT * FROM core.Provider WHERE provider_id = :provider_id", nativeQuery = true)
		Provider findByProviderId(Long provider_id);

}

