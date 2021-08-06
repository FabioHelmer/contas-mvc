package com.client.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.domain.EmprestimoPagar;
import com.client.enums.Status;
import com.client.repository.EmprestimoPagarRepository;
import com.client.util.Util;

@Service
public class EmprestimoPagarService implements GenericService<EmprestimoPagar, String> {

	@Autowired
	private EmprestimoPagarRepository emprestimoPagarRepository;

	@Autowired
	private UserService userService;

	@Override
	public EmprestimoPagar save(EmprestimoPagar entity) {
		if (entity.getId() == null || entity.getId().isEmpty())
			entity.setId(Util.toUUID());

		if (entity.getStatus() == null)
			entity.setStatus(Status.OPEN);

		if (!entity.isParcelar()) {
			entity.setNumParcelas(0);
			entity.setParcelaAtual(0);
		}

		if (entity.getParcelaAtual() == null)
			entity.setParcelaAtual(0);

		entity.setUser(userService.getById("2062973d-ac2c-4a71-90a9-a2b0d8109670"));
		entity.setDataCadastro(new Date());

		EmprestimoPagar newConta = emprestimoPagarRepository.save(entity);
		return newConta;
	}

	@Override
	public EmprestimoPagar update(EmprestimoPagar entity) {

		EmprestimoPagar old = getById(entity.getId().replace(",", ""));
		old.setNomeConta(entity.getNomeConta());
		old.setNomePessoa(entity.getNomePessoa());
		old.setParcelar(entity.isParcelar());
		old.setNumParcelas(entity.getNumParcelas());
		old.setParcelaAtual(entity.getParcelaAtual());
		old.setParcelar(entity.isParcelar());


		old.setValor(entity.getValor());

		EmprestimoPagar updated = save(old);
		return updated;

	}

	public void fecharConta(String id) {
		EmprestimoPagar emprestimoPagar = getById(id);
		if (emprestimoPagar.isParcelar()) {
			emprestimoPagar.setParcelaAtual(emprestimoPagar.getNumParcelas());
		}
		emprestimoPagar.setStatus(Status.OK);

		update(emprestimoPagar);
	}

	public void abrirConta(String id) {
		EmprestimoPagar emprestimoPagar = getById(id);
		if (emprestimoPagar.isParcelar()) {
			emprestimoPagar.setParcelaAtual(0);
		}

		emprestimoPagar.setStatus(Status.OPEN);

		update(emprestimoPagar);

	}

	@Override
	public EmprestimoPagar getById(String id) {
		Optional<EmprestimoPagar> result = emprestimoPagarRepository.findById(id);
		return result.get();
	}

	@Override
	public List<EmprestimoPagar> listAll() {
		List<EmprestimoPagar> results = emprestimoPagarRepository.findAll();
		return results;
	}

	public List<EmprestimoPagar> listAllByUserId(String id) {
		List<EmprestimoPagar> results = emprestimoPagarRepository.findAllByUserId(id);
		return results;
	}

	@Override
	public void delete(EmprestimoPagar entity) {
		emprestimoPagarRepository.delete(entity);
	}

	@Override
	public void deleteById(String id) {
		EmprestimoPagar entity = getById(id);
		delete(entity);
	}

	public BigDecimal getSomaEmprestimos() {
		return emprestimoPagarRepository.getSomaEmprestimos();
	}

}
