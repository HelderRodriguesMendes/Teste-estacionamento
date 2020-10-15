package br.com.testePratico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.testePratico.model.Movimentacao;
import br.com.testePratico.model.DTO.VeiculoEdicao_DTO;
import br.com.testePratico.model.DTO.VeiculoEstacionado_DTO;
import br.com.testePratico.service.MovimentacaoService;

@RestController
@RequestMapping("/veiculo")
public class MovimentacaoController {
	
	@Autowired
	MovimentacaoService veiculoService;
	
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Movimentacao> cadastrar(@RequestBody Movimentacao veiculo){
		return new ResponseEntity<Movimentacao>(veiculoService.cadastrar(veiculo), HttpStatus.CREATED);
	}
	
	@GetMapping("/VeivulosEstacionados")
	public ResponseEntity<List<VeiculoEstacionado_DTO>> findAllVeivuloEstacionado(){
		List<VeiculoEstacionado_DTO> veiculos = veiculoService.findAllVeivuloEstacionado();
		return new ResponseEntity<List<VeiculoEstacionado_DTO>>(veiculos, HttpStatus.OK);
	}
	
	@PutMapping("/alterarVeiculoEstacionado/{id}")
	public ResponseEntity<List<VeiculoEstacionado_DTO>>alterarVeiculoEstacionado(@RequestBody VeiculoEdicao_DTO edita_veiculo, @PathVariable("id") Long id){
		edita_veiculo.setId(id);
		return new ResponseEntity<List<VeiculoEstacionado_DTO>>(veiculoService.alterarVeiculoEstacionado(edita_veiculo), HttpStatus.OK);
	}
}
