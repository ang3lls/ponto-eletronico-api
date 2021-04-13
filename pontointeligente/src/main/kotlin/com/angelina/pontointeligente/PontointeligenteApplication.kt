package com.angelina.pontointeligente

import com.angelina.pontointeligente.documents.Empresa
import com.angelina.pontointeligente.documents.Funcionario
import com.angelina.pontointeligente.enums.PerfilEnum
import com.angelina.pontointeligente.repositories.EmpresaRepository
import com.angelina.pontointeligente.repositories.FuncionarioRepository
import com.angelina.pontointeligente.repositories.LancamentoRepository
import com.angelina.pontointeligente.utils.SenhaUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PontointeligenteApplication(val empresaRepository: EmpresaRepository,
								  val funcionarioRepository: FuncionarioRepository,
								  val lancamentoRepository: LancamentoRepository) : CommandLineRunner{

	override fun run(vararg args: String?) {
		empresaRepository.deleteAll()
		funcionarioRepository.deleteAll()
		lancamentoRepository.deleteAll()

		val empresa: Empresa = Empresa(1, "Empresa", "10443887000146")
		empresaRepository.save(empresa)

		val admin: Funcionario = Funcionario(1, "Admin", "admin@empresa.com",
			SenhaUtils().gerarBcrypt("123456"), "25700317077", PerfilEnum.ROLE_ADMIN, empresa.id!!)
		funcionarioRepository.save(admin)

		val funcionario: Funcionario = Funcionario(1, "Funcionario", "funcionario@empresa.com",
		SenhaUtils().gerarBcrypt("123456"), "44325441557", PerfilEnum.ROLE_USUARIO, empresa.id!!)
		funcionarioRepository.save(funcionario)

		System.out.println("Empresa ID: " + empresa.id)
		System.out.println("Admin ID: " + admin.id)
		System.out.println("Funcionario ID: " + funcionario.id)
	}

}

fun main(args: Array<String>) {
	runApplication<PontointeligenteApplication>(*args)
}
