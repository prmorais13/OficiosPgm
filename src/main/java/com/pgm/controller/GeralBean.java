package com.pgm.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.pgm.util.report.Connect;

@Named
@SessionScoped
public class GeralBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String saida;
	
	//Retorna o caminhos completo de um arquivo ou pasta da aplicação
	private String getDiretorioReal(String diretorio){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getServletContext().getRealPath(diretorio);

	}
	
	//Retorna o nome da aplicação
	private String getContextPath(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getServletContext().getContextPath();
	}
	
	//Gera o arquivo PDF
	private void preenchePDF(JasperPrint print) throws JRException{
		
		//Pega o caminho completo do PDF desde a raiz
		saida = getDiretorioReal("/pdf/OficiosGerados.pdf");
		System.out.println("Saída preencher 1 " + saida);
		
		//Exporta para pdf
		JasperExportManager.exportReportToPdfFile(print, saida);
		
		saida = getContextPath() + "/pdf/OficiosGerados.pdf";
		System.out.println("Saída preencher 2 " + saida);
	}
	
	public String geraRelatorio(){
		saida = null;
		Connection conexao = null;
		String jasper = getDiretorioReal("/jasper/rel_oficios_enviados.jasper");
		
		try {
			conexao = Connect.getConexao();
			
			JasperPrint print = JasperFillManager.fillReport(jasper, null, conexao);
			
			System.out.println("Paginas " + print.getPages().size());
			
			preenchePDF(print);
			
			System.out.println("Saída preencher 3 " + saida);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(conexao != null){
					conexao.close();
				}
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		}
		
		return "exibePdf?faces-redirect=true";
	}

	public String getSaida() {
		return saida;
	}
	
	public void setSaida(String saida) {
		this.saida = saida;
	}

}
