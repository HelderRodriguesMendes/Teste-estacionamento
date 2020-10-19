package br.com.testePratico.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.Exception.RegistroNaoEncontradoException;
import br.com.testePratico.convert_DTO_Entity.Convert_DTO_Entity;
import br.com.testePratico.model.Movimentacao;
import br.com.testePratico.model.Valor;
import br.com.testePratico.model.DTO.VeiculoEdicao_DTO;
import br.com.testePratico.model.DTO.VeiculoEstacionado_DTO;
import br.com.testePratico.model.DTO.VeiculoSaida_DTO;
import br.com.testePratico.repository.MovimentacaoRepository;
import br.com.testePratico.repository.ValorRepository;

@Service
public class MovimentacaoService {

	@Autowired
	MovimentacaoRepository veiculoRepository;

	@Autowired
	ValorRepository valorRepository;

	Convert_DTO_Entity convert_DTO = new Convert_DTO_Entity();

	private int Add1Hora = 0;

	// CADASTRA UM NOVO VEICULO NO ESTACONAMENTO
	public Movimentacao cadastrar(Movimentacao veiculo) {
		Movimentacao veiculoSalvo = veiculoRepository.save(veiculo);

		return veiculoSalvo;
	}

	// BUSCA TODOS OS VEICULOS QUE AINDA ESTAO ESTACIONADOS
	public List<VeiculoEstacionado_DTO> findAllVeivuloEstacionado() {
		List<Movimentacao> veiculos = veiculoRepository.findAllVeivuloEstacionado()
				.orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
		return convert_DTO.toVeiculoEstacionado_DTO(veiculos);
	}

	// BUSCA TODOS OS VEICULOS QUE AINDA ESTAO ESTACIONADOS - POR PLACA
	public List<VeiculoEstacionado_DTO> findAllVeivuloEstacionado_Placa(String placa) {
		List<Movimentacao> veiculos = veiculoRepository.findAllVeivuloEstacionado_Placa(placa)
				.orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
		return convert_DTO.toVeiculoEstacionado_DTO(veiculos);
	}
	
	// BUSCA TODOS OS VEICULOS QUE JA SAIRAM DO ESTACIONAMENTO - POR PLACA
	public List<VeiculoEstacionado_DTO> findAllVeivulo_N_Estacionado_Placa(String placa) {
		List<Movimentacao> veiculos = veiculoRepository.findAllVeivulo_N_Estacionado_Placa(placa)
				.orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
		return convert_DTO.toVeiculoEstacionado_DTO(veiculos);
	}

	// BUSCA TODOS OS VEICULOS QUE AINDA ESTAO ESTACIONADOS - POR MODELO
	public List<VeiculoEstacionado_DTO> findAllVeivuloEstacionado_Modelo(String modelo) {
		List<Movimentacao> veiculos = veiculoRepository.findAllVeivuloEstacionado_Modelo(modelo)
				.orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
		return convert_DTO.toVeiculoEstacionado_DTO(veiculos);
	}
	
	// BUSCA TODOS OS VEICULOS QUE JA SAIRAM DO ESTACIONAMENTO - POR MODELO
	public List<VeiculoEstacionado_DTO> findAllVeivulo_N_Estacionado_Modelo(String modelo) {
		List<Movimentacao> veiculos = veiculoRepository.findAllVeivulo_N_Estacionado_Modelo(modelo)
				.orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
		return convert_DTO.toVeiculoEstacionado_DTO(veiculos);
	}

	// BUSCA TODOS OS VEICULOS QUE JÁ SAIRAM DO ESTACIONAMENTO
	public List<Movimentacao> findAllEstacionamentosFinalizados() {
		List<Movimentacao> veiculos = veiculoRepository.findAllEstacionamentosFinalizados()
				.orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
		return veiculos;
	}

	// ALTERA UM VEICULO QUE ESTA ESTACIONADO
	public List<VeiculoEstacionado_DTO> alterarVeiculoEstacionado(VeiculoEdicao_DTO edita_veiculo) {
		veiculoRepository.alterarVeiculoEstacionado(edita_veiculo.getModelo(), edita_veiculo.getPlaca(),
				edita_veiculo.getId());
		return findAllVeivuloEstacionado();
	}

	// FINALIZA O ESTACIONAMENTO DE UM VEICULO
	public List<Movimentacao> finalizarEstacionamento(VeiculoSaida_DTO veiculo) {
		veiculoRepository.save(calcularValor_Tempo(veiculo));

		return findAllEstacionamentosFinalizados();
	}

