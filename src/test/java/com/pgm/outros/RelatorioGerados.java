package com.pgm.outros;

import java.io.Serializable;
import java.util.Date;

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
public class RelatorioGerados implements Serializable {

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

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/rel_oficios_gerados.jasper",
				this.response, null, "Oficios gerados.pdf");
		
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
