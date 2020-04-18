package edu.uga.ccrc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.FundingGrant;


@Repository
public interface FundingGrantDAO extends CrudRepository<FundingGrant, Long> {
	@Transactional
	@Modifying
	@Query(value="DELETE FROM core.funding_grant where dataset_id = ?1",nativeQuery=true)
	public void deleteFundingGrantByDatasetId(Long id);

	@Query(value="SELECT * FROM core.funding_grant where funding_source_id = ?1",nativeQuery=true)
	public List<FundingGrant> findByFundingSouce(Long id);

}
