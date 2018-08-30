package com.mbueno.pointcontrol.api.services;

import com.mbueno.pointcontrol.api.entities.Empresa;
import com.mbueno.pointcontrol.api.repositories.EmpresaRepository;
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

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

    private static final String CNPJ = "51463645000100";
    @MockBean
    private EmpresaRepository empresaRepository;
    @Autowired
    private EmpresaService empresaService;

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
        BDDMockito.given(empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
    }

    @Test
    public void testBuscarEmpresaPorCnpj() {
        Optional<Empresa> empresa = empresaService.buscarPorCnpj(CNPJ);
        assertTrue(empresa.isPresent());
    }

    @Test
    public void testPersistirEmpresa() {
        Empresa empresa = empresaService.persistir(new Empresa());
        assertNotNull(empresa);
    }

    @Test
    public void testCnpjInvalido() {
        Optional<Empresa> empresa = empresaService.buscarPorCnpj("555555");
        assertNull(null);
    }
}
