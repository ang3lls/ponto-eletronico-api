package com.angelina.pontointeligente.controllers

import com.angelina.pontointeligente.documents.Empresa
import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.dtos.CadastroPJDto
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
@RequestMapping("/api/cadastrar-pj")
class CadastroPJController (val empresaService: EmpresaService, val funcionarioService: FuncionarioService) {

    @PostMapping
    fun cadastrar(@Valid @RequestBody cadastrarPJDto: CadastroPJDto,
                  result: BindingResult): ResponseEntity<Response<CadastroPJDto>>{

        val response: Response<CadastroPJDto> = Response<CadastroPJDto>()

        validarDadosExistentes(cadastrarPJDto, result)
        if (result.hasErrors()){
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage!!)
            return ResponseEntity.badRequest().body(response)
        }

        val empresa: Empresa = converterDtoParaEmpresa(cadastrarPJDto)
        empresaService.persistir(empresa)

        var funcionario: Funcionario = converterDtoParaFuncionario(cadastrarPJDto, empresa)
        funcionarioService.persistir(funcionario)
        response.data = converterCadastroPJDto(funcionario, empresa)

        return ResponseEntity.ok(response)
    }

    private fun validarDadosExistentes(cadastrarPJDto: CadastroPJDto, result: BindingResult) {
        val empresa: Empresa? = empresaService.buscarPorCnpj(cadastrarPJDto.cnpj)
        if (empresa != null){
            result.addError(ObjectError("empresa", "Empresa já existente."))
        }

        val funcionarioCpf: Funcionario? = funcionarioService.buscarPorCpf(cadastrarPJDto.cpf)
        if (funcionarioCpf != null){
            result.addError(ObjectError("funcionario", "CPF já cadastrado."))
        }

        val funcionarioEmail: Funcionario? = funcionarioService.buscarPorEmail(cadastrarPJDto.email)
        if (funcionarioEmail != null){
            result.addError(ObjectError("funcionario", "Email já existente."))
        }
    }

    private fun converterDtoParaEmpresa(cadastrarPJDto: CadastroPJDto): Empresa =
        Empresa(1, cadastrarPJDto.razaoSocial, cadastrarPJDto.cnpj)

    private fun converterDtoParaFuncionario(cadastroPJDto: CadastroPJDto, empresa: Empresa) =
        Funcionario(1, cadastroPJDto.nome, cadastroPJDto.email, SenhaUtils().gerarBcrypt(cadastroPJDto.senha),
        cadastroPJDto.cpf, PerfilEnum.ROLE_ADMIN, empresa.id.toString(), valorHora = null, qtdHorasTrabalhoDia = null,
        qtdHorasAlmoco = null)

    private fun converterCadastroPJDto(funcionario: Funcionario, empresa: Empresa): CadastroPJDto =
        CadastroPJDto(funcionario.nome, funcionario.email, "", funcionario.cpf!!, empresa.cnpj,
            empresa.razaoSocial, funcionario.id)
}