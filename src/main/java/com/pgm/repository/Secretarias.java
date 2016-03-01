package com.pgm.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pgm.model.Secretaria;

public class Secretarias implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Secretaria gravar(Secretaria secretaria){
		return this.manager.merge(secretaria);	
	}

	public void excluir(Secretaria secretaria){
		secretaria = this.porId(secretaria.getId());
		this.manager.remove(secretaria);
	}

	public Secretaria porId(Long id) {
		return this.manager.find(Secretaria.class, id);
	}
	
	public List<Secretaria> todas(){
		return this.manager.createQuery("FROM Secretaria ORDER BY nome", Secretaria.class).getResultList();
	}
}
