package com.pgm.outros;
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
public class RelGeradosBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	private StreamedContent content;

	@Inject
	private RelatorioUtil relUtil;
	
	@Inject
	private FacesMessages messages;
	
	public void emitir() {	
		//Map<String, Object> parametros = new HashMap<>();

		String arqJasper = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("/WEB-INF/relatorios/rel_oficios_gerados.jasper");

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
		
	}

	public StreamedContent getContent() {
		return content;
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}
}
