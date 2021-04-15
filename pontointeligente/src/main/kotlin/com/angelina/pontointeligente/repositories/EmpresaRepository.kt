package com.angelina.pontointeligente.repositories

import com.angelina.pontointeligente.documents.Empresa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmpresaRepository : JpaRepository<Empresa, Long>{

    fun findByCnpj(cnpj: String): Empresa
}