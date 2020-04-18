package edu.uga.ccrc.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.uga.ccrc.entity.FundingGrant;
import edu.uga.ccrc.entity.FundingSource;

public interface FundingSourceDAO extends CrudRepository<FundingSource, Long>{

	@Query(value="SELECT * FROM core.funding_source where name = :name",nativeQuery=true)
	FundingSource findAllByName(String name);

}
