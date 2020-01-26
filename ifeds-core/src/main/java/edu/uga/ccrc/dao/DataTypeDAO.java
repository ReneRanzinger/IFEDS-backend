package edu.uga.ccrc.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.uga.ccrc.entity.DataType;

public interface DataTypeDAO extends JpaRepository<DataType,Long>{

}
