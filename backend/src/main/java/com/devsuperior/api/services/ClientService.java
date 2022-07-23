package com.devsuperior.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.api.dtos.ClientDTO;
import com.devsuperior.api.entities.Client;
import com.devsuperior.api.repositories.ClientRepository;

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
		Client client = optional.get();
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
		Client client = clientRepository.getReferenceById(id);
		DTOToEntity(client, newClientDTO);
		client = clientRepository.save(client);
		return new ClientDTO(client);
	}

	public void deleteById(Long id) {
		clientRepository.deleteById(id);		
	}
	
	private void DTOToEntity(Client newClient, ClientDTO newClientDTO) {
		newClient.setName(newClientDTO.getName());
		newClient.setCpf(newClientDTO.getCpf());
		newClient.setIncome(newClientDTO.getIncome());
		newClient.setBirthDate(newClientDTO.getBirthDate());
		newClient.setChildren(newClientDTO.getChildren());
	}
}