	// CALCULA O VALOR DO TEMPO UTILIZADO NO ESTACIONAMENTO
	public Movimentacao calcularValor_Tempo(VeiculoSaida_DTO veiculo) {
		Valor valores = valorRepository.getValores()
				.orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));

		Movimentacao dadosSalvos = veiculoRepository.findById(veiculo.getId())
				.orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));

		int totalHoras = 0;

		totalHoras = calcularHoras(dadosSalvos.getDataEntrada(), veiculo.getDataSaida(), dadosSalvos.getTempo(),
				veiculo.getTempo());

		// ADICIONANDO O VALOR DA PRIMEIRA HORA
		double VALORTOTAL = valores.getValor_primeira_hora();

		dadosSalvos.setTotalHoras(totalHoras);

		// DIMINUINDO A PRIMEIRA HORA, QUE É COBRADA NO VALOR DE 6 R$
		totalHoras -= 1;

		VALORTOTAL += totalHoras * valores.getValor_demais_horas();

		dadosSalvos.setId(veiculo.getId());
		dadosSalvos.setDataSaida(veiculo.getDataSaida());
		dadosSalvos.setValor(VALORTOTAL);

		return dadosSalvos;
	}

	// CALCULA O TOTAL DE HORAS NO GERAL, QUE O VEICULO FICOU NO ESTACIONAMENTO
	public int calcularHoras(LocalDate data_entro, LocalDate data_saiu, LocalTime hora_entro, LocalTime hora_saiu) {

		hora_entro = horaExata(hora_entro, false);
		hora_saiu = horaExata(hora_saiu, true);

		/*
		 * 23 PORQUE SE COLOCAR 24 DA ERRO SE COLOCAR 0, NÃO CALCULA CORRETAMENTE AS
		 * HORAS DO DIA QUE ENTRO NO ESTACIONAMENTO ENTÃO SEMPRE QUE CALCULAR O TOTAL DE
		 * HORAS UTILIZANDO ESTE (meiaNoite_entrada), DEVE SER ACRESCENTADO 1 HORA
		 */
		LocalTime meiaNoite_entrada = LocalTime.of(23, 0, 0);

		LocalTime meiaNoite_saida = LocalTime.of(0, 0, 0);

		int totalDias = 0, totalHoras = 0, h = 0;
		long horas = 0;
		Duration duracao;

		totalDias += calcularDias(data_entro, data_saiu);

		if (totalDias == 0) { // ENTRO E SAIU NO MESMO DIA

			duracao = Duration.between(hora_entro, hora_saiu);
			horas = duracao.toHours();
			h = (int) horas;
			totalHoras += negativoToPositivo(h) + Add1Hora;

		} else if (totalDias == 1) { // SAIU 1 DIA DEPOIS

			duracao = Duration.between(hora_entro, meiaNoite_entrada);
			horas = duracao.toHours();
			h = (int) horas; // HORAS DO DIA QUE ENTRO
			totalHoras += negativoToPositivo(h) + 1;

			horas = ChronoUnit.HOURS.between(meiaNoite_saida, hora_saiu);
			h = (int) horas; // HORAS DO DIA QUE SAIU
			totalHoras += negativoToPositivo(h) + Add1Hora;

		} else {

			/*
			 * DIMINUINDO O DIA DA SAIDA PORQUE ESSE DIA É CALCULADO SEPARADAMENTE POR NÃO
			 * UTILIZAR AS 24 HRS
			 */
			totalDias -= 1;

			duracao = Duration.between(hora_entro, meiaNoite_entrada);
			horas = duracao.toHours();
			h = (int) horas;
			totalHoras += negativoToPositivo(h) + 1; // TOTAL DE HORAS DO DIA QUE ENTRO

			horas = ChronoUnit.HOURS.between(meiaNoite_saida, hora_saiu);
			h = (int) horas;
			totalHoras += negativoToPositivo(h) + Add1Hora; // TOTAL DE HORAS DO DIA QUE SAIU

			totalHoras += totalDias * 24; // HORAS DOS RESTANTES DE DIAS QUE FICO
		}
		return totalHoras;
	}

	// REDUZ OS MINUTOS DA HORA PARA 00, PARA TRABALHAR APENAS COM HORAS EXATAS
	public LocalTime horaExata(LocalTime hora, boolean saida) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String horaFormat = hora.format(formatter);

		String[] h = horaFormat.split(":");

		// SE A HORA A SER ALTERADA, FOR UM HORARIO DE SAIDA DO VEICULO
		// E O HORARIO ULTRAPASSA MINUTOS, SERA ADICIONADO A MAIS DEPOIS O VALOR DE 1 HR
		if (saida) {
			String minu = h[1];
			if (!minu.equals("00")) {
				Add1Hora = 1;
			}
		}

		String horaExata = h[0] + ":00";
		LocalTime HORA = LocalTime.parse(horaExata);

		return HORA;
	}

	// CALCULAR INTERVALO DE DATAS
	public int calcularDias(LocalDate data_entro, LocalDate data_saiu) {
		long Dias;
		int d = 0;

		Dias = ChronoUnit.DAYS.between(data_entro, data_saiu);
		d = (int) Dias; // QUANTIDADE DE DIAS ENTRE DATA DE ENTRADA E DE SAIDA

		return d;
	}

	// CONVERTER NUMERO NEGATIVO PARA POSITIVO
	public static int negativoToPositivo(int n) {
		String regex = "" + n;
		regex = regex.replaceAll("[^0-9]", "");
		return Integer.parseInt(regex);
	}
}
