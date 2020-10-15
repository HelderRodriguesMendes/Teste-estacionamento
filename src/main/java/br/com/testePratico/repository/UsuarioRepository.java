package br.com.testePratico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.testePratico.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
}
