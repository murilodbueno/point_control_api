package com.mbueno.pointcontrol.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbueno.pointcontrol.api.dtos.CadastroPFDto;
import com.mbueno.pointcontrol.api.entities.Empresa;
import com.mbueno.pointcontrol.api.entities.Funcionario;
import com.mbueno.pointcontrol.api.services.EmpresaService;
import com.mbueno.pointcontrol.api.services.FuncionarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
public class CadastroPFControllerTest {

    //data for employee
    private static final String CADASTRAR_PF = "/api/cadastrar-pf";
    private static final Long ID_FUNCIONARIO = 1L;
    private static final String NOME = "Funcionario teste";
    private static final String EMAIL = "funcionario_teste@teste.com.br";
    private static final String SENHA = "123456";
    private static final String CPF = "17223847000";
    //data for company
    private static final String CNPJ = "24218462000148";
    private static final String RAZAO_SOCIAL = "Empresa XYZ";
    private static final Long ID_EMPRESA = 1L;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FuncionarioService funcionarioService;
    @MockBean
    private EmpresaService empresaService;

    @Test
    @WithMockUser
    public void testePersistirFuncionario() throws Exception {
        Funcionario funcionario = obterDadosFuncionario();
        BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(this.obterDadosEmpresa()));
        BDDMockito.given(this.funcionarioService.buscarPorCpf(Mockito.anyString())).willReturn(Optional.empty());
        BDDMockito.given(this.funcionarioService.buscarPorEmail(Mockito.anyString())).willReturn(Optional.empty());
        BDDMockito.given(this.funcionarioService.persistir(Mockito.any(Funcionario.class))).willReturn(funcionario);

        mvc.perform(MockMvcRequestBuilders.post(CADASTRAR_PF)
                .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID_FUNCIONARIO))
                .andExpect(jsonPath("$.data.nome").value(NOME))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
//                .andExpect(jsonPath("$.data.senha").value(nullValue()))
                .andExpect(jsonPath("$.data.cpf").value(CPF))
                .andExpect(jsonPath("$.data.cnpj", equalTo(CNPJ)))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private Funcionario obterDadosFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(ID_FUNCIONARIO);
        funcionario.setNome(NOME);
        funcionario.setEmail(EMAIL);
        funcionario.setSenha(SENHA);
        funcionario.setCpf(CPF);
        funcionario.setEmpresa(obterDadosEmpresa());
        return funcionario;
    }

    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(ID_EMPRESA);
        empresa.setCnpj(CNPJ);
        empresa.setRazaoSocial("Empresa XYZ");
        return empresa;
    }

    private String obterJsonRequisicaoPost() throws JsonProcessingException {
        CadastroPFDto cadastroPFDto = new CadastroPFDto();
        cadastroPFDto.setNome(NOME);
        cadastroPFDto.setEmail(EMAIL);
        cadastroPFDto.setSenha(SENHA);
        cadastroPFDto.setCpf(CPF);
        cadastroPFDto.setCnpj(CNPJ);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(cadastroPFDto);
    }
}
