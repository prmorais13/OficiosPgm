package com.pgm.repository;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pgm.model.Oficio;

public class Oficios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	//Métodos dos ofícios gerados
	public Oficio salvarGerado(Oficio oficio){
		if(oficio.getId() == null){
			oficio.setDataCriacao(new Date());
			
			oficio.setStatus("GERADO");
		}
		return manager.merge(oficio);	
	}
	
	public List<Oficio> todosGerados(){
		return manager.createQuery("FROM Oficio WHERE status = 'GERADO' ORDER BY dataCriacao DESC", 
				Oficio.class).getResultList();
	}
	
	//Métodos dos ofícios cadastrados	
	public Oficio salvarCadastro(Oficio oficio){
		if(oficio.getDataCadastro() == null){
			oficio.setDataCadastro(new Date());
			oficio.setStatus("CADASTRADO");
		}
		return manager.merge(oficio);	
	}
	
	public List<Oficio> todosCadastrados(){
		return manager.createQuery("FROM Oficio WHERE status = 'CADASTRADO' ORDER BY dataCadastro DESC",
				Oficio.class).getResultList();
	}
	
	//Métodos dos ofícios enviados
	public Oficio salvarEnviado(Oficio oficio){
		if(oficio.getDataEnvio() == null){
			oficio.setDataEnvio(new Date());
			oficio.setStatus("ENVIADO");
		}
		return manager.merge(oficio);	
	}
	
	public List<Oficio> todosEnviados(){
		return manager.createQuery("FROM Oficio WHERE status = 'ENVIADO' ORDER BY dataEnvio DESC",
				Oficio.class).getResultList();
	}
	
	
	//Métodos dos ofícios recebidos
	public Oficio salvarRecebido(Oficio oficio){
		
		if(oficio.getDataRecebimento() == null){
			oficio.setDataRecebimento(new Date());
			
			Calendar c = Calendar.getInstance();
			c.setTime(oficio.getDataRecebimento());
			c.add(Calendar.DATE, + 10);
			oficio.setDataPrazo(c.getTime());
		
			oficio.setStatus("RECEBIDO");
		}
		
		return manager.merge(oficio);	
	}
	
	public List<Oficio> todosRecebidos(){
		return manager.createQuery("FROM Oficio WHERE status = 'RECEBIDO' ORDER BY dataRecebimento DESC",
				Oficio.class).getResultList();
	}

	public Oficio porId(Long id) {
		return manager.find(Oficio.class, id);
	}
}