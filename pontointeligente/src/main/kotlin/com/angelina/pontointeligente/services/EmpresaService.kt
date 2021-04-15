package com.angelina.pontointeligente.services

import com.angelina.pontointeligente.documents.Empresa
import org.springframework.stereotype.Service

@Service
interface EmpresaService {

    fun buscarPorCnpj(cnpj: String): Empresa?

    fun persistir(empresa: Empresa): Empresa
}