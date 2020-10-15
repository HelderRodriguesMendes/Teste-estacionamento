package br.com.testePratico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.testePratico.model.Usuario;
import br.com.testePratico.model.Valor;
import br.com.testePratico.repository.UsuarioRepository;
import br.com.testePratico.repository.ValorRepository;

@SpringBootApplication
public class EstacionamentoApplication implements CommandLineRunner{
	@Autowired
	UsuarioRepository usu;
	
	@Autowired
	ValorRepository valor;

	public static void main(String[] args) {
		SpringApplication.run(EstacionamentoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Usuario usuario = new Usuario("admin", "admin", 123456);
		usu.save(usuario);
		
		Valor v = new Valor(6.00, 4.00);
		valor.save(v);
	}

}
