package br.com.testePratico.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import br.com.testePratico.repository.MovimentacaoRepository;
import br.com.testePratico.repository.ValorRepository;

@Service
public class MovimentacaoService {

	@Autowired
	MovimentacaoRepository veiculoRepository;
	
	@Autowired
	ValorRepository valorRepository;
	
	Convert_DTO_Entity convert_DTO = new Convert_DTO_Entity();

	public Movimentacao cadastrar(Movimentacao veiculo) {
		Movimentacao veiculoSalvo = veiculoRepository.save(veiculo);

		return veiculoSalvo;
	}

	public List<VeiculoEstacionado_DTO> findAllVeivuloEstacionado() {
		List<Movimentacao> veiculos = veiculoRepository.findAllVeivuloEstacionado().orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
		return convert_DTO.toVeiculoEstacionado_DTO(veiculos);
	}
	
	public List<VeiculoEstacionado_DTO> alterarVeiculoEstacionado(VeiculoEdicao_DTO edita_veiculo){
		veiculoRepository.alterarVeiculoEstacionado(edita_veiculo.getModelo(), edita_veiculo.getPlaca(), edita_veiculo.getId());
		return findAllVeivuloEstacionado();
	}

	public void calcularValor_Tempo(List<VeiculoEstacionado_DTO> veiculos) {
		Valor valores = valorRepository.getValores().orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
		
		
	}

	public int calcularHoras(LocalDate data_entro, LocalDate data_saiu, LocalTime hora_entro, LocalTime hora_saiu) {

		LocalTime meiaNoite = LocalTime.parse("00:00");

		int totalDias = 0, totalHoras = 0, h = 0, d = 0;
		long Horas, Dias;

		Dias = ChronoUnit.DAYS.between(data_entro, data_saiu);
		d = (int) Dias; // QUANTIDADE DE DIAS ENTRA DATA DE ENTRADA E DE SAIDA
		totalDias += negativoToPositivo(d);

		if (totalDias == 0) { // ENTRO E SAIU NO MESMO DIA
			Horas = ChronoUnit.HOURS.between(hora_entro, hora_saiu);
			h = (int) Horas;
			totalHoras += negativoToPositivo(h);

		} else if (totalDias == 1) { // SAIU 1 DIA DEPOIS
			Horas = ChronoUnit.HOURS.between(hora_entro, meiaNoite);
			h = (int) Horas; // HORAS DO DIA QUE ENTRO
			totalHoras += negativoToPositivo(h);

			Horas = ChronoUnit.HOURS.between(meiaNoite, hora_saiu);
			h = (int) Horas; // HORAS DO DIA QUE SAIU
			totalHoras += negativoToPositivo(h);

		} else if (totalDias == 2) {
			Horas = ChronoUnit.HOURS.between(hora_entro, meiaNoite);
			h = (int) Horas;
			totalHoras += negativoToPositivo(h);

			totalHoras += 24;

			Horas = ChronoUnit.HOURS.between(meiaNoite, hora_saiu);
			h = (int) Horas;
			totalHoras += negativoToPositivo(h);

		} else {
			totalDias -= 1;

			Horas = ChronoUnit.HOURS.between(hora_entro, meiaNoite);
			h = (int) Horas;
			totalHoras += negativoToPositivo(h); // TOTA DE HORAS DO DIA QUE ENTRO

			Horas = ChronoUnit.HOURS.between(meiaNoite, hora_saiu);
			h = (int) Horas;
			totalHoras += negativoToPositivo(h); // TOTA DE HORAS DO DIA QUE SAIU

			totalHoras += totalDias * 24; // HORAS DOS RESTANTES DE DIAS QUE FICO
		}
		return totalHoras;
	}

	public static int negativoToPositivo(int n) {
		String regex = "" + n;
		regex = regex.replaceAll("[^0-9]", "");
		return Integer.parseInt(regex);
	}
	
	public LocalTime getHoraAtual() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		LocalTime horaAtual = LocalTime.now();
		String horaAtualForrmat = formatter.format(horaAtual);
		LocalTime localTime = LocalTime.parse(horaAtualForrmat);

		return localTime;
	}

	public LocalDate getDataAtual() {

		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		LocalDate dataAtual = LocalDate.now();
		String DataAtualForrmat = formatter.format(dataAtual);

		DateTimeFormatter formatte = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate localDate = LocalDate.parse(DataAtualForrmat, formatte);

		return localDate;
	}
}
