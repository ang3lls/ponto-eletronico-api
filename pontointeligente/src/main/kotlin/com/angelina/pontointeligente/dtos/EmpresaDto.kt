package com.angelina.pontointeligente.dtos

data class EmpresaDto (
    val razaoSocial: String,
    val cnpj: String,
    val id: Long? = null
)