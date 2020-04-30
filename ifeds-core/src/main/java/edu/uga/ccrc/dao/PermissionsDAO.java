package edu.uga.ccrc.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
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

	@Transactional
	@Modifying
	@Query(value="DELETE FROM core.Permissions WHERE permissions_id = :permissions_id",nativeQuery=true)
	void deleteByPermissionId(Long permissions_id);

}
