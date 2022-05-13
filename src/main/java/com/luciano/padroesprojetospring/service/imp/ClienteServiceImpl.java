package com.luciano.padroesprojetospring.service.imp;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luciano.padroesprojetospring.model.Cliente;
import com.luciano.padroesprojetospring.model.Endereco;
import com.luciano.padroesprojetospring.repository.ClienteRepository;
import com.luciano.padroesprojetospring.repository.EnderecoRepository;
import com.luciano.padroesprojetospring.service.ClienteService;
import com.luciano.padroesprojetospring.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	
	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		Optional<Cliente> optionalCliente = clienteRepository.findById(id);
		
		return optionalCliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCEP(cliente);
		
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCEP(cliente);
		}
		
	}

	@Override
	public void deletar(Long id) {
		clienteRepository.deleteById(id);
		
	}

	private void salvarClienteComCEP(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		
		// Caso encontre teremos o endereço, caso não salvaremos primeiro no banco para depois retornar
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novo = viaCepService.consultarCep(cep);
			enderecoRepository.save(novo);
			return novo;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
		
	}
}
