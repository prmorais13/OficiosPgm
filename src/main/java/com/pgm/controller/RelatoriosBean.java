package com.pgm.controller;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.pgm.util.message.FacesMessages;
import com.pgm.util.report.RelatorioUtil;

@Named
@SessionScoped
public class RelatoriosBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	private StreamedContent content;

	@Inject
	private RelatorioUtil relUtil;
	
	@Inject
	private FacesMessages messages;
	
	public void emitir(String relatorio) {	
		//Map<String, Object> parametros = new HashMap<>();
		
		String verificaRelatorio = null;
		
		if(relatorio.equals("gerados")){
			verificaRelatorio = "rel_oficios_gerados";
		}else{
			verificaRelatorio = "rel_oficios_cadastrados";
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
	}
}
