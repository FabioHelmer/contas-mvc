package com.client.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.domain.Lancamento;
import com.client.repository.LancamentoRepository;
import com.client.util.Util;

@Service
public class LancamentoService implements GenericService<Lancamento, String> {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private UserService userService;

	@Override
	public Lancamento save(Lancamento entity) {
		if (entity.getId() == null || entity.getId().isEmpty())
			entity.setId(Util.toUUID());

		entity.setDataCadastro(new Date());
		entity.setUser(userService.getById("2062973d-ac2c-4a71-90a9-a2b0d8109670"));
		Lancamento newLancamento = lancamentoRepository.save(entity);
		return newLancamento;
	}

	@Override
	public Lancamento update(Lancamento entity) {
		Lancamento oldLanc = getById(entity.getId().replace(",", ""));
		oldLanc.setNome(entity.getNome());
		oldLanc.setValor(entity.getValor());

		Lancamento updated = lancamentoRepository.save(oldLanc);
		return updated;
	}

	@Override
	public Lancamento getById(String id) {
		Optional<Lancamento> result = lancamentoRepository.findById(id);
		return result.get();
	}

	@Override
	public List<Lancamento> listAll() {
		List<Lancamento> results = lancamentoRepository.findAll();
		return results;
	}

	public List<Lancamento> listAllByUserId(String id) {
		List<Lancamento> results = lancamentoRepository.findAllByUserId(id);
		return results;
	}

	@Override
	public void delete(Lancamento entity) {
		lancamentoRepository.delete(entity);
	}

	@Override
	public void deleteById(String id) {
		Lancamento entity = getById(id);
		delete(entity);
	}

	public BigDecimal getSomaLancamentos() {
		return lancamentoRepository.getSomaLancamentos();
	}

}
