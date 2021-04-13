package com.angelina.pontointeligente.repositories

import com.angelina.pontointeligente.documents.Empresa
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface EmpresaRepository : PagingAndSortingRepository<Empresa, Long>{

    fun findByCnpj(cnpj: String): Empresa?
}