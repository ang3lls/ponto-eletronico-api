package com.angelina.pontointeligente.services

import com.angelina.pontointeligente.documents.Lancamento
import com.angelina.pontointeligente.enums.TipoEnum
import com.angelina.pontointeligente.repositories.LancamentoRepository
import org.junit.Assert
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

    private val funcionaioId: String = "1"
    private val id: Long = 1

    private fun lancamento(): Lancamento  = Lancamento(id, Date(), TipoEnum.INICIO_TRABALHO, funcionaioId)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given<Page<Lancamento>>(lancamentoRepository?.findByFuncionarioId(funcionaioId, PageRequest.of(0, 10)))
            .willReturn(PageImpl(ArrayList<Lancamento>()))
        BDDMockito.given(lancamentoRepository?.getOne(java.lang.Long.valueOf("1"))).willReturn(lancamento())
        BDDMockito.given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java)))
    }

    @Test
    fun testBuscarLancamentoPorfuncionarioId() {
        val lancamento: Page<Lancamento>? =
            lancamentoService?.buscarPorFuncionarioId(funcionaioId, PageRequest.of(0, 10))
        Assert.assertNotNull(lancamento)
    }

    @Test
    fun testBuscarLancamentoPorId() {
        val lancamento: Optional<Lancamento>? = lancamentoService?.buscarPorId(id)
        Assert.assertNotNull(lancamento)
    }

    @Test
    fun testPersistirLancamento() {
        val lancamento: Lancamento? = lancamentoService?.persistir(lancamento())
        Assert.assertNotNull(lancamento)
    }
}