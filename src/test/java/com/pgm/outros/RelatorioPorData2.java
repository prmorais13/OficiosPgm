package com.pgm.outros;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.pgm.model.Oficio;
import com.pgm.repository.Oficios;
import com.pgm.util.report.RelatorioUtil;

@Named
@RequestScoped
public class RelatorioPorData2 implements Serializable{

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;
	
	@Inject
	private RelatorioUtil relatorioUtil;
	
	@Inject
	private HttpServletResponse response;

	@Inject
	private Oficios oficios;
	
	public void emitir() throws IOException, ServletException{
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_final", this.dataFim);
		
		String arqJasper = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/WEB-INF/relatorios/relatorio_oficios.jasper");
				
		byte[] bytes = relatorioUtil.criarRelatorio(arqJasper, parametros);
		
		if (bytes != null && bytes.length > 0) {
			response.addHeader("Content-disposition", "inline; filename=report.pdf");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			
			ServletOutputStream ouputStream = response.getOutputStream();		
			ouputStream.write(bytes, 0, bytes.length);			
			ouputStream.flush();
			ouputStream.close();
			
			FacesContext.getCurrentInstance().responseComplete();
			
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
	
	public void gerarPDF() throws JRException, IOException {        
        JasperPrint jasperPrint;
        
        List<Oficio> lista = this.oficios.todosCadastrados();
        
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lista);
        
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String caminhoRelatorio = servletContext.getRealPath("/WEB-INF/relatorios/relatorio_oficios.jasper");
        
        Map<String, Object> parametros = new HashMap<>();
/*        parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_final", this.dataFim);*/

        jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, parametros, beanCollectionDataSource);
        response = (HttpServletResponse) context.getExternalContext().getResponse();
       /* HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");*/
        response.addHeader("Content-disposition", "inline; filename=report.pdf");
        ServletOutputStream servletOutputStream = response.getOutputStream();
       /* ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();*/
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        context.responseComplete();
    }

}
