package com.client.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.client.domain.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, String> {

	public List<Lancamento> findAllByUserId(String id);

	@Query("select coalesce(sum(e.valor),'0.00') from Lancamento e ")
	BigDecimal getSomaLancamentos();

}
