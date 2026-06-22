package com.blandino.demo_park_api.service;

import com.blandino.demo_park_api.entity.Vaga;
import com.blandino.demo_park_api.exception.CodigoUniqueViolationException;
import com.blandino.demo_park_api.exception.EntityNotFoundException;
import com.blandino.demo_park_api.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository repository;

    @Transactional
    public Vaga salvar(Vaga vaga) {

        try {
            return repository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException(
                    String.format("Vaga com código '%s' já foi cadastrada", vaga.getCodigo())
            );
        }
    }

    public Vaga buscarPorCodigo(String codigo) {

        return repository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Vaga com codigo '%s' não foi encontrado", codigo))
        );
    }
}
