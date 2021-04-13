package com.angelina.pontointeligente.services

import com.angelina.pontointeligente.documents.Funcionario
import java.util.*

interface FuncionarioService {

    fun persistir(funcionario: Funcionario): Funcionario

    fun buscarPorCpf(cpf: String): Funcionario?

    fun buscarPorEmail(email: String): Funcionario?

    fun buscarPorId(id: Long): Funcionario
}