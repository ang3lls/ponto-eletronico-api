package com.angelina.pontointeligente.services.impl

import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.repositories.FuncionarioRepository
import com.angelina.pontointeligente.services.FuncionarioService
import org.springframework.stereotype.Service
import java.util.*

@Service
class FuncionarioServiceImpl(val funcionarioRepository: FuncionarioRepository) : FuncionarioService {

    override fun persistir(funcionario: Funcionario): Funcionario = funcionarioRepository.save(funcionario)

    override fun buscarPorCpf(cpf: String): Funcionario? = funcionarioRepository.findByCpf(cpf)

    override fun buscarPorEmail(email: String): Funcionario? = funcionarioRepository.findByEmail(email)

    override fun buscarPorId(id: Long): Optional<Funcionario> = funcionarioRepository.findById(id)
}