package com.angelina.pontointeligente.controllers

import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.documents.Lancamento
import com.angelina.pontointeligente.dtos.LancamentoDto
import com.angelina.pontointeligente.enums.TipoEnum
import com.angelina.pontointeligente.response.Response
import com.angelina.pontointeligente.services.FuncionarioService
import com.angelina.pontointeligente.services.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("api/lancamentos")
class LancamentoController(val lancamentoService: LancamentoService, val funcionarioService: FuncionarioService) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Value("\${paginacao.qtd_por_pagina}")
    val qtdPorPagina: Int = 15

    @PostMapping("/save")
    fun adicionar(@Valid @RequestBody lancamentoDto: LancamentoDto,
                  result: BindingResult): ResponseEntity<Response<LancamentoDto>>{
        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        validarFuncionario(lancamentoDto, result)

        //tratar o erro, erros que podem ter vindo do validar funcionario
        if (result.hasErrors()){
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage!!)
            return ResponseEntity.badRequest().body(response)
        }
        
        val lancamento: Lancamento = converterDtoParaLancamento(lancamentoDto, result)
        lancamentoService.persistir(lancamento)
        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/getId/{id}")
    fun listarPorId(@PathVariable("id") id: Long): ResponseEntity<Response<LancamentoDto>>{
        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)

        //tratamento de erro
        if (lancamento == null){
            response.erros.add("Lan??amento n??o encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/getFunByID/{id}")
    fun listarPorFuncionarioId(@PathVariable("funcionarioId") funcionarioId: Long,
                                @RequestParam("pag", defaultValue = "0") pag: Int,
                                @RequestParam("ord", defaultValue = "id") ord: String,
                                @RequestParam("dir", defaultValue = "DESC") dir: String):
            ResponseEntity<Response<Page<LancamentoDto>>>{

        val response: Response<Page<LancamentoDto>> = Response<Page<LancamentoDto>>()

        val pageRequest: PageRequest = PageRequest.of(pag, qtdPorPagina, Sort.Direction.valueOf(dir), ord)
        val lancamentos: Page<Lancamento> = lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest)

        val lancamentoDto: Page<LancamentoDto> = lancamentos.map { lancamento -> converterLancamentoDto(lancamento) }

        response.data = lancamentoDto
        return ResponseEntity.ok(response)

    }

    @PutMapping("/update/{id}")
    fun atualizar(@PathVariable("id") id: Long, @Valid @RequestBody lancamentoDto: LancamentoDto,
                    result: BindingResult): ResponseEntity<Response<LancamentoDto>> {

        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        validarFuncionario(lancamentoDto, result)
        lancamentoDto.id = id
        val lancamento: Lancamento = converterDtoParaLancamento(lancamentoDto, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage!!)
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.persistir(lancamento)
        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun remover(@PathVariable("id") id: Long): ResponseEntity<Response<String>>{

        val response: Response<String> = Response<String>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)

        if (lancamento == null){
            response.erros.add("Erro ao remover lan??amento. Registro n??o encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.remover(id)
        return ResponseEntity.ok(Response<String>())
    }

    private fun converterLancamentoDto(lancamento: Lancamento): LancamentoDto =
        LancamentoDto(dateFormat.format(lancamento.data), lancamento.tipo.toString(),
            lancamento.descricao, lancamento.localizacao,
            lancamento.funcionarioId, lancamento.id)

    private fun converterDtoParaLancamento(lancamentoDto: LancamentoDto, result: BindingResult): Lancamento {
        if (lancamentoDto.id != null){
            val lanc: Lancamento? = lancamentoService.buscarPorId(lancamentoDto.id!!)
            if (lanc == null) result.addError(
                ObjectError("lancamento",
                "Lan??amento n??o encontrado.")
            )
        }

        return Lancamento(lancamentoDto.id, dateFormat.parse(lancamentoDto.data), TipoEnum.valueOf(lancamentoDto.tipo!!),
             lancamentoDto.descricao, lancamentoDto.localizacao, lancamentoDto.funcionarioId!!)
    }

    private fun validarFuncionario(lancamentoDto: LancamentoDto, result: BindingResult) {
        if (lancamentoDto.funcionarioId == null){
            result.addError(ObjectError("funcionario", "Funcion??rio n??o informado."))
            return
        }

        val funcionario: Funcionario = funcionarioService.buscarPorId(lancamentoDto.funcionarioId)
        if (funcionario == null) {
            result.addError(
                ObjectError("funcionario",
                "Funcion??rio n??o encontrado. ID inexistente.")
            )
        }
    }

}