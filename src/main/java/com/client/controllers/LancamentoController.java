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

import com.client.domain.Lancamento;
import com.client.services.LancamentoService;

@Controller
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired
	LancamentoService lancamentoService;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("lancamento/cadastro");
		mv.addObject(new Lancamento());
		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lancamento/lista");
		mv.addObject("lancamentos", lancamentoService.listAll());
		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Lancamento lancamento, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("lancamento/cadastro");
			List<ObjectError> erros = result.getAllErrors();
			mv.addObject("erros", erros);
			mv.addObject(lancamento);
		} else {
			mv.setViewName("redirect:/lancamentos/listar");
			lancamentoService.save(lancamento);
		}

		return mv;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("lancamento/alterar");
		mv.addObject(lancamentoService.getById(id));

		return mv;
	}

	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid Lancamento lancamento, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("lancamento/alterar");
			List<ObjectError> erros = result.getAllErrors();
			mv.addObject("erros", erros);
			mv.addObject(lancamento);
		} else {
			mv.setViewName("redirect:/lancamentos/listar");
			lancamentoService.update(lancamento);
		}
		return mv;
	}



	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		lancamentoService.deleteById(id);
		mv.setViewName("redirect:/lancamentos/listar");

		return mv;
	}

}
