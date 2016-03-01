package com.pgm.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.pgm.model.Usuario;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario gravar(Usuario usuario){
		return this.manager.merge(usuario);
	}

	public void excluir(Usuario usuario){
		usuario = porId(usuario.getId());
		this.manager.remove(usuario);
	}
	
	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}
	
	public List<Usuario> todos(){
		return this.manager.createQuery("FROM Usuario", Usuario.class).getResultList();
	}

	public Usuario porLogin(String login) {
		Usuario usuario = null;
		
		try {
	
			usuario = manager.createQuery("FROM Usuario WHERE lower(login) = :login", Usuario.class)
					.setParameter("login", login.toLowerCase()).getSingleResult();
			
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return usuario;
	}
	
	

}
