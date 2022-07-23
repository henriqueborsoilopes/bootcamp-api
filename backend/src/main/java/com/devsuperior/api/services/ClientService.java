package com.devsuperior.api.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.api.dtos.ClientDTO;
import com.devsuperior.api.entities.Client;
import com.devsuperior.api.repositories.ClientRepository;
import com.devsuperior.api.services.exceptions.ControllerNotFoundException;
import com.devsuperior.api.services.exceptions.DatabaseException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> clients = clientRepository.findAll(pageRequest);
		return clients.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> optional = clientRepository.findById(id);
		Client client = optional.orElseThrow(() -> new ControllerNotFoundException("Entity not found"));
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO newClientDTO) {
		Client newClient = new Client();
		DTOToEntity(newClient, newClientDTO);
		newClient = clientRepository.save(newClient);
		return new ClientDTO(newClient);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO newClientDTO) {
		try {
			Client client = clientRepository.getReferenceById(id);
			DTOToEntity(client, newClientDTO);
			client = clientRepository.save(client);
			return new ClientDTO(client);
		}
		catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("Entity not found");
		}
	}

	public void deleteById(Long id) {
		try {
			clientRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ControllerNotFoundException("Entity not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violation integrity");
		}
	}
	
	private void DTOToEntity(Client newClient, ClientDTO newClientDTO) {
		newClient.setName(newClientDTO.getName());
		newClient.setCpf(newClientDTO.getCpf());
		newClient.setIncome(newClientDTO.getIncome());
		newClient.setBirthDate(newClientDTO.getBirthDate());
		newClient.setChildren(newClientDTO.getChildren());
	}
}
