package edu.uga.ccrc.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.Paper;
import edu.uga.ccrc.entity.Permissions;

@Repository
public interface PermissionsDAO extends CrudRepository<Permissions, Long> {

	@Query(value = "SELECT * FROM core.Permissions WHERE provider_id = :provider_id", nativeQuery = true)
	Permissions findByProviderId(Long provider_id);

	@Query(value = "SELECT * FROM core.Permissions WHERE permission_level ='admin' and provider_id != :provider_id LIMIT 1", nativeQuery = true)
	Permissions getAdminOtherThanThisId(Long provider_id);

}
