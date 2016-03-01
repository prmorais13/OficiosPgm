package com.pgm.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pgm.model.Procurador;

public class Procuradores implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Procurador gravar(Procurador procurador){
		return this.manager.merge(procurador);
	}
	
	public Procurador porId(Long id){
		return this.manager.find(Procurador.class, id);
	}
	
	public List<Procurador> todos(){
		return this.manager.createQuery("FROM Procurador ORDER BY nome", Procurador.class).getResultList();
	}
	
	public void excluir(Procurador procurador){
		procurador = this.porId(procurador.getId());
		this.manager.remove(procurador);
	}
	
}
