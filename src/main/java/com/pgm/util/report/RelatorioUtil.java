package com.pgm.util.report;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;

import javax.inject.Inject;

import com.pgm.util.message.FacesMessages;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	FacesMessages messages;
	
	private boolean relatorioGerado;
	
	public byte[] criarRelatorio(String arqJasper, Map<String, Object> parametros){
		byte[] bytes = null;
		
		try {
			Connection con = Connect.getConexao();
			
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(arqJasper);			
			
			JasperPrint print = JasperFillManager.fillReport(relatorioJasper, parametros, con);
			
			this.relatorioGerado = print.getPages().size() > 0;
						
			bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros,con);
			
			System.out.println("Bytes " + bytes.length);
			
		} catch (JRException  e) {
			e.printStackTrace();
		}
		
		return bytes;
	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}
}
