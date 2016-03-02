package com.pgm.util.report;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static byte[] criarRelatorio(String arqJasper, Map<String, Object> parametros){
		byte[] bytes = null;
		
		try {
			Connection con = Connect.getConexao();
			
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(arqJasper);
			
			bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros,con);
			
		} catch (JRException  e) {
			e.printStackTrace();
		}
		
		return bytes;
	}
	

}
