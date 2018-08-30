package com.mbueno.pointcontrol.api.repositories;

import com.mbueno.pointcontrol.api.entities.Empresa;
import com.mbueno.pointcontrol.api.entities.Funcionario;
import com.mbueno.pointcontrol.api.entities.Lancamento;
import com.mbueno.pointcontrol.api.mock.DataForTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest extends DataForTests {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    private long funcionarioId;

    @Before
    public void setUp() {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());

        Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(obterDadosLancamento(funcionario));
        this.lancamentoRepository.save(obterDadosLancamento(funcionario));
    }

    @After
    public void tearDown() throws Exception {
        this.empresaRepository.deleteAll();
        this.funcionarioRepository.deleteAll();
        this.lancamentoRepository.deleteAll();
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioId() {
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
        assertEquals(2, lancamentos.size());
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioIdPaginado() {
        PageRequest page = new PageRequest(0, 10);
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
        assertEquals(2, lancamentos.getTotalElements());
    }
}
