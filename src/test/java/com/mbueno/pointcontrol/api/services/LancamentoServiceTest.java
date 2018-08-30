package com.mbueno.pointcontrol.api.services;

import com.mbueno.pointcontrol.api.entities.Lancamento;
import com.mbueno.pointcontrol.api.repositories.LancamentoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @MockBean
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Before
    public void setUP() {
        BDDMockito.given(lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
        BDDMockito.given(lancamentoRepository.findById(Mockito.anyLong()))
                .willReturn(java.util.Optional.of(new Lancamento()));
        BDDMockito.given(lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
                .willReturn(new PageImpl<>(new ArrayList<>()));
    }

    @Test
    public void testPersistirLancamento() {
        Lancamento lancamento = lancamentoService.persistir(new Lancamento());
        assertNotNull(lancamento);
    }

    @Test
    public void testBuscarLancamentoPorId() {
        Optional<Lancamento> lancamento = lancamentoService.buscarPorId(1L);
        assertTrue(lancamento.isPresent());
    }

    @Test
    public void testBuscarLancamentoPorFuncionarioId() {
        Page<Lancamento> lancamento =
                lancamentoService.buscarPorIdFuncionario(1L, new PageRequest(0, 10));
        assertNotNull(lancamento);
    }

}
