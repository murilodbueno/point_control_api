package com.mbueno.pointcontrol.api.mock;

import com.mbueno.pointcontrol.api.entities.Empresa;
import com.mbueno.pointcontrol.api.entities.Funcionario;
import com.mbueno.pointcontrol.api.entities.Lancamento;
import com.mbueno.pointcontrol.api.enums.PerfilEnum;
import com.mbueno.pointcontrol.api.enums.TipoEnum;
import com.mbueno.pointcontrol.api.utils.PasswordUtils;

import java.math.BigDecimal;
import java.util.Date;

public class DataForTests {

    public static final String EMAIL = "email@email.com";
    public static final String CPF = "24291173474";
    public static final String CNPJ = "51463645000100";

    public DataForTests() {
    }

    public Funcionario obterDadosFuncionario(Empresa empresa) {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF);
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
        funcionario.setNome("Funcionario de teste");
        funcionario.setDataAtualizacao(new Date());
        funcionario.setDataCriacao(new Date());
        funcionario.setQtdHorasTrabalhoDia(8f);
        funcionario.setEmpresa(empresa);
        funcionario.setEmail(EMAIL);
        funcionario.setValorHora(BigDecimal.valueOf(50));
        funcionario.setQtdHorasAlmoco(1f);
        return funcionario;
    }

    public Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa para teste");
        empresa.setCnpj(CNPJ);
        empresa.setDataAtualizacao(new Date());
        empresa.setDataCriacao(new Date());
        return empresa;
    }

    public Lancamento obterDadosLancamento(Funcionario funcionario) {
        Lancamento lancamento = new Lancamento();
        lancamento.setData(new Date());
        lancamento.setDescricao("Almoço");
        lancamento.setLocalizacao("Empresa");
        lancamento.setDataAtualizacao(new Date());
        lancamento.setDataCriacao(new Date());
        lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
        lancamento.setFuncionario(funcionario);
        return lancamento;
    }
}
