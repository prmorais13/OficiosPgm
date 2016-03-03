package com.pgm.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@Named
@SessionScoped
public class RelatorioServletBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date dataInicio;
	private Date dataFim;

	protected void redirect(String page) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext ec = ctx.getExternalContext();
		
		try {
			ec.dispatch(page);
			//ec.redirect(ec.getRequestContextPath() + page);
		} catch (IOException ex) {
			ex.printStackTrace();;
		}
	}

	public void imprimirRelatorio() throws Exception {
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) ctx.getExternalContext()
					.getSession(false);
			
			session.setAttribute("dataInicio", this.dataInicio);
			session.setAttribute("dataFinal", this.dataFim);
					
			redirect("/RelServlet");
			
		} catch (Exception ex) {
			ex.printStackTrace();;
		}
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

}
