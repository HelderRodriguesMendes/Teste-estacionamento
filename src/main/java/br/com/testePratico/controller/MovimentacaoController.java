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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.testePratico.model.Movimentacao;
import br.com.testePratico.model.DTO.VeiculoEdicao_DTO;
import br.com.testePratico.model.DTO.VeiculoEstacionado_DTO;
import br.com.testePratico.model.DTO.VeiculoSaida_DTO;
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
	
	@GetMapping("/VeivulosEstacionadosPlaca")
	public ResponseEntity<List<VeiculoEstacionado_DTO>> findAllVeivuloEstacionado_Placa(@RequestParam String placa){
		List<VeiculoEstacionado_DTO> veiculos = veiculoService.findAllVeivuloEstacionado_Placa(placa);
		return new ResponseEntity<List<VeiculoEstacionado_DTO>>(veiculos, HttpStatus.OK);
	}
	
	@GetMapping("/Veivulos_N_EstacionadosPlaca")
	public ResponseEntity<List<VeiculoEstacionado_DTO>> findAllVeivulo_N_Estacionado_Placa(@RequestParam String placa){
		List<VeiculoEstacionado_DTO> veiculos = veiculoService.findAllVeivulo_N_Estacionado_Placa(placa);
		return new ResponseEntity<List<VeiculoEstacionado_DTO>>(veiculos, HttpStatus.OK);
	}
	
	@GetMapping("/VeivulosEstacionadosModelo")
	public ResponseEntity<List<VeiculoEstacionado_DTO>> findAllVeivuloEstacionado_Modelo(@RequestParam String modelo){
		List<VeiculoEstacionado_DTO> veiculos = veiculoService.findAllVeivuloEstacionado_Modelo(modelo);
		return new ResponseEntity<List<VeiculoEstacionado_DTO>>(veiculos, HttpStatus.OK);
	}
	
	@GetMapping("/Veivulos_N_EstacionadosModelo")
	public ResponseEntity<List<VeiculoEstacionado_DTO>> findAllVeivulo_N_Estacionado_Modelo(@RequestParam String modelo){
		List<VeiculoEstacionado_DTO> veiculos = veiculoService.findAllVeivulo_N_Estacionado_Modelo(modelo);
		return new ResponseEntity<List<VeiculoEstacionado_DTO>>(veiculos, HttpStatus.OK);
	}
	
	@GetMapping("/estacionamentosFinalizados")
	public ResponseEntity<List<Movimentacao>> findAllEstacionamentosFinalizados(){
		System.out.println("finalizados");
		List<Movimentacao> veiculos = veiculoService.findAllEstacionamentosFinalizados();
		return new ResponseEntity<List<Movimentacao>>(veiculos, HttpStatus.OK);
	}
	
	@PutMapping("/alterarVeiculoEstacionado/{id}")
	public ResponseEntity<List<VeiculoEstacionado_DTO>>alterarVeiculoEstacionado(@RequestBody VeiculoEdicao_DTO edita_veiculo, @PathVariable("id") Long id){
		System.out.println("alterar id: " + id);
		System.out.println("alterar modelo: " + edita_veiculo.getModelo());
		edita_veiculo.setId(id);
		return new ResponseEntity<List<VeiculoEstacionado_DTO>>(veiculoService.alterarVeiculoEstacionado(edita_veiculo), HttpStatus.OK);
	}
	
	@PutMapping("/finalizarEstacionamento/{id}")
	public ResponseEntity<List<Movimentacao>>finalizarEstacionamento(@RequestBody VeiculoSaida_DTO veiculo, @PathVariable("id") Long id){
		veiculo.setId(id);
		return new ResponseEntity<List<Movimentacao>>(veiculoService.finalizarEstacionamento(veiculo), HttpStatus.OK);
	}
}
