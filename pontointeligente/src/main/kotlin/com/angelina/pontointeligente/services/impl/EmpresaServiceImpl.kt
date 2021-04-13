package com.angelina.pontointeligente.services.impl

import com.angelina.pontointeligente.documents.Empresa
import com.angelina.pontointeligente.repositories.EmpresaRepository
import com.angelina.pontointeligente.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository): EmpresaService {

    override fun buscarPorCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj)

    override fun persistir(empresa: Empresa): Empresa = empresaRepository.save(empresa)

}