package br.com.testePratico.repository;

import java.time.LocalDate;
import java.time.LocalTime;
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
	@Query(value = "select * from movimentacao where data_saida is not null", nativeQuery = true)
	Optional<List<Movimentacao>> findAllEstacionamentosFinalizados();
	
	@Transactional
	@Modifying
	@Query(value = "update movimentacao set modelo = ?1, placa = ?2 where id = ?3", nativeQuery = true)
	void alterarVeiculoEstacionado(String modelo, String placa, Long id);
	
	
	@Transactional
	@Modifying
	@Query(value = "update movimentacao set data_saida = ?1, tempo = ?2, valor = ?3 where id = ?4", nativeQuery = true)
	void finalizarEstacionamento(LocalDate dataSaida, LocalTime tempo, Double valor, Long id);
	
	@Transactional
	@Query(value = "select * from movimentacao where data_saida is null and placa like %?1% limit 100", nativeQuery = true)
	Optional<List<Movimentacao>> findAllVeivuloEstacionado_Placa(String placa);
	
	@Transactional
	@Query(value = "select * from movimentacao where data_saida is null and modelo like %?1% limit 100", nativeQuery = true)
	Optional<List<Movimentacao>> findAllVeivuloEstacionado_Modelo(String modelo);
}
