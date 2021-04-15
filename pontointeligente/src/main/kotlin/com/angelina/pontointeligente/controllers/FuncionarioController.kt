package com.angelina.pontointeligente.controllers

import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.dtos.FuncionarioDto
import com.angelina.pontointeligente.response.Response
import com.angelina.pontointeligente.services.FuncionarioService
import com.angelina.pontointeligente.utils.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/funcionarios")
class FuncionarioController(val funcionarioService: FuncionarioService) {
    
    @PutMapping("/{id}")
    fun atualizar(@PathVariable("id") id: Long, @Valid @RequestBody funcionarioDto: FuncionarioDto,
    result: BindingResult): ResponseEntity<Response<FuncionarioDto>> {
         
        val response: Response<FuncionarioDto> = Response<FuncionarioDto>()
        val funcionario: Funcionario? = funcionarioService.buscarPorId(id)
        
        if (funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionario não encontrado."))
        }
        
        if (result.hasErrors()) {
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage!!)
            return ResponseEntity.badRequest().body(response)
        }
        
        val funcAtualizar: Funcionario = atualizarDadosFuncionario(funcionario!!, funcionarioDto)
        funcionarioService.persistir(funcAtualizar)
        response.data = converterFuncionarioDto(funcAtualizar)

        return ResponseEntity.ok(response)
    }

    private fun converterFuncionarioDto(funcionario: Funcionario): FuncionarioDto =
        FuncionarioDto(funcionario.nome, funcionario.email, "", funcionario.valorHora.toString(),
            funcionario.qtdHorasTrabalhoDia.toString(), funcionario.qtdHorasAlmoco.toString(), funcionario.id)

    private fun atualizarDadosFuncionario(funcionario: Funcionario, funcionarioDto: FuncionarioDto): Funcionario {
        var senha: String
        if (funcionarioDto.senha == null) {
            senha = funcionario.senha
        } else {
            senha = SenhaUtils().gerarBcrypt(funcionarioDto.senha)
        }

        return Funcionario(funcionario.id, funcionarioDto.nome, funcionario.email, senha, funcionario.cpf,
            funcionario.perfil, funcionario.empresaId, funcionarioDto.valorHora?.toDouble(),
            funcionarioDto.qtdHorasTrabalhoDia?.toFloat(), funcionarioDto.qtdHorasAlmoco?.toFloat())
    }

}