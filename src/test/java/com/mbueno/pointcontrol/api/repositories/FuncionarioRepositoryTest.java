package com.mbueno.pointcontrol.api.repositories;

import com.mbueno.pointcontrol.api.entities.Empresa;
import com.mbueno.pointcontrol.api.entities.Funcionario;
import com.mbueno.pointcontrol.api.mock.DataForTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest extends DataForTests {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Before
    public void setUp() throws Exception {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        this.funcionarioRepository.save(obterDadosFuncionario(empresa));
    }

    @After
    public final void tearDown() {
        this.empresaRepository.deleteAll();
        this.funcionarioRepository.deleteAll();
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {
        Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
        assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {
        Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
        assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void testBuscarFuncionarioPorEmailECpf() {
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
        assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido() {
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email_invalido@email.com");
        assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorEmailOuCpfParaCpfInvalido() {
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("123456789", EMAIL);
        assertNotNull(funcionario);
    }
}
