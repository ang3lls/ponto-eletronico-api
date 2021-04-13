package com.angelina.pontointeligente.repositories

import com.angelina.pontointeligente.documents.Funcionario
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface FuncionarioRepository : PagingAndSortingRepository<Funcionario, Long> {

    fun findByEmail(email: String): Funcionario?

    fun findByCpf(cpf: String): Funcionario?
}