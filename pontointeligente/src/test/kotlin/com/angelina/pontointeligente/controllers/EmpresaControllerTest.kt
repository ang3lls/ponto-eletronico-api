package com.angelina.pontointeligente.controllers

import com.angelina.pontointeligente.documents.Empresa
import com.angelina.pontointeligente.services.EmpresaService
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class EmpresaControllerTest {
    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val empresaService: EmpresaService? = null

    private val CNPJ: String = "51463645000100"

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testBuscarEmpresaPeloCnpj() {
        val empresa: Empresa = empresa()
        BDDMockito.given<Empresa>(empresaService?.buscarPorCnpj(empresa.cnpj)).willReturn(empresa())

        mvc!!.perform(MockMvcRequestBuilders.get("/api/empresa/cnpj/{cnpj}", "51463645000100")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
    }

    private fun empresa(): Empresa = Empresa(1,"Razão Social", CNPJ)
}