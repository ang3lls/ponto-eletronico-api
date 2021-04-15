package com.angelina.pontointeligente.services

import com.angelina.pontointeligente.documents.Lancamento
import com.angelina.pontointeligente.enums.TipoEnum
import com.angelina.pontointeligente.repositories.LancamentoRepository
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

@RunWith(SpringRunner::class)
@SpringBootTest
class LancamentoServiceTest {

    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    @Autowired
    private val lancamentoService: LancamentoService? = null

    private val funcionaioId: Long = 1
    private val id: Long = 1
    private val descricao: String = ""
    private val localizacao:String = ""

    private fun lancamento(): Lancamento  = Lancamento(id, Date(), TipoEnum.INICIO_TRABALHO, descricao, localizacao,
    funcionaioId)

    @Test
    fun testBuscarLancamentoPorfuncionarioId() {
        BDDMockito.given<Page<Lancamento>>(lancamentoRepository?.findByFuncionarioId(funcionaioId, PageRequest.of(0, 10)))
            .willReturn(PageImpl(ArrayList<Lancamento>()))
        val lancamento: Page<Lancamento>? =
            lancamentoService?.buscarPorFuncionarioId(funcionaioId, PageRequest.of(0, 10))
        assertNotNull(lancamento)
    }

    @Test
    fun testBuscarLancamentoPorId() {
        BDDMockito.given(lancamentoRepository?.findLancById(id)).willReturn(lancamento())
        val lancamento: Lancamento? = lancamentoService?.buscarPorId(id)
        assertNotNull(lancamento)
    }

    @Test
    fun testPersistirLancamento() {
        BDDMockito.given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java))).willReturn(lancamento())
        val lancamento: Lancamento? = lancamentoService?.persistir(lancamento())
        assertNotNull(lancamento)
    }
}