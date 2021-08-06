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

import com.client.domain.Conta;
import com.client.domain.Erro;
import com.client.services.ContaService;

@Controller
@RequestMapping("/contas")
public class ContaController {

	@Autowired
	ContaService contaService;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("conta/cadastro");
		mv.addObject(new Conta());
		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/conta/lista");
		mv.addObject("contas", contaService.listAll());
		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Conta conta, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {

			List<ObjectError> erros = result.getAllErrors();
			mv.addObject("erros", erros);

			mv.setViewName("conta/cadastro");
			mv.addObject(conta);
		} else {
			mv.setViewName("redirect:/contas/listar");
			contaService.save(conta);
		}

		return mv;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("conta/alterar");
		mv.addObject(contaService.getById(id));

		return mv;
	}

	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid Conta conta, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("conta/alterar");
			mv.addObject(conta);
		} else {
			mv.setViewName("redirect:/contas/listar");
			contaService.update(conta);
		}
		return mv;
	}

	@GetMapping("/open/{id}")
	public ModelAndView openConta(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			contaService.abrirConta(id);
			mv.setViewName("redirect:/contas/listar");
		} catch (Exception e) {
			mv.setViewName("/conta/lista");
			mv.addObject("contas", contaService.listAll());

			Erro erro = new Erro("não foi possivel realizar a ação");
			mv.addObject("erroCloseOpen", erro);
		}

		return mv;
	}

	@GetMapping("/close/{id}")
	public ModelAndView closeConta(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			contaService.fecharConta(id);
			mv.setViewName("redirect:/contas/listar");
		} catch (Exception e) {
			mv.setViewName("/conta/lista");
			mv.addObject("contas", contaService.listAll());

			Erro erro = new Erro("não foi possivel realizar a ação");
			mv.addObject("erroCloseOpen", erro);
		}

		return mv;
	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		contaService.deleteById(id);
		mv.setViewName("redirect:/contas/listar");

		return mv;
	}

}
