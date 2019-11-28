package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;

import edu.uga.ccrc.entity.FundingGrant;



public interface FundingGrantDAO extends CrudRepository<FundingGrant, Long> {

}
