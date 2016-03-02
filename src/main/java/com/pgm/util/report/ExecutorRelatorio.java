package com.pgm.util.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.hibernate.jdbc.Work;

public class ExecutorRelatorio implements Work {

	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String , Object> parametros;
	//private String nomeArquivoSaida;
	
	private boolean relatorioGerado;
	
	
	public ExecutorRelatorio(String caminhoRelatorio,
			HttpServletResponse response, Map<String, Object> parametros,
			String nomeArquivoSaida) {
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		//this.nomeArquivoSaida = nomeArquivoSaida;
	}


	@Override
	public void execute(Connection connection) throws SQLException {
		
		try {
			InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);
			
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
			this.relatorioGerado = print.getPages().size() > 0;
			
			if(this.relatorioGerado){
				JRExporter exportador = new JRPdfExporter();
				exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);
			
				response.setContentType("application/pdf");
				
				exportador.exportReport();
			}
			
		} catch (Exception e) {
			throw new SQLException("Erro ao excutar o relatório " + this.caminhoRelatorio, e);
		}
			
	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}

}
