package com.angelina.pontointeligente.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

//tranforma a String senha em codigo criptografado
class SenhaUtils {

    fun gerarBcrypt(senha: String): String = BCryptPasswordEncoder().encode(senha)
}