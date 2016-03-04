package com.pgm.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@Named
@RequestScoped
public class RelatorioPorDataBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;
	
	public void emitir(){
		
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
			
			session.setAttribute("dataInicio", this.dataInicio);
			session.setAttribute("dataFinal", this.dataFim);
			
			//redirect("/relatorioPorData");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	protected void redirect(String page) {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext ec = ctx.getExternalContext();
			
		try {
			ec.redirect(ec.getRequestContextPath() + page);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
		
/*		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_final", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_oficios.jasper",
				this.response, parametros, "Oficios por data.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if(executor.isRelatorioGerado()){
			this.facesContext.responseComplete();

		}else{
			this.messages.error("A execução do relatório não retornou dados!");	
		}
				
	}*/
	
	@NotNull(message = "Entre com a data inicial!")
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull(message = "Entre com a data final!")
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
