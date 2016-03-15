package com.pgm.outros;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.pgm.util.message.FacesMessages;
import com.pgm.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioPorDataBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private EntityManager manager;
	
	@Inject
	private HttpServletResponse response;
		
	public void emitir(){
		
		/*try {
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
	}*/
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_final", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/rel_oficios_por_data.jasper",
				this.response, parametros, "Oficios por data.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if(executor.isRelatorioGerado()){			
			FacesContext.getCurrentInstance().responseComplete();

		}else{
			this.messages.error("A execução do relatório não retornou dados!");	
		}				
	}
	
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
