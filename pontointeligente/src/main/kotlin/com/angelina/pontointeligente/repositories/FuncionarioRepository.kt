package com.angelina.pontointeligente.repositories

import com.angelina.pontointeligente.documents.Funcionario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FuncionarioRepository : JpaRepository<Funcionario, Long> {

    fun findByEmail(email: String): Funcionario

    fun findByCpf(cpf: String): Funcionario

    fun findFuncById(id: Long): Funcionario
}