package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;

import edu.uga.ccrc.entity.FundingGrant;
import edu.uga.ccrc.entity.FundingSource;

public interface FundingSourceDAO extends CrudRepository<FundingSource, Long>{

}
