package org.sid.repository;

import org.sid.entity.AppRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RolesRepository extends JpaRepository<AppRoles, Long> {

	public AppRoles findByRoleName(String roleName);
}
