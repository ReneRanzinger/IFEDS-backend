package edu.uga.ccrc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uga.ccrc.entity.DataFile;
import edu.uga.ccrc.entity.Dataset;

public interface DataFileDAO extends JpaRepository<DataFile,Long>{

}
