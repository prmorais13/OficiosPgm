package com.pgm.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.pgm.model.Usuario;
import com.pgm.repository.Usuarios;
import com.pgm.security.Seguranca;

@Named
@RequestScoped
public class RelUsuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	@Inject
	private Seguranca seguranca;
	
	private List<Usuario> listaUsuarios;
	
	public void exportarPdf() throws JRException, IOException{
		Map<String, Object> param = new HashMap<>();
		param.put("nome_usuario", this.seguranca.getNomeUsuario() );
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/jasper/teste.jasper"));
		JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), param, new JRBeanCollectionDataSource(getListaUsuarios()));
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition", "inline; filename=ListaUsuarios.pdf");
		ServletOutputStream stream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(print, stream);
		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}
	

	public List<Usuario> getListaUsuarios() {
		if(this.usuarios.relUsuario() != null){
			this.listaUsuarios = this.usuarios.relUsuario();
		}
		
		return listaUsuarios;
	}

}