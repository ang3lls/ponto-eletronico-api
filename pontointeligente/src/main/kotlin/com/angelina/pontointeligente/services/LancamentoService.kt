package com.angelina.pontointeligente.services

import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.documents.Lancamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.*

interface LancamentoService {

    fun buscarPorFuncionarioId(funcionarioId: String, pageRequest: PageRequest): Page<Lancamento>

    fun buscarPorId(id: Long): Optional<Lancamento>

    fun persistir(lancamento: Lancamento): Lancamento

    fun remover(id: Long)
}