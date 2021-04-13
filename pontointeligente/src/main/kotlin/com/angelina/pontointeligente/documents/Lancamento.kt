package com.angelina.pontointeligente.documents

import com.angelina.pontointeligente.enums.TipoEnum
import java.util.*
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "lancamento")
data class Lancamento(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lancamento")
    val id: Long?,

    @Column(name = "dia")
    val data: Date,

    @Transient
    val tipo: TipoEnum,

    @Column(name = "descricao")
    val descricao: String? = "",

    @Column(name = "localizacao")
    val localizacao: String? = "",

    @Transient
    val funcionarioId: Long,
)