package com.pgm.controller;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.primefaces.model.StreamedContent;

import com.pgm.model.Oficio;
import com.pgm.repository.Oficios;
import com.pgm.security.Seguranca;
import com.pgm.util.message.FacesMessages;
import com.pgm.util.report.Connect;
import com.pgm.util.report.RelatorioUtil;

@Named
@SessionScoped
public class RelGeradosBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	private StreamedContent content;

	@Inject
	private RelatorioUtil relUtil;
	
	@Inject
	private Oficios oficios;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private Seguranca seguranca;
	
	private boolean relatorioGerado;
	
	private List<Oficio> listaOficiosGerados;
	
	public void exportarPdf() throws JRException, IOException, SQLException{
		Connection con = Connect.getConexao();
		Map<String, Object> param = new HashMap<>();
		param.put("nome_usuario", this.seguranca.getNomeUsuario());
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/jasper/rel_oficios_gerados_novo.jasper"));
		JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), param, con);
		
		this.relatorioGerado = print.getPages().size() > 0;
		
		System.out.println("Valor " + this.relatorioGerado);
		
		if(this.relatorioGerado){
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.addHeader("Content-disposition", "inline; filename=ListaUsuarios.pdf");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(print, stream);
			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();
		
		}else{
			messages.info("Não há dados para gerar o relatório!");
		}

		con.close();
	}
	
	/*public void emitir() {	
		//Map<String, Object> parametros = new HashMap<>();

		String arqJasper = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("/jasper/rel_oficios_gerados_novo.jasper");

		byte[] bytes = relUtil.criarRelatorio(arqJasper, null);

		//if (bytes != null && bytes.length > 0) {
			
			if(relUtil.isRelatorioGerado()){
					
				content = new DefaultStreamedContent(
						new ByteArrayInputStream(bytes), "application/pdf");
				
				RequestContext.getCurrentInstance().execute("PF('relGerados').show()");
				
				
				
			}else{
				
				messages.error("Não existem dados para gerar o relatório!");
			}
		//}
		
	}*/
	
	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}
	
	public List<Oficio> getListaOficiosGerados() {
		if(this.oficios.gerados() != null){
			this.listaOficiosGerados = this.oficios.gerados();
		}
		
		return listaOficiosGerados;
	}

	public StreamedContent getContent() {
		return content;
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}
}
