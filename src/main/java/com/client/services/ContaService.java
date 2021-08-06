package com.client.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.domain.Conta;
import com.client.enums.Status;
import com.client.repository.ContaRepository;
import com.client.util.Util;

@Service
public class ContaService implements GenericService<Conta, String> {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private UserService userService;

	@Override
	public Conta save(Conta entity) {
		if (entity.getId() == null || entity.getId().isEmpty())
			entity.setId(Util.toUUID());

		if (entity.getStatus() == null)
			entity.setStatus(Status.OPEN);

		entity.setDataCadastro(new Date());
		entity.setUser(userService.getById("2062973d-ac2c-4a71-90a9-a2b0d8109670"));

		Conta newConta = contaRepository.save(entity);
		return newConta;
	}

	@Override
	public Conta update(Conta entity) {

		Conta old = getById(entity.getId().replace(",", ""));
		old.setNomeConta(entity.getNomeConta());
		old.setValor(entity.getValor());

		Conta updatedConta = contaRepository.save(old);
		return updatedConta;
	}

	public void fecharConta(String id) {

		Conta conta = getById(id);
		conta.setStatus(Status.OK);

		update(conta);
	}

	public void abrirConta(String id) {
		Conta conta = getById(id);
		conta.setStatus(Status.OPEN);

		update(conta);

	}

	@Override
	public Conta getById(String id) {
		Optional<Conta> result = contaRepository.findById(id);
		return result.get();
	}

	@Override
	public List<Conta> listAll() {
		List<Conta> results = contaRepository.findAll();
		return results;
	}

	public List<Conta> listAllByUserId(String id) {
		List<Conta> results = contaRepository.findAllByUserId(id);
		return results;
	}

	@Override
	public void delete(Conta entity) {
		contaRepository.delete(entity);
	}

	@Override
	public void deleteById(String id) {
		Conta entity = getById(id);
		delete(entity);
	}

	public BigDecimal getSomaContas() {
		return contaRepository.getSomaContas();

	}

}
