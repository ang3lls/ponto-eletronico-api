package com.angelina.pontointeligente.documents

import com.angelina.pontointeligente.enums.PerfilEnum
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "funcionario")
data class Funcionario (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    val id: Long,

    @Column(name = "nome")
    val nome: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "senha")
    val senha: String,

    @Column(name = "cpf")
    val cpf: String,

    @Transient
    val perfil: PerfilEnum,

    val empresaId: Long,
//    val valorHora: String,
//    val qtdHorasTrabalhoDia: String,
//    val qtdHorasAlmoco: String
)