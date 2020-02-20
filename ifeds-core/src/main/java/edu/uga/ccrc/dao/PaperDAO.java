package edu.uga.ccrc.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.Paper;

@Repository
public interface PaperDAO extends CrudRepository<Paper, Long>{
	@Query(value = "SELECT * FROM core.Paper WHERE pmid = :pmid", nativeQuery = true)
	public Paper findByPMId(long pmid);
}
