package com.pgm.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.pgm.util.report.ExecutorRelatorio;

@WebServlet(urlPatterns = "/relatorioPorData")
public class ServletRelatorioPorData extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		Date dataInicial = (Date) session.getAttribute("dataInicio");
		Date dataFinal = (Date) session.getAttribute("dataFinal");
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", dataInicial);
		parametros.put("data_final", dataFinal);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_oficios.jasper",
				response, parametros, "Oficios por data.pdf");
		
		Session sessao = manager.unwrap(Session.class);
		sessao.doWork(executor);
		
		/*if(executor.isRelatorioGerado()){
			FacesContext.getCurrentInstance().responseComplete();

		}else{
			this.messages.error("A execução do relatório não retornou dados!");	
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage("Não há dados para as datas selecionadas!");
			ctx.addMessage(null, msg);
		}*/
		
		/*String arqJasper = getServletContext().getRealPath(
				"/WEB-INF/relatorios/relatorio_oficios.jasper");
		
		byte[] bytes = RelatorioUtil.criarRelatorio(arqJasper, parametros);
		
		if (bytes != null && bytes.length > 0) {
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		}*/
						
	}

}
