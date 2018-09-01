package com.mbueno.pointcontrol.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbueno.pointcontrol.api.dtos.FuncionarioDto;
import com.mbueno.pointcontrol.api.entities.Funcionario;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
public class FuncionarioControllerTest {

    private static final String URL_BUSCAR_FUNCIONARIOS = "/api/funcionarios/";
    private static final Long ID = Long.valueOf(1);
    private static final String NOME = "Funcionario 1";
    private static final String EMAIL = "funcionario@teste.com";
    private static final String CPF = "01919155058";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @Test
    @WithMockUser
    public void testAtualizarFuncionarioNomeVazio() throws Exception {
        Funcionario funcionario = obterFuncionario();
        funcionario.setNome(null);

        BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(obterFuncionario()));

        mvc.perform(MockMvcRequestBuilders.put(URL_BUSCAR_FUNCIONARIOS + ID)
                .content(this.obterJsonRequisicaoPost(funcionario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").value("Nome não pode ser vazio."));
    }

    @Test
    @WithMockUser
    public void testAtualizarFuncionarioEmailVazio() throws Exception {
        Funcionario funcionario = obterFuncionario();
        funcionario.setEmail(null);
        BDDMockito.given(funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(obterFuncionario()));

        mvc.perform(MockMvcRequestBuilders.put(URL_BUSCAR_FUNCIONARIOS + ID)
                .content(this.obterJsonRequisicaoPost(funcionario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").value("E-mail não pode ser vazio."));

    }

    @Test
    @WithMockUser
    public void testAtualizarFuncionarioEmailInvalido() throws Exception {
        Funcionario funcionario = obterFuncionario();
        funcionario.setEmail("a873/*3/4d23dss23/232*-/*-2/*2dd");

        BDDMockito.given(funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(obterFuncionario()));

        mvc.perform(MockMvcRequestBuilders.put(URL_BUSCAR_FUNCIONARIOS + ID)
                .content(this.obterJsonRequisicaoPost(funcionario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").value("E-mail inválido."));

    }

    @Test
    @WithMockUser
    public void testAtualizarFuncionarioIdInexistente() throws Exception{
//        BDDMockito.given(funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(obterFuncionario()));
//
//        mvc.perform(MockMvcRequestBuilders.put(URL_BUSCAR_FUNCIONARIOS + 9999)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(obterJsonRequisicaoPost(obterFuncionario()))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.data").isEmpty())
//                .andExpect(jsonPath("$.errors").value("Funcionário não encontrado."));
    }

    @Test
    @WithMockUser
    public void testeAtualizarFuncionarioEmailJaCadastrado() {

    }

    private Funcionario obterFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(ID);
        funcionario.setNome(NOME);
        funcionario.setEmail(EMAIL);
        funcionario.setCpf(CPF);
        return funcionario;
    }

    private String obterJsonRequisicaoPost(Funcionario funcionario) throws JsonProcessingException {
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setId(funcionario.getId());
        funcionarioDto.setNome(funcionario.getNome());
        funcionarioDto.setEmail(funcionario.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(funcionarioDto);
    }

}
