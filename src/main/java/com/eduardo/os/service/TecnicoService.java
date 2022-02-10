package com.eduardo.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.os.domain.Pessoa;
import com.eduardo.os.domain.Tecnico;
import com.eduardo.os.dtos.TecnicoDTO;
import com.eduardo.os.repositories.PessoaRepository;
import com.eduardo.os.repositories.TecnicoRepository;
import com.eduardo.os.service.exception.DataIntegratyViolationException;
import com.eduardo.os.service.exception.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoarepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo:  " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}
	
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id ) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
			
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getList().size() >0) {
			throw new DataIntegratyViolationException("Tecnico possui ordens de serviço, impossivel apaga-lo!");
		}
		repository.deleteById(id);
	}
	
	
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoarepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;

		}
		return null;
	}



}
