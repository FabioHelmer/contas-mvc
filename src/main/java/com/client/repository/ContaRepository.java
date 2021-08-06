package com.client.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.client.domain.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, String> {

	public List<Conta> findAllByUserId(String id);

	@Query("select coalesce(sum(e.valor),'0.00') from Conta e WHERE e.status = 'OPEN' ")
	BigDecimal getSomaContas();

}
