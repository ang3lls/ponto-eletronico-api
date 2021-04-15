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
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import kotlin.jvm.Throws

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
class EmpresaServiceTest {

    @Autowired
    lateinit var empresaService: EmpresaService

    @MockBean
    private lateinit var empresaRepository: EmpresaRepository

    private val CNPJ = "51463645000100"

    @Test
    fun testBuscarEmpresaPorCnpj() {
        BDDMockito.given(empresaRepository.findByCnpj(CNPJ)).willReturn(empresa())
        val empresa: Empresa? = empresaService.buscarPorCnpj(CNPJ)
        Assert.assertNotNull(empresa)
    }

    @Test
    fun testPersistirEmpresa() {
        BDDMockito.given(empresaRepository.save(empresa())).willReturn(empresa())
        val empresa: Empresa? = empresaService.persistir(empresa())
        Assert.assertNotNull(empresa)
    }

    private fun empresa(): Empresa = Empresa(1,"Razão Social", CNPJ)

}