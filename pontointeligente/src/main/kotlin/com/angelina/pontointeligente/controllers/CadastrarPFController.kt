package com.angelina.pontointeligente.controllers

import com.angelina.pontointeligente.documents.Empresa
import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.dtos.CadastroPFDto
import com.angelina.pontointeligente.enums.PerfilEnum
import com.angelina.pontointeligente.response.Response
import com.angelina.pontointeligente.services.EmpresaService
import com.angelina.pontointeligente.services.FuncionarioService
import com.angelina.pontointeligente.utils.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/cadastrar-pf")
class CadastrarPFController(val empresaService: EmpresaService, val funcionarioService: FuncionarioService) {

    @PostMapping
    fun cadastrar(@Valid @RequestBody cadastrarPFDto: CadastroPFDto,
                  result: BindingResult): ResponseEntity<Response<CadastroPFDto>> {

        val response: Response<CadastroPFDto> = Response<CadastroPFDto>()

        val empresa: Empresa? = empresaService.buscarPorCnpj(cadastrarPFDto.cnpj)
        validarDadosExistentes(cadastrarPFDto, empresa, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage!!)
            return ResponseEntity.badRequest().body(response)
        }

        var funcionario: Funcionario = converterDtoParaFuncionario(cadastrarPFDto, empresa!!)

        funcionarioService.persistir(funcionario)
        response.data = converterCadastroPFDto(funcionario, empresa!!)

        return ResponseEntity.ok(response)
    }

    private fun converterCadastroPFDto(funcionario: Funcionario, empresa: Empresa): CadastroPFDto =
        CadastroPFDto(funcionario.nome, funcionario.email, "", funcionario.cpf, empresa.cnpj,
        empresa.id.toString(), funcionario.valorHora.toString(), funcionario.qtdHorasTrabalhoDia.toString(),
        funcionario.qtdHorasAlmoco.toString(), funcionario.id)


    private fun converterDtoParaFuncionario(cadastrarPFDto: CadastroPFDto, empresa: Empresa) =
        Funcionario(cadastrarPFDto.id, cadastrarPFDto.nome, cadastrarPFDto.email,
        SenhaUtils().gerarBcrypt(cadastrarPFDto.senha), cadastrarPFDto.cpf, PerfilEnum.ROLE_USUARIO,
        empresa.id.toString(), cadastrarPFDto.valorHora?.toDouble(), cadastrarPFDto.qtdHorasTrabalhoDia?.toFloat(),
        cadastrarPFDto.qtdHorasAlmoco?.toFloat())

    private fun validarDadosExistentes(cadastrarPFDto: CadastroPFDto, empresa: Empresa?, result: BindingResult) {

        if (empresa == null){
            result.addError(ObjectError("empresa", "Empresa não cadastrada."))
        }

        val funcionarioCpf: Funcionario? = funcionarioService.buscarPorCpf(cadastrarPFDto.cpf!!)
        if (funcionarioCpf != null){
            result.addError(ObjectError("funcionario", "CPF já existente."))
        }

        val funcionarioEmail: Funcionario? = funcionarioService.buscarPorEmail(cadastrarPFDto.email)
        if (funcionarioEmail != null){
            result.addError(ObjectError("funcionario", "Email já existente."))
        }

    }

}