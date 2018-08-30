package com.mbueno.pointcontrol.api.repositories;

import com.mbueno.pointcontrol.api.entities.Empresa;
import com.mbueno.pointcontrol.api.mock.DataForTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest extends DataForTests {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Before
    public void setUp() throws Exception {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa para teste");
        empresa.setCnpj(CNPJ);
        empresa.setDataAtualizacao(new Date());
        empresa.setDataCriacao(new Date());
        this.empresaRepository.save(empresa);
    }

    @After
    public final void tearDown() {
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarPorCnpj() {
        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);

        assertEquals(CNPJ, empresa.getCnpj());
    }
}
