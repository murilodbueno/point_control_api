package com.mbueno.pointcontrol.api.services;

import com.mbueno.pointcontrol.api.entities.Funcionario;
import com.mbueno.pointcontrol.api.repositories.FuncionarioRepository;
import com.mbueno.pointcontrol.api.services.impl.FuncionarioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

    private static final String EMAIL = "email@email.com";
    private static final String CPF = "24291173474";
    @MockBean
    FuncionarioRepository funcionarioRepository;
    @Autowired
    FuncionarioServiceImpl funcionarioService;

    @Before
    public void setUp() {
        BDDMockito.given(funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
        BDDMockito.given(funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(funcionarioRepository.findById(Mockito.anyLong())).willReturn(java.util.Optional.of(new Funcionario()));
    }

    @Test
    public void testPersistirFuncionario() {
        Funcionario funcionario = funcionarioService.persistir(new Funcionario());
        assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorCpf(CPF);
        assertTrue(funcionario.isPresent());
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(EMAIL);
        assertTrue(funcionario.isPresent());
    }

    @Test
    public void testBuscarFuncionarioPorId() {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorId(1L);
        assertTrue(funcionario.isPresent());
    }
}
