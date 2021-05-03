package org.sid.repository;

import org.sid.entity.Acti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ActiRepository extends JpaRepository<Acti, Long> {

}
