package br.com.testePratico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.testePratico.model.Usuario;
import br.com.testePratico.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/logar")
	public ResponseEntity<Usuario> login(@RequestParam String usuario, @RequestParam String senha){

		usuarioService.inserirDados();

		Usuario usu = new Usuario();
		
		if(!usuarioService.logar(usuario, senha)) {
			usu.setUsuario("Login ou senha incorretos");
			return ResponseEntity.ok(usu);
		}else {
			usu.setUsuario("Acesso altorizado");
			return ResponseEntity.ok(usu);
		}		
	}
}
