package com.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.client.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query(value = "select DISTINCT\r\n" + "EXTRACT(month from e.data_cadastro),\r\n" + "sum(e.valor)\r\n" + "\r\n"
			+ "from ct_conta.ct_lancamento e \r\n" + "where e.id_usuario = :idUser\r\n"
			+ "\r\n" + "GROUP by \r\n" + "e.data_cadastro", nativeQuery = true)
	List<Object[]> getLancamentosPorMes(String idUser);

}
