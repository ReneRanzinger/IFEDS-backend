package edu.uga.ccrc.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.DatasetToPaper;
import edu.uga.ccrc.entity.Keyword;

@Repository
public interface DatasetToPaperDAO extends CrudRepository<DatasetToPaper, Long>{

	@Transactional
	@Modifying
	@Query(value="DELETE FROM core.dataset_to_paper where dataset_id = ?1",nativeQuery=true)
	public void deleteDatasetToPaperByDatasetId(Long id);
}
