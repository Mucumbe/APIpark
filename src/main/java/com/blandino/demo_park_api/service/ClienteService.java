package com.blandino.demo_park_api.service;

import com.blandino.demo_park_api.entity.Cliente;
import com.blandino.demo_park_api.exception.EntityNotFoundException;
import com.blandino.demo_park_api.exception.NuitUniqueVioletionException;
import com.blandino.demo_park_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente salvar(Cliente cliente){

        try {
            return repository.save(cliente);
        }catch (DataIntegrityViolationException exception){
            throw new NuitUniqueVioletionException(String.format("Nuit '%s' ja cadastrado no sistema",cliente.getNuit()));
        }

    }

@Transactional(readOnly = true)
    public Cliente obterPorId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("ID:%s nao emcontrado",id))
        );
    }
}
