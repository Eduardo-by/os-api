package com.eduardo.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.os.domain.Cliente;
import com.eduardo.os.domain.Pessoa;
import com.eduardo.os.domain.Tecnico;
import com.eduardo.os.dtos.ClienteDTO;
import com.eduardo.os.dtos.TecnicoDTO;
import com.eduardo.os.repositories.ClienteRepository;
import com.eduardo.os.repositories.PessoaRepository;
import com.eduardo.os.service.exception.DataIntegratyViolationException;
import com.eduardo.os.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoarepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
			"Cliente não encontrado! Id: "+id+", Tipo "+ Cliente.class.getName()));	
	}


	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	
	
	
	
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoarepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}


	public Cliente create(@Valid ClienteDTO objDTO) {
		if(findByCPF(objDTO)!= null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}


	public Cliente update(Integer id,  @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id ) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
			
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}


	public void delete(Integer id) {
		Cliente obj = findById(id);
		if (obj.getList().size()>0) {
			throw new DataIntegratyViolationException("O cliente possui ordens de serviço, impossivel apaga-lo");
		}
		repository.deleteById(id);
	}



		
	}
