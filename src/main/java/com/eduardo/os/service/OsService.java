package com.eduardo.os.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.os.domain.Cliente;
import com.eduardo.os.domain.OS;
import com.eduardo.os.domain.Tecnico;
import com.eduardo.os.domain.enus.Prioridade;
import com.eduardo.os.domain.enus.Status;
import com.eduardo.os.dtos.OSDTO;
import com.eduardo.os.repositories.OSRepository;
import com.eduardo.os.service.exception.ObjectNotFoundException;

@Service
public class OsService {
	@Autowired
	private OSRepository repository;
	
	@Autowired
	private TecnicoService tecnicoservice;
	
	@Autowired
	private ClienteService clienteservice;
	

	public OS findById(Integer id) {
		Optional<OS> obj = repository.findById(id);
	 	return obj.orElseThrow(() -> new ObjectNotFoundException( 
				"Ordem de Serviço não encontrada! Id: "+id+", Tipo "+ OS.class.getName()));	
	}
	public List<OS> findAll() {
		return repository.findAll();
	}
	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
		
	}
	public OS update(@Valid OSDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
	}
	
	
	
	
	private OS fromDTO(OSDTO obj) {
		OS newOBJ = new OS();
		newOBJ.setId(obj.getId());
		newOBJ.setObservacoes(obj.getObservacoes());
		newOBJ.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newOBJ.setStatus(Status.toEnum(obj.getStatus()));
		
		Tecnico tec = tecnicoservice.findById(obj.getTecnico());
		Cliente cli = clienteservice.findById(obj.getCliente());
		
		newOBJ.setTecnico(tec);
		newOBJ.setCliente(cli);
		
		if(newOBJ.getStatus().getCod().equals(2)) {
			newOBJ.setDataFechamento(LocalDateTime.now());
		}
		return repository.save(newOBJ);
	}
	
}
