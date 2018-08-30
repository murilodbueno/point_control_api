package com.mbueno.pointcontrol.api.services;

import com.mbueno.pointcontrol.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LancamentoService {
    /**
     * Persiste Lancamento na base de dados
     *
     * @param lancamento
     * @return Lancamento
     */
    Lancamento persistir(Lancamento lancamento);

    /**
     * Busca um lancamento na base de dados pelo id
     *
     * @param id
     * @return Lancamento
     */
    Optional<Lancamento> buscarPorId(Long id);

    /**
     * Retorna uma lista paginada de lancamentos de um determinado funcionario
     *
     * @param funcionarioId
     * @param pageRequest
     * @return
     */
    Page<Lancamento> buscarPorIdFuncionario(Long funcionarioId, PageRequest pageRequest);

    /**
     * Remove Lancamento da base de dados
     *
     * @param id
     */
    void remover(Long id);
}
