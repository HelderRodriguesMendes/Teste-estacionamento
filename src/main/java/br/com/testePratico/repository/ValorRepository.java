package br.com.testePratico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.Valor;


public interface ValorRepository extends JpaRepository<Valor, Long>{
	
	@Transactional
	@Query(value = "select * from valores", nativeQuery = true)
	Optional<Valor> getValores();
}
