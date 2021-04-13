package com.angelina.pontointeligente.services

import com.angelina.pontointeligente.documents.Empresa
import com.angelina.pontointeligente.repositories.EmpresaRepository
import org.junit.Assert
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.jvm.Throws

@RunWith(SpringRunner::class)
@SpringBootTest
class EmpresaServiceTest {

    @Autowired
    val empresaService: EmpresaService? = null

    @Autowired
    private val empresaRepository: EmpresaRepository? = null

    private val CNPJ =  "51463645000100"

    private fun empresa(): Empresa = Empresa(1, "Razão Social", CNPJ)

    @Before
    @Throws(Exception::class)
    fun setUp(){
        BDDMockito.given(empresaRepository?.findByCnpj(CNPJ)).willReturn(empresa())
        BDDMockito.given(empresaRepository?.save(empresa())).willReturn(empresa())
    }

    @Test
    fun testBuscarEmpresaPorCnpj(){
        val empresa: Empresa? = empresaService?.buscarPorCnpj(CNPJ)
        Assert.assertNotNull(empresa)
    }

    @Test
    fun testPersistirEmpresa(){
        val empresa: Empresa? = empresaService?.persistir(empresa())
        Assert.assertNotNull(empresa)
    }
}