package com.mbueno.pointcontrol.api.services.impl;

import com.mbueno.pointcontrol.api.entities.Lancamento;
import com.mbueno.pointcontrol.api.repositories.LancamentoRepository;
import com.mbueno.pointcontrol.api.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Lancamento persistir(Lancamento lancamento) {
        log.info("Persiste o lancamento: {}", lancamento);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    public Optional<Lancamento> buscarPorId(Long id) {
        log.info("Buscando lancamento por id: {}", id);
        return lancamentoRepository.findById(id);
    }

    @Override
    public Page<Lancamento> buscarPorIdFuncionario(Long funcionarioId, PageRequest pageRequest) {
        log.info("Buscando lancamentos para o funcionario ID: {}", funcionarioId);
        return lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Override
    public void remover(Long id) {
        log.info("Removendo funcionario por id: {}", id);
        lancamentoRepository.deleteById(id);
    }
}
