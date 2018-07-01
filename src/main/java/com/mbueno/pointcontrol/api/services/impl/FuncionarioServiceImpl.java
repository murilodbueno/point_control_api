package com.mbueno.pointcontrol.api.services.impl;

import com.mbueno.pointcontrol.api.entities.Funcionario;
import com.mbueno.pointcontrol.api.repositories.FuncionarioRepository;
import com.mbueno.pointcontrol.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario persistir(Funcionario funcionario) {
        log.info("Persistindo funcionário: {}", funcionario);
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public Funcionario buscarPorCpf(String cpf) {
        log.info("Buscando funcionario por CPF {}", cpf);
        return funcionarioRepository.findByCpf(cpf);
    }

    @Override
    public Funcionario buscarPorEmail(String email) {
        log.info("Buscando funcionario por email {}", email);
        return funcionarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        log.info("Buscando funcionario por ID {}", id);
        return funcionarioRepository.findById(id);
    }
}
