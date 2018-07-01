package com.mbueno.pointcontrol.api.services;

import com.mbueno.pointcontrol.api.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioService {
    /**
     * Persiste um funcionário na base de dados
     * @param funcionario
     * @return Funcionario
     */
    Funcionario persistir(Funcionario funcionario);

    /**
     * Busca funcionario por CPF
     * @param cpf
     * @return Funcionario
     */
    Funcionario buscarPorCpf(String cpf);

    /**
     * Busca funcionario por email
     * @param email
     * @return Funcionario
     */
    Funcionario buscarPorEmail(String email);

    /**
     * Busca funcionario por id
     * @param id
     * @return Funcionario
     */
    Optional<Funcionario> buscarPorId(Long id);
}
