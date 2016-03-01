package com.pgm.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.pgm.util.message.FacesMessages;


@Named
@SessionScoped
public class DocPdfBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UploadedFile docPdf;
	private StreamedContent file;
	private Long id;
	
	@Inject
	private EntityManager manager;
	
	@Inject
	private FacesMessages messages;
	
	//Faz o upload do arquivo pdf
	public void capturarPdf(FileUploadEvent event) {

		this.docPdf = event.getFile();

		this.messages.info("O Arquivo " + this.docPdf.getFileName() + " carregado com sucesso");
	}
	
	//Exibe o arquivo pdf numa janela
	public void exibirPdf(){
		
		try {		
			Query consulta = manager.createQuery("SELECT docPdf FROM Oficio WHERE id = :id");		
			byte[] resultado = (byte[]) consulta.setParameter("id", this.id).getSingleResult();
			file = new DefaultStreamedContent(new ByteArrayInputStream(resultado), "application/pdf");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Mostra o PDF
	public void mostraPdf(){
		
		try {			
			Query consulta = manager.createQuery("SELECT docPdf FROM Oficio WHERE id = :id");		
			byte[] resultado = (byte[]) consulta.setParameter("id", this.id).getSingleResult();
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.getOutputStream().write(resultado);
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] lerPdf(Long id){
		Query consulta = manager.createQuery("SELECT docPdf FROM Oficio WHERE id = :id");
		return (byte[]) consulta.setParameter("id", id).getSingleResult();
		/*System.out.println("OFICIO NÚMERO: " + id);
		return manager.find(Oficio.class, id).getDocPdf();*/
	}

	public void limparAnexo() {
		this.docPdf = null;
	}

	public boolean isCapturadoPdf() {
		return getDocPdf() != null;
	}

	public UploadedFile getDocPdf() {
		return docPdf;
	}

	public void setDocPdf(UploadedFile docPdf) {
		this.docPdf = docPdf;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
