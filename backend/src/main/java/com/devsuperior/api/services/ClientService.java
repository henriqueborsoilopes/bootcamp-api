package com.devsuperior.api.services;

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

}
