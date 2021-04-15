package com.angelina.pontointeligente.documents

import javax.persistence.*

@Entity
@Table(name = "empresa")
data class Empresa(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    val id: Long?,

    @Column(name = "razao_social")
    val razaoSocial: String,

    @Column(name = "cnpj")
    val cnpj: String
    )