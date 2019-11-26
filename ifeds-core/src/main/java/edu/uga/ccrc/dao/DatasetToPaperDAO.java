package edu.uga.ccrc.dao;

import org.springframework.data.repository.CrudRepository;

import edu.uga.ccrc.entity.DatasetToPaper;
import edu.uga.ccrc.entity.Keyword;

public interface DatasetToPaperDAO extends CrudRepository<DatasetToPaper, Long>{

}
