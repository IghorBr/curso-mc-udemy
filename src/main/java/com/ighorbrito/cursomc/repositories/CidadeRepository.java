package com.ighorbrito.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ighorbrito.cursomc.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	
	@Transactional(readOnly = true)
	@Query("Select c from Cidade c where c.estado.id = :estadoId order by c.nome")
	List<Cidade> findCidades(@Param("estadoId")Integer estadoId);
}
