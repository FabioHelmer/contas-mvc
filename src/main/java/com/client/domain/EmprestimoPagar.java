package com.client.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.client.enums.Status;

@Entity
@Table(name = "ct_emp_pagar")
public class EmprestimoPagar {

	@Id
	@Column(name = "id", nullable = false, unique = true, length = 38)
	private String id;

	@NotNull(message = "Nome não pode ser nulo")
	@NotBlank(message = "Nome é obrigatorio")
	@Column(name = "pessoa", nullable = false, unique = false, length = 100)
	private String nomePessoa;

	@NotNull(message = "Nome da conta não pode ser nulo")
	@NotBlank(message = "Nome da conta é obrigatorio")
	@Column(name = "nome_conta", nullable = false, unique = false, length = 100)
	private String nomeConta;

	@Column(name = "data_cadastro", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataCadastro;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@NotNull(message = "Valor não pode ser nulo")
	@Column(name = "valor", nullable = false)
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private BigDecimal valor;

	@NotNull(message = "Parcelas não pode ser nulo")
	@Column(name = "parcelar", nullable = false)
	private boolean parcelar;

	@Column(name = "total_parcelas", nullable = true)
	private Integer numParcelas;

	@Column(name = "parcela_atual", nullable = true)
	private Integer parcelaAtual;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private User user;

	public EmprestimoPagar() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getNomeConta() {
		return nomeConta;
	}

	public void setNomeConta(String nomeConta) {
		this.nomeConta = nomeConta;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isParcelar() {
		return parcelar;
	}

	public void setParcelar(boolean parcelar) {
		this.parcelar = parcelar;
	}

	public Integer getNumParcelas() {
		return numParcelas;
	}

	public void setNumParcelas(Integer numParcelas) {
		this.numParcelas = numParcelas;
	}

	public Integer getParcelaAtual() {
		return parcelaAtual;
	}

	public void setParcelaAtual(Integer parcelaAtual) {
		this.parcelaAtual = parcelaAtual;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
