package com.mbueno.pointcontrol.api.controllers;

import com.mbueno.pointcontrol.api.services.FuncionarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles(profiles = "test")
public class FuncionarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FuncionarioService funcionarioService;

    private static final String URL_FUNCIONARIOS = "/api/funcionarios";

    @Test
    @WithMockUser
    void testAtualizarFuncionarioNomeVazio(){

    }

    @Test
    @WithMockUser
    void testAtualizarFuncionarioEmailVazio(){

    }

    @Test
    @WithMockUser
    void testAtualizarFuncionarioEmailInvalido(){

    }

    @Test
    @WithMockUser
    void testAtualizarFuncionarioIdInexistente(){

    }

    @Test
    @WithMockUser
    void testeAtualizarFuncionarioEmailJaCadastrado(){

    }

    @Test
    @WithMockUser
    void testAtualizarFuncionarioIdExistente(){

    }

}
