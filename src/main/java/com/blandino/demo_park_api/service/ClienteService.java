package com.blandino.demo_park_api.service;

import com.blandino.demo_park_api.entity.Cliente;
import com.blandino.demo_park_api.exception.NuitUniqueVioletionException;
import com.blandino.demo_park_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository repository;

    private Cliente salvar(Cliente cliente){

        try {
            return repository.save(cliente);
        }catch (DataIntegrityViolationException exception){
            throw new NuitUniqueVioletionException("Nuit '/%' ja cadastrado no sistema"+cliente.getNuit());
        }

    }


}
