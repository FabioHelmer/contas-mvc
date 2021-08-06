package com.client.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.client.domain.EmprestimoPagar;

@Repository
public interface EmprestimoPagarRepository extends JpaRepository<EmprestimoPagar, String> {

	public List<EmprestimoPagar> findAllByUserId(String id);

	@Query("select coalesce(sum(e.valor), '0.00') from EmprestimoPagar e WHERE e.status = 'OPEN' ")
	BigDecimal getSomaEmprestimos();

}
