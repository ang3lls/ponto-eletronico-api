package com.angelina.pontointeligente.services

import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.enums.PerfilEnum
import com.angelina.pontointeligente.repositories.FuncionarioRepository
import com.angelina.pontointeligente.utils.SenhaUtils
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import kotlin.jvm.Throws

@RunWith(SpringRunner::class)
@SpringBootTest
class FuncionarioServiceTest {

    @MockBean
    private val funcionarioRepository: FuncionarioRepository? = null

    @Autowired
    private val funcionarioService: FuncionarioService? = null

    private val email: String = "login@email.com"
    private val cpf: String = "34234855948"
    private val id: Long = 1

    private fun funcionario(): Funcionario =
        Funcionario(id, "Nome", email, SenhaUtils().gerarBcrypt("123456"), cpf, PerfilEnum.ROLE_USUARIO,
            "1", 40.0, 8f, 1f)



    @Test
    fun testPersistirFuncionario() {
        BDDMockito.given(funcionarioRepository?.save(Mockito.any(Funcionario::class.java))).willReturn(funcionario())
        val funcionario: Funcionario? = funcionarioService?.persistir(funcionario())
        assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorId(){
        BDDMockito.given(funcionarioRepository?.findFuncById(id)).willReturn(funcionario())
        val funcionario: Funcionario? = funcionarioService?.buscarPorId(id)
        assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorEmail() {
        BDDMockito.given(funcionarioRepository?.findByEmail(email)).willReturn(funcionario())
        val funcionario: Funcionario? = funcionarioService?.buscarPorEmail(email)
        assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorCpf() {
        BDDMockito.given(funcionarioRepository?.findByCpf(cpf)).willReturn(funcionario())
        val funcionario: Funcionario? = funcionarioService?.buscarPorCpf(cpf)
        assertNotNull(funcionario)
    }
}
