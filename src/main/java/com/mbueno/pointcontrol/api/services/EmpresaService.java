package com.mbueno.pointcontrol.api.services;

import com.mbueno.pointcontrol.api.entities.Empresa;

public interface EmpresaService {

    /**
     * Retorna uma empresa dado um CNPJ
     * @param cnpj
     * @return Empresa
     */
    Empresa buscarPorCnpj(String cnpj);

    /**
     * Cadastra uma nova empresa na base de dados
     * @param empresa
     * @return Empresa
     */
    Empresa persistir(Empresa empresa);
}
