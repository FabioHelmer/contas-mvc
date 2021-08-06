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

import com.client.domain.User;
import com.client.services.UserService;

@Controller
@RequestMapping("/admin")
public class UsuarioController {

	@Autowired
	UserService userService;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuario/cadastro");
		mv.addObject("usuario", new User());
		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/usuario/lista");
		mv.addObject("usuarios", userService.listAll());
		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid User user, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("usuario/cadastro");
			List<ObjectError> erros = result.getAllErrors();
			mv.addObject("erros", erros);
			mv.addObject(user);

		} else {
			mv.setViewName("redirect:/usuario/listar");
			userService.save(user);
		}

		return mv;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuario/alterar");
		mv.addObject(userService.getById(id));

		return mv;
	}

	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid User user, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("conta/alterar");
			mv.addObject(user);

			List<ObjectError> erros = result.getAllErrors();
			mv.addObject("erros", erros);

		} else {
			mv.setViewName("redirect:/usuario/listar");
			userService.update(user);
		}
		return mv;
	}


	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") String id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		userService.deleteById(id);
		mv.setViewName("redirect:/conta/listar");

		return mv;
	}

}
