package br.com.testePratico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.Movimentacao;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>{
	
	@Transactional
	@Query(value = "select * from movimentacao where data_saida is null", nativeQuery = true)
	Optional<List<Movimentacao>> findAllVeivuloEstacionado();
	
	@Transactional
	@Modifying
	@Query(value = "update movimentacao set modelo = ?1, placa = ?2 where id = ?3", nativeQuery = true)
	void alterarVeiculoEstacionado(String modelo, String placa, Long id);
}
