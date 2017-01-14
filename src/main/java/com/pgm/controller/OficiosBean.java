package com.pgm.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.pgm.model.Oficio;
import com.pgm.repository.Oficios;
import com.pgm.util.jpa.Transacional;
import com.pgm.util.message.FacesMessages;

@Named
@SessionScoped
public class OficiosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Oficios oficios;

	@Inject
	private FacesMessages messages;
	
	@Inject
	private DocPdfBean docPdfBean;
	
	@Inject
	private Oficio oficioEdicao;

	private List<Oficio> gerados;
	private List<Oficio> cadastrados;
	private List<Oficio> enviados;
	private List<Oficio> recebidos;
	private List<Oficio> respondidos;
	
	
	@PostConstruct
	public void iniciar(){
		//this.oficioEdicao = new Oficio();
	}
	
	@Transacional
	public void salvarTeste(){
		this.oficios.salvarTeste(this.oficioEdicao);
	}

	//Métodos dos ofícios gerados
	@Transacional
	public void salvar() {
		if(this.oficioEdicao.getId() != null && !this.oficioEdicao.getStatus().equals("GERADO")){
			this.messages.info("ATENÇÃO: O Ofício está com o status de " + this.oficioEdicao.getStatus() +
					" e não pode ser editado nesta tela!");
			this.cancelar();
		}else{
			this.oficios.salvarGerado(this.oficioEdicao);
			this.oficioEdicao = new Oficio();
			this.consultarGerados();
			this.messages.info("Ofício gerado com sucesso!");
		}
	}
	
	public void consultarGerados() {
		this.gerados = this.oficios.todosGerados();

	}

	public String preparaCadastro(Oficio oficio){
		this.oficioEdicao = oficio;
		return "OficiosCadastrados?faces-redirect=true";
	}
	
	//Métodos dos ofícios cadastrados
	@Transacional
	public void salvarCadastro() {
		if(this.oficioEdicao.getDataCadastro() != null && !this.oficioEdicao.getStatus().equals("CADASTRADO")){
			this.messages.info("ATENÇÃO: O Ofício está com o status de " + this.oficioEdicao.getStatus() +
				" e não pode ser editado nesta tela!");
			this.cancelar();
		}else{
			this.oficios.salvarCadastro(this.oficioEdicao);
			this.oficioEdicao = new Oficio();
			this.consultarCadastrados();
		
			this.messages.info("Ofício cadastrado com sucesso!");
		}
	}
		
	public void consultarCadastrados() {
		this.cadastrados = this.oficios.todosCadastrados();

	}
	
	public String preparaEnvio(Oficio oficio){
		this.oficioEdicao = oficio;
		return "OficiosEnviados?faces-redirect=true";
	}
	
	//Métodos dos ofícios envidados
	@Transacional
	public void salvarEnvio() {
		if(this.oficioEdicao.getDataEnvio() != null && !this.oficioEdicao.getStatus().equals("ENVIADO")){
			this.messages.info("ATENÇÃO: O Ofício está com o status de " + this.oficioEdicao.getStatus() +
				" e não pode ser editado nesta tela!");
			this.cancelar();
		}else{
			this.oficios.salvarEnviado(this.oficioEdicao);
			this.oficioEdicao = new Oficio();
			this.consultarEnviados();
	
			this.messages.info("Ofício enviado com sucesso!");
		}
	}
		
	public void consultarEnviados() {
		this.enviados = this.oficios.todosEnviados();

	}
	
	public String preparaRecebimento(Oficio oficio){
		this.oficioEdicao = oficio;
		return "OficiosRecebidos?faces-redirect=true";
	}
	
	//Métodos dos ofícios recebidos
	@Transacional
	public void salvarRecebido() {
		if(this.oficioEdicao.getDataRecebimento() != null && !this.oficioEdicao.getStatus().equals("RECEBIDO")){
			this.messages.info("ATENÇÃO: O Ofício está com o status de " + this.oficioEdicao.getStatus() +
				" e não pode ser editado nesta tela!");
			this.cancelar();
		}
		
		if(!this.docPdfBean.isCapturadoPdf()){
			this.messages.error("Cópia do Ofício enviado deve ser anexada!");
		}else{
			this.oficioEdicao.setDocPdf(this.docPdfBean.getDocPdf().getContents());	
			this.oficios.salvarRecebido(this.oficioEdicao);
			this.oficioEdicao = new Oficio();
			this.consultarRecebidos();
			
			this.messages.info("Ofício recebido com sucesso!");
		}
		
		this.docPdfBean.limparAnexo();
	}
		
	public void consultarRecebidos() {
		this.recebidos = this.oficios.todosRecebidos();

	}
	
	public String preparaResposta(Oficio oficio){
		this.oficioEdicao = oficio;
		return "OficiosRespondidos?faces-redirect=true";
	}
	
	//Métodos dos ofícios respondidos
	@Transacional
	public void salvarRespondido() {
		if(this.oficioEdicao.getDataResposta() != null && !this.oficioEdicao.getStatus().equals("RESPONDIDO")){
			this.messages.info("ATENÇÃO: O Ofício está com o status de " + this.oficioEdicao.getStatus() +
				" e não pode ser editado nesta tela!");
			this.cancelar();
		}
		
		if(!this.docPdfBean.isCapturadoPdf()){
			this.messages.error("Cópia da resposta do ofício deve ser anexada!");
		}else{
			this.oficioEdicao.setOficioResposta(this.docPdfBean.getDocPdf().getContents());	
			this.oficios.salvarRespondido(this.oficioEdicao);
			this.oficioEdicao = new Oficio();
			this.consultarRespondidos();
				
			this.messages.info("Resposta de ofício cadastrada com sucesso!");
		}
			
		this.docPdfBean.limparAnexo();
	}
			
	public void consultarRespondidos() {
		this.respondidos = this.oficios.todosRespondidos();

	}
		
	public void cancelar(){
		this.oficioEdicao = new Oficio();
		this.docPdfBean.limparAnexo();
	}

	

	public Oficio getOficioEdicao() {
		return oficioEdicao;
	}

	public void setOficioEdicao(Oficio oficioEdicao) {
		this.oficioEdicao = oficioEdicao;
	}

	public List<Oficio> getGerados() {
		return gerados;
	}

	public List<Oficio> getCadastrados() {
		return cadastrados;
	}

	public List<Oficio> getEnviados() {
		return enviados;
	}

	public List<Oficio> getRecebidos() {
		return recebidos;
	}
	
	public List<Oficio> getRespondidos() {
		return respondidos;
	}
	
	public boolean isEditavel(){
		return this.oficioEdicao.getId() == null;
	}
	
}
