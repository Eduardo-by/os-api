package com.eduardo.os.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.os.domain.Cliente;
import com.eduardo.os.domain.OS;
import com.eduardo.os.domain.Tecnico;
import com.eduardo.os.domain.enus.Prioridade;
import com.eduardo.os.domain.enus.Status;
import com.eduardo.os.repositories.ClienteRepository;
import com.eduardo.os.repositories.OSRepository;
import com.eduardo.os.repositories.TecnicoRepository;

@Service
public class DBService {
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instaciaDB() {
		Tecnico t1 = new Tecnico(null, "Eduardo Santos", "190.573.300-32", "(63) 99941-0232");
		Tecnico t2 = new Tecnico(null, "Hugo Casio", "232.755.020-34", "(63) 98741-0222");
		Cliente c1 = new Cliente(null, "Borges Guimarares", "445.589.850-61", "(63) 99983-5059");

		OS os1 = new OS(null, Prioridade.Alta, Status.ANDAMENTO, "Teste create Os", t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1 , t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}

}
