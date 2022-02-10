package com.eduardo.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eduardo.os.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

	@Query("SELECT obj FROM Tecnico obj WHERE obj.cpf = :cpf")
	Tecnico findByCPF(@Param("cpf") String cpf);

}
