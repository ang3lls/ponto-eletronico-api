package com.angelina.pontointeligente.services.impl

import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.repositories.FuncionarioRepository
import com.angelina.pontointeligente.services.FuncionarioService
import org.springframework.stereotype.Service
import java.util.*

@Service
class FuncionarioServiceImpl(val funcionarioRepository: FuncionarioRepository) : FuncionarioService {

    override fun persistir(funcionario: Funcionario): Funcionario = funcionarioRepository.save(funcionario)

    override fun buscarPorCpf(cpf: String) = funcionarioRepository.findByCpf(cpf)

    override fun buscarPorEmail(email: String) = funcionarioRepository.findByEmail(email)

    override fun buscarPorId(id: Long) = funcionarioRepository.findFuncById(id)
}