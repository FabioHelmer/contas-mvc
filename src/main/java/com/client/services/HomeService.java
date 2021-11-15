package com.client.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

	@Autowired
	LancamentoService lancamentoService;

	@Autowired
	ContaService contaService;

	@Autowired
	EmprestimoPagarService emprestimoPagarService;

	@Autowired
	UserService userService;

	public BigDecimal getSomalancamentos() {
		return lancamentoService.getSomaLancamentos();

	}

	public BigDecimal getSomaContas() {
		return contaService.getSomaContas();

	}

	public BigDecimal getSomaEmprestimo() {
		return emprestimoPagarService.getSomaEmprestimos();
	}

	public List<Object[]> somaLancamentosPorMes() {
		return userService.getLancamentosPorMes();
	}

}
