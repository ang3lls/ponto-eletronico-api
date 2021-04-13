package com.angelina.pontointeligente.services

import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.enums.PerfilEnum
import com.angelina.pontointeligente.repositories.FuncionarioRepository
import com.angelina.pontointeligente.utils.SenhaUtils
import org.junit.Assert
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
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
        Funcionario(id, "Nome", email, SenhaUtils().gerarBcrypt("123456"), cpf, PerfilEnum.ROLE_USUARIO)

    @Before
    @Throws
    fun setUp(){
        BDDMockito.given(funcionarioRepository?.save(Mockito.any(Funcionario::class.java))).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findById(id)).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findByEmail(email)).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findByCpf(cpf)).willReturn(funcionario())
    }

    @Test
    fun testPersistirFuncionario() {
        val funcionario: Funcionario? = this.funcionarioService?.persistir(funcionario())
        Assert.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorId(){
        val funcionario: Optional<Funcionario>? = this.funcionarioService?.buscarPorId(id)
        Assert.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorEmail() {
        val funcionario: Funcionario? = this.funcionarioService?.buscarPorEmail(email)
        Assert.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorCpf() {
        val funcionario: Funcionario? = this.funcionarioService?.buscarPorCpf(cpf)
        Assert.assertNotNull(funcionario)
    }
}

private fun <T> BDDMockito.BDDMyOngoingStubbing<T>.willReturn(funcionario: Funcionario) {

}