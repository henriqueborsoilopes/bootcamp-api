package com.devsuperior.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.api.entities.Client;
import com.devsuperior.api.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<Client> findAllPaged(PageRequest pageRequest) {
		Page<Client> clients = clientRepository.findAll(pageRequest);
		return clients;
	}

	@Transactional(readOnly = true)
	public Client findById(Long id) {
		Optional<Client> optional = clientRepository.findById(id);
		Client client = optional.get();
		return client;
	}
	
	@Transactional
	public Client insert(Client newClient) {
		newClient = clientRepository.save(newClient);
		return newClient;
	}
	
	@Transactional
	public Client update(Long id, Client newClient) {
		Client client = clientRepository.getReferenceById(id);
		client.setName(newClient.getName());
		client.setCpf(newClient.getCpf());
		client.setIncome(newClient.getIncome());
		client.setBirthDate(newClient.getBirthDate());
		client.setChildren(newClient.getChildren());
		client = clientRepository.save(client);
		return client;
	}

	public void deleteById(Long id) {
		clientRepository.deleteById(id);		
	}
}
