package com.pgm.controller;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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

import com.pgm.security.Seguranca;
import com.pgm.util.message.FacesMessages;
import com.pgm.util.report.Connect;

@Named
@SessionScoped
public class RelatoriosBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesMessages messages;
	
	@Inject
	private Seguranca seguranca;
	
	private boolean relatorioGerado;
	
	public void exportarPdf(String relatorio) throws JRException, IOException, SQLException{
		
		String verificaRelatorio = null;
		Connection con = Connect.getConexao();
		String logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/imagens/logo.png");		
		
		Map<String, Object> param = new HashMap<>();
		param.put("nome_usuario", this.seguranca.getNomeUsuario());
		param.put("logomarca", logo);
		
		if(relatorio.equals("gerados")){
			
			verificaRelatorio = "rel_oficios_gerados";
		
		}else if(relatorio.equals("cadastrados")){
			
			verificaRelatorio = "rel_oficios_cadastrados";
		
		}else if(relatorio.equals("enviados")){
			
			verificaRelatorio = "rel_oficios_enviados";
		
		}else{
			
			verificaRelatorio = "rel_oficios_recebidos";
		}
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().
				getRealPath("/jasper/" + verificaRelatorio + ".jasper"));
		
		JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), param, con);
		
		this.relatorioGerado = print.getPages().size() > 0;
		
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
	
	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}

	/*private StreamedContent content;

	@Inject
	private RelatorioUtil relUtil;
	
	@Inject
	private FacesMessages messages;
	
	public void emitir(String relatorio) {	
		//Map<String, Object> parametros = new HashMap<>();
		
		String verificaRelatorio = null;
		
		if(relatorio.equals("gerados")){
			verificaRelatorio = "rel_oficios_gerados";
		}else if(relatorio.equals("cadastrados")){
			verificaRelatorio = "rel_oficios_cadastrados";
		}else if(relatorio.equals("enviados")){
			verificaRelatorio = "rel_oficios_enviados";
		}else{
			verificaRelatorio = "rel_oficios_recebidos";
		}

		String arqJasper = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("/WEB-INF/relatorios/" + verificaRelatorio + ".jasper");

		byte[] bytes = relUtil.criarRelatorio(arqJasper, null);

		//if (bytes != null && bytes.length > 0) {
			
			if(relUtil.isRelatorioGerado()){
					
				content = new DefaultStreamedContent(
						new ByteArrayInputStream(bytes), "application/pdf");
				
				RequestContext.getCurrentInstance().execute("PF('relatorio').show()");
				
			}else{
				
				messages.error("Não existem dados para gerar o relatório!");
			}
		//}
		
	}

	public StreamedContent getContent() {
		return content;
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}*/
}
