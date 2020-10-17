package br.com.testePratico.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.Exception.RegistroNaoEncontradoException;
import br.com.testePratico.model.Usuario;
import br.com.testePratico.model.Valor;
import br.com.testePratico.repository.UsuarioRepository;
import br.com.testePratico.repository.ValorRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ValorRepository valorRepository;
	
	
	public boolean logar(String login, String senha) {
		Usuario usuario = usuarioRepository.findById((long)1).orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado"));
		
		boolean ok = false;
		
		if(usuario.getUsuario().equals(login) && usuario.getSenha().equals(senha)) {
			ok = true;
		}
		
		return ok;
	}
	
	public void inserirDados() {
		Usuario usuario = new Usuario("admin", "admin", "123456");
				
		Valor v = new Valor(6.00, 4.00);
		
		Optional<Valor> va= valorRepository.findById((long) 1);
		Optional<Usuario> u= usuarioRepository.findById((long) 1);
		
		if(u.isEmpty()) {
			usuarioRepository.save(usuario);
		}
		
		if(va.isEmpty()) {
			valorRepository.save(v);
		}
	}
}
