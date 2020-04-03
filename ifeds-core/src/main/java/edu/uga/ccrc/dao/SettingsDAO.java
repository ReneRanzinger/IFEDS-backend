package edu.uga.ccrc.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.SampleType;
import edu.uga.ccrc.entity.Settings;

@Repository
public interface SettingsDAO extends CrudRepository<Settings, Long> {

	@Query(value="SELECT TOP 1 FROM core.settings where key = ?1",nativeQuery=true)
	Settings existsByKey(String key);

	@Query(value="DELETE FROM core.settings where key = ?1",nativeQuery=true)
	void deleteByKey(String key);

}
