package com.angelina.pontointeligente.services.impl

import com.angelina.pontointeligente.documents.Lancamento
import com.angelina.pontointeligente.repositories.LancamentoRepository
import com.angelina.pontointeligente.services.LancamentoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class LancamentoServiceImpl(val lancamentoRepository: LancamentoRepository) : LancamentoService {

    override fun buscarPorFuncionarioId(funcionarioId: Long, pageRequest: PageRequest): Page<Lancamento> =
        lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest)

    override fun buscarPorId(id: Long) = lancamentoRepository.findLancById(id)

    override fun persistir(lancamento: Lancamento) = lancamentoRepository.save(lancamento)

    override fun remover(id: Long) = lancamentoRepository.deleteById(id)
}