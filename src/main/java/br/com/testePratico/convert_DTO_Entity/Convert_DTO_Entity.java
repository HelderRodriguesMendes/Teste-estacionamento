package br.com.testePratico.convert_DTO_Entity;

import java.util.ArrayList;
import java.util.List;

import br.com.testePratico.model.Movimentacao;
import br.com.testePratico.model.DTO.VeiculoEstacionado_DTO;

public class Convert_DTO_Entity {
	public List<VeiculoEstacionado_DTO> toVeiculoEstacionado_DTO(List<Movimentacao> veiculos){
		
		List<VeiculoEstacionado_DTO> list = new ArrayList<>();
		for(Movimentacao m : veiculos) {
			VeiculoEstacionado_DTO v = new VeiculoEstacionado_DTO();
			v.setId(m.getId());
			v.setModelo(m.getModelo());
			v.setPlaca(m.getPlaca());
			v.setTempo(m.getTempo());
			
			list.add(v);
		}
		return list;
	}
}
