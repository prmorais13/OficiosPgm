package com.pgm.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pgm.model.Grupo;

public class Grupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Grupo salvar(Grupo grupo){
		return this.manager.merge(grupo);
	}
	
	public void excluir(Grupo grupo){
		grupo = this.porId(grupo.getId());
		this.manager.remove(grupo);
	}
	
	public Grupo porId(Long id) {
		return this.manager.find(Grupo.class, id);
	}

	public List<Grupo> todos(){
		return this.manager.createQuery("FROM Grupo", Grupo.class).getResultList();
	}
	
}
