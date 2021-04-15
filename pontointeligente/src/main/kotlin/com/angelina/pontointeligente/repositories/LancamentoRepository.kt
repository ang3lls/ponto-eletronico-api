package com.angelina.pontointeligente.repositories

import com.angelina.pontointeligente.documents.Lancamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LancamentoRepository : JpaRepository<Lancamento, Long>{

    fun findByFuncionarioId(funcionarioId: Long, pageable: Pageable): Page<Lancamento>

    fun findLancById(id: Long): Lancamento
}