
package edu.uga.ccrc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.uga.ccrc.entity.DataFile;
import edu.uga.ccrc.entity.Dataset;

public interface DataFileDAO extends JpaRepository<DataFile,Long>{
	
	
	@Query(value="SELECT * FROM core.data_file where dataset_id = ?1 and original_file_name = ?2",nativeQuery=true)
	public DataFile checkIfDuplicateFile(long dataset_id, String origFileName);
	
	@Query(value="SELECT count(*) FROM core.data_file where dataset_id = ?1",nativeQuery=true)
	public int numberOfDataFiles(Long dataset_id);
	

}
