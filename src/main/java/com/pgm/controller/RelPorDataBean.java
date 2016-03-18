package com.pgm.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.pgm.util.message.FacesMessages;
import com.pgm.util.report.RelatorioUtil;

@Named
@SessionScoped
public class RelPorDataBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;
	private StreamedContent content;
	
	@Inject
	private RelatorioUtil relUtil;
	
	@Inject
	private FacesMessages messages;
	
	public void emitir() {	
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_final", this.dataFim);

		String arqJasper = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("/WEB-INF/relatorios/rel_oficios_por_data.jasper");

		byte[] bytes = relUtil.criarRelatorio(arqJasper, parametros);

		if(relUtil.isRelatorioGerado()){

			content = new DefaultStreamedContent(
				new ByteArrayInputStream(bytes), "application/pdf");
				
			RequestContext.getCurrentInstance().execute("PF('relPorData').show()");
			
		}else{

			messages.error("Não existem dados para gerar o relatório!");
		}
		
		this.dataFim = null;
		this.dataInicio = null;
		
	}

	@NotNull(message = "Informe a data inicial!")
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull(message = "Informe a data final!")
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public StreamedContent getContent() {
		return content;
	}

}
