package org.sid.repository;

import java.util.Date;
import java.util.List;

import org.sid.entity.Passage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PassageRepository extends JpaRepository<Passage, Long> {
	@Query(value = "SELECT passage FROM Passage passage where MONTH( passage.passageDate )= MONTH( :passageDate ) and passage.idBenevole= :idBenevole")
	List<Passage> findPassages(Date passageDate, Long idBenevole);

	@Query(value = "SELECT passage FROM Passage passage where MONTH( passage.passageDate )= MONTH( :passageDate ) and DAY( passage.passageDate )= DAY( :passageDate ) and YEAR( passage.passageDate )= YEAR( :passageDate ) and passage.idBenevole= :idBenevole")
	Passage findPassage(Date passageDate, Long idBenevole);
}
