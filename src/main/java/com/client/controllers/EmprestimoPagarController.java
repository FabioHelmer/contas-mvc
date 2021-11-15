package com.client.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.client.domain.EmprestimoPagar;
import com.client.domain.Erro;
import com.client.services.EmprestimoPagarService;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoPagarController {

	@Autowired
	EmprestimoPagarService emprestimoPagarService;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("emprestimo/cadastro");
		EmprestimoPagar emprestimoPagar = new EmprestimoPagar();
		emprestimoPagar.setNumParcelas(0);
		emprestimoPagar.setParcelaAtual(0);
		mv.addObject("emprestimo", emprestimoPagar);
		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/emprestimo/lista");
		mv.addObject("emprestimos", emprestimoPagarService.listAll());
		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid EmprestimoPagar emprestimoPagar, BindingResult result,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (emprestimoPagar.getParcelaAtual() > emprestimoPagar.getNumParcelas()) {
			result.rejectValue("parcelaAtual", "parcelaAtual", "Parcela atual não pode ser maior que Total");
		}

		if (emprestimoPagar.getParcelaAtual() < 0) {
			result.rejectValue("parcelaAtual", "parcelaAtual", "Parcela atual não pode ser menor que 0");
		}

		if (emprestimoPagar.getNumParcelas() < 0) {
			result.rejectValue("numParcela", "numParcela", "total parcelas não pode ser menor que 0");
		}

		if (result.hasErrors()) {

			List<ObjectError> erros = result.getAllErrors();
			mv.addObject("erros", erros);

			mv.setViewName("emprestimo/cadastro");
			mv.addObject("emprestimo", emprestimoPagar);
		} else {
			mv.setViewName("redirect:/emprestimos/listar");
			emprestimoPagarService.save(emprestimoPagar);
		}

		return mv;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("emprestimo/alterar");
		mv.addObject("emprestimo", emprestimoPagarService.getById(id));

		return mv;
	}

	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid EmprestimoPagar emprestimoPagar, BindingResult result,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (emprestimoPagar.getParcelaAtual() > emprestimoPagar.getNumParcelas()) {
			result.rejectValue("parcelaAtual", "parcelaAtual", "Parcela atual não pode ser maior que Total");
		}

		if (emprestimoPagar.getParcelaAtual() < 0) {
			result.rejectValue("parcelaAtual", "parcelaAtual", "Parcela atual não pode ser menor que 0");
		}

		if (emprestimoPagar.getNumParcelas() < 0) {
			result.rejectValue("numParcela", "numParcela", "total parcelas não pode ser menor que 0");
		}

		if (result.hasErrors()) {
			mv.setViewName("emprestimo/alterar");
			mv.addObject("emprestimo", emprestimoPagar);
		} else {
			mv.setViewName("redirect:/emprestimos/listar");
			emprestimoPagarService.update(emprestimoPagar);
		}
		return mv;
	}

	@GetMapping("/open/{id}")
	public ModelAndView openConta(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			emprestimoPagarService.abrirConta(id);
			mv.setViewName("redirect:/emprestimos/listar");
		} catch (Exception e) {
			mv.setViewName("/emprestimo/lista");
			mv.addObject("contas", emprestimoPagarService.listAll());

			Erro erro = new Erro("não foi possivel realizar a ação");
			mv.addObject("erroCloseOpen", erro);
		}

		return mv;
	}

	@GetMapping("/close/{id}")
	public ModelAndView closeConta(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			emprestimoPagarService.fecharConta(id);
			mv.setViewName("redirect:/emprestimos/listar");
		} catch (Exception e) {
			mv.setViewName("/emprestimo/lista");
			mv.addObject("emprestimos", emprestimoPagarService.listAll());

			Erro erro = new Erro("não foi possivel realizar a ação");
			mv.addObject("erroCloseOpen", erro);
		}

		return mv;
	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		emprestimoPagarService.deleteById(id);
		mv.setViewName("redirect:/emprestimos/listar");

		return mv;
	}

}
