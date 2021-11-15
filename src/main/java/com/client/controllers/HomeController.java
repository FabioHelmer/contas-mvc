package com.client.controllers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.client.services.HomeService;

@Controller
public class HomeController {

	@Autowired
	HomeService homeService;

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/index");

		Map<String, BigDecimal> valores = new LinkedHashMap<>();
		BigDecimal lancamentos = homeService.getSomalancamentos();
		BigDecimal contas = homeService.getSomaContas();
		BigDecimal emprestimos = homeService.getSomaEmprestimo();

		valores.put("Lancamentos", lancamentos);
		valores.put("Despezas", contas);
		valores.put("Emprestimos", emprestimos);
		mv.addObject("valores", valores);

		List<Object[]> resultLancamentoPorMes = homeService.somaLancamentosPorMes();
		Map<String, BigDecimal> lancamentosMes = new LinkedHashMap<>();

		for (Object[] linha : resultLancamentoPorMes) {
			String mes = "";
			String valor = "";
			if (linha[0] != null) {
				mes = (linha[0].toString());
				mes = mes.replace(".0", "");
			}
			if (linha[1] != null) {
				valor = linha[1].toString();
			}

			lancamentosMes.put(mes, new BigDecimal(valor));
		}

		mv.addObject("lancamentos", lancamentos);
		mv.addObject("contas", contas);
		mv.addObject("emprestimos", emprestimos);
		mv.addObject("lancamentosMes", lancamentosMes);
		mv.addObject("maxGrahBar", emprestimos.add(contas).add(lancamentos));
		mv.addObject("ishome", "true");

		return mv;
	}

}

