package com.devsuperior.api.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.api.entities.Client;
import com.devsuperior.api.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@GetMapping
	public ResponseEntity<Page<Client>> findAllPaged(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Client> clients = clientService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(clients);
	}
	
	@GetMapping(value ="/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client client = clientService.findById(id);
		return ResponseEntity.ok().body(client);
	}
	
	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client newClient) {
		newClient = clientService.insert(newClient);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newClient.getId()).toUri();
		return ResponseEntity.created(uri).body(newClient);
	}
	
	@PutMapping(value = "{id}")
	public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client newClient) {
		newClient = clientService.update(id, newClient);
		return ResponseEntity.ok().body(newClient);
	}
	
	@DeleteMapping(value ="/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
