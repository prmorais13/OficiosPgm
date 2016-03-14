package com.pgm.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.digest.DigestUtils;

import com.pgm.model.Grupo;
import com.pgm.model.Usuario;
import com.pgm.repository.Usuarios;
import com.pgm.util.jpa.Transacional;
import com.pgm.util.message.FacesMessages;

/**
 * 
 * @author Paulo e edilvolima
 *
 */
@Named
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioEdicao;
	private List<Usuario> todosUsuarios;
	
	private Grupo grupo;
	
	@Inject
	private Usuarios usuarios;
	
	@Inject
	private FacesMessages messages;
	
	@PostConstruct
	private void iniciar(){
		this.usuarioEdicao = new Usuario();
	}
	
	@Transacional
	public void gravar(){	
		//Griptografa senha com hash MD5
		//this.usuarioEdicao.setSenha(DigestUtils.md5Hex(this.usuarioEdicao.getSenha()));
		
		if(this.usuarioEdicao.getGrupos().size() == 0){
			this.messages.error("Adicione o usuário a pelo menos um grupo!");
		}else{	
			this.usuarios.gravar(this.usuarioEdicao);
			this.usuarioEdicao = new Usuario();
			this.consultar();
			
			this.messages.info("Usuário salvo com sucesso!");
		}
	}
	
	@Transacional
	public void excluir(Usuario usuario){
		if(usuario.getLogin().equals("admin")){
			this.messages.info("O Administrador não pode ser excluído!");
		}else{
			this.usuarios.excluir(usuario);
			this.consultar();
			
			this.messages.info("Usuário excluído com sucesso!");
		}
	}

	public void consultar() {
		this.todosUsuarios = this.usuarios.todos();		
	}
	
	public void adicionarGrupo(){
		this.usuarioEdicao.getGrupos().add(this.grupo);
		
	}
	
	public void novoGrupo(){
		this.grupo = new Grupo();
	}
	
	public void removerGrupo(){
		this.usuarioEdicao.getGrupos().remove(this.grupo);
	}
	
	public String prepararEdicao(){
		return "gestaoUsuarios?faces-redirect=true";
	}
	
	public void cancelar(){
		this.usuarioEdicao = new Usuario();
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarioEdicao;
	}

	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}

	public Grupo getGrupo() {
		return grupo;
	}
	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}
