package com.mbueno.pointcontrol.api.services.impl;

import com.mbueno.pointcontrol.api.entities.Empresa;
import com.mbueno.pointcontrol.api.repositories.EmpresaRepository;
import com.mbueno.pointcontrol.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Empresa buscarPorCnpj(String cnpj) {
        log.info("Buscando uma empresa por CNPJ {}", cnpj);
        return empresaRepository.findByCnpj(cnpj);
    }

    @Override
    public Empresa persistir(Empresa empresa) {
        log.info("Persistindo empresa: {}", empresa);
        return empresaRepository.save(empresa);
    }
}
