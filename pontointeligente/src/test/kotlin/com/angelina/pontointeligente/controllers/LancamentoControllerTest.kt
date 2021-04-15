package com.angelina.pontointeligente.controllers

import com.angelina.pontointeligente.PontoInteligenteApiApplication
import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.documents.Lancamento
import com.angelina.pontointeligente.dtos.LancamentoDto
import com.angelina.pontointeligente.enums.PerfilEnum
import com.angelina.pontointeligente.enums.TipoEnum
import com.angelina.pontointeligente.services.FuncionarioService
import com.angelina.pontointeligente.services.LancamentoService
import com.angelina.pontointeligente.utils.SenhaUtils
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LancamentoControllerTest {
    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    private val urlBase: String = "/api/lancamentos/"
    private val idFuncionario: Long = 1
    private val idLancamento: Long = 1
    private val empresaId: String = "1"
    private val tipo: String = TipoEnum.INICIO_TRABALHO.name
    private val data: Date = Date()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testCadastrarLancamento() {
        val lancamento: Lancamento = obterDadosLancamento()
        BDDMockito.given<Funcionario>(funcionarioService?.buscarPorId(idFuncionario))
            .willReturn(funcionario())
        BDDMockito.given(lancamentoService?.persistir(obterDadosLancamento()))
            .willReturn(lancamento)

        mvc!!.perform(MockMvcRequestBuilders.post("/api/lancamentos/save")
            .content(obterJsonRequisicaoPost())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.tipo").value(tipo))
            .andExpect(jsonPath("$.data.data").value(dateFormat.format(data)))
            .andExpect(jsonPath("$.data.funcionarioId").value(idFuncionario))
            .andExpect(jsonPath("$.erros").isEmpty())
    }

//    @Test
//    @WithMockUser
//    @Throws(Exception::class)
//    fun testCadastrarLancamentoFuncionarioIdInvalido() {
//        BDDMockito.given<Funcionario>(funcionarioService?.buscarPorId(idFuncionario))
//            .willReturn(null)
//
//        mvc!!.perform(MockMvcRequestBuilders.post("/api/lancamentos/save")
//            .content(obterJsonRequisicaoPost())
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.erros").value("Funcionário não encontrado. ID inexistente."))
//            .andExpect(jsonPath("$.data").isEmpty())
//    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = arrayOf("ADMIN"))
    @Throws(Exception::class)
    fun testRemoverLancamento() {
        BDDMockito.given<Lancamento>(lancamentoService?.buscarPorId(idLancamento))
            .willReturn(obterDadosLancamento())

        mvc!!.perform(MockMvcRequestBuilders.delete("/api/lancamentos/delete/{id}", 1)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
    }

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testRemoverLancamentoAcessoNegado() {
        BDDMockito.given<Lancamento>(lancamentoService?.buscarPorId(idLancamento))
            .willReturn(obterDadosLancamento())

        mvc!!.perform(MockMvcRequestBuilders.delete("/api/lancamentos/delete/{id}", 1)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden())
    }

    @Throws(JsonProcessingException::class)
    private fun obterJsonRequisicaoPost(): String {
        val lancamentoDto: LancamentoDto = LancamentoDto(
            dateFormat.format(data), tipo, "Descrição",
            "1.234,4.234", idFuncionario)
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(lancamentoDto)
    }

    private fun obterDadosLancamento(): Lancamento =
        Lancamento(1, data, TipoEnum.valueOf(tipo),
            "Descrição","1.243,4.345", idFuncionario)

    private fun funcionario(): Funcionario =
        Funcionario(1,"Nome", "email@email.com", SenhaUtils().gerarBcrypt("123456"),
            "23145699876", PerfilEnum.ROLE_USUARIO, empresaId , 40.0, 8f,
            1f)
}