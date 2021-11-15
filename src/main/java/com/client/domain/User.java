package com.client.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ct_usuario")
public class User {

	@Id
	@Column(name = "id", nullable = false, unique = true, length = 38)
	private String id;

	@NotNull(message = "Nome é obrigatório")
	@NotBlank(message = "Nome Não pode está em branco")
	@Column(name = "nome", nullable = false, unique = true, length = 100)
	private String nome;

	@Column(name = "CPF", nullable = true, unique = true, length = 14)
	private String CPF;

	@NotNull(message = "Email é obrigatório")
	@NotBlank(message = "Email Não pode está em branco")
	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;

	@Column(name = "senha", length = 15, nullable = false)
	@NotNull(message = "Senha é obrigatório")
	@NotBlank(message = "Senha não pode está em branco")
	@Length(min = 3, message = "Tamanho Inválido")
	private String senha;

	@Column(name = "celular", length = 11, nullable = true)
	@NotNull(message = "Telefone é obrigatório")
	@Length(min = 9, max = 11, message = "Tamanho Inválido")
	private String celular;

	@Column(name = "data_cadastro", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataCadastro;

	@Column(name = "data_nascimento", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataNascimento;

	@OneToMany(mappedBy = "user")
	private List<Conta> contas;

	@OneToMany(mappedBy = "user")
	private List<Lancamento> lancamentos;

	@OneToMany(mappedBy = "user")
	private List<EmprestimoPagar> emprestimos;

	public User() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public List<EmprestimoPagar> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<EmprestimoPagar> emprestimos) {
		this.emprestimos = emprestimos;
	}

}
