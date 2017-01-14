package com.pgm.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pgm.model.Oficio;
import com.pgm.model.Sequencial;
import com.pgm.security.Seguranca;

public class Oficios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Inject
	private Seguranca seguranca;
	
	@Inject
	private Sequenciais sequenciais;
	
	private int prazoResposta;
	
	public int getPrazoResposta() {
		return prazoResposta;
	}
	
	public void setPrazoResposta(int prazoResposta) {
		this.prazoResposta = prazoResposta;
	}
	
	//Métodos dos ofícios gerados
	public Oficio salvarGerado(Oficio oficio){
		if(oficio.getId() == null){
			//seta a data de criação com a data atual do sistema
			oficio.setDataCriacao(new Date());
			
			//instancia um sequencial
			Sequencial s = new Sequencial();
			
			//atribui ao sequencial o resultado da pesquisa 
			s = this.sequenciais.numOficio();
			
			SimpleDateFormat ano = new SimpleDateFormat("yyyy");
			
			if(ano.format(s.getAnoOficio()).equals(ano.format(new Date()))){
				
				int numero = s.getSequencial() + 1;

				oficio.setNumOficio(numero);
				
				s.setSequencial(numero);
				
			}else{
				
				oficio.setNumOficio(1);
				s.setSequencial(1);
				s.setAnoOficio(new Date());	
			}
			
			oficio.setStatus("GERADO");
			
			//atualiza a tabela Sequencial 
			this.sequenciais.atualizar(s);
		}
		
		return manager.merge(oficio);	
	}
	
	public void salvarTeste(Oficio oficio){
		
		Sequencial s = new Sequencial();
		s = this.sequenciais.numOficio();
		
		SimpleDateFormat ano = new SimpleDateFormat("yyyy");
		
		System.out.println("Ano tabela sequencial: " + ano.format(s.getAnoOficio()));
		
		System.out.println("Ano atual: " + ano.format(new Date()));
		
		if(ano.format(s.getAnoOficio()).equals(ano.format(new Date()))){
			
			int numero = s.getSequencial() + 1;
			
			System.out.println("Número é igual a: " + numero);
			//oficio.setNumOficio(numero);
			
			s.setSequencial(numero);
			
			//this.sequenciais.atualizar(s);
		
		}else{
			
			System.out.println("sequencial antes: " + s.getSequencial());
			
			s.setSequencial(0);
			
			System.out.println("sequencial depois: " + s.getSequencial());
		}
		
		this.sequenciais.atualizar(s);
		
	}
	
	public List<Oficio> todosGerados(){
		return manager.createQuery("FROM Oficio WHERE status = 'GERADO' ORDER BY dataCriacao DESC", 
				Oficio.class).getResultList();
	}
	
	public List<Oficio> gerados(){
		return manager.createQuery("FROM Oficio o INNER JOIN o.destino d INNER JOIN o.procurador p " +
				"WHERE o.status = 'GERADO'", Oficio.class).getResultList();
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
			c.add(Calendar.DATE, + oficio.getPrazo());
			oficio.setDataPrazo(c.getTime());
		
			oficio.setStatus("RECEBIDO");
		}
		
		return manager.merge(oficio);	
	}
	
	public List<Oficio> todosRecebidos(){
		return manager.createQuery("FROM Oficio WHERE status = 'RECEBIDO' ORDER BY dataRecebimento DESC",
				Oficio.class).getResultList();
	}
	
	//Métodos dos ofícios respondidos
		public Oficio salvarRespondido(Oficio oficio){
			
			if(oficio.getDataResposta() == null){
				oficio.setDataResposta(new Date());
				
				/*Calendar c = Calendar.getInstance();
				c.setTime(oficio.getDataRecebimento());
				c.add(Calendar.DATE, + oficio.getPrazo());
				oficio.setDataPrazo(c.getTime());*/
			
				oficio.setStatus("RESPONDIDO");
			}
			
			oficio.setRespCadastroResposta(this.seguranca.getNomeUsuario());
			
			return manager.merge(oficio);
		}
		
		public List<Oficio> todosRespondidos(){
			return manager.createQuery("FROM Oficio WHERE status = 'RESPONDIDO' ORDER BY dataResposta DESC",
					Oficio.class).getResultList();
		}

	public Oficio porId(Long id) {
		return manager.find(Oficio.class, id);
	}
}
