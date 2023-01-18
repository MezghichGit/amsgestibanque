package com.sip.ams.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sip.ams.entities.Affectation;

public interface AffectationRepository extends CrudRepository<Affectation, Integer>{

	@Query("FROM Affectation a WHERE a.idAgent = ?1")
	List<Affectation> findAffectationByAgent(int id);

}
