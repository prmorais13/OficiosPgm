package com.pgm.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pgm.model.Sequencial;

public class Sequenciais implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void atualizar(Sequencial sequencial){
		
		if(sequencial.getId() != null){
			manager.merge(sequencial);
		}
	}
	
	public Sequencial numOficio(){
		return this.manager.createQuery("FROM Sequencial", Sequencial.class).getSingleResult(); 
	}
}
