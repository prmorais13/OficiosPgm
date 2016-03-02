package com.pgm.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pgm.util.report.RelatorioUtil;

@WebServlet(urlPatterns = "/RelServlet")
public class RelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
						
		String dataInicio = request.getParameter("dataInicio");
		String dataFinal = request.getParameter("dataFinal");
		
		System.out.println(dataInicio);
		System.out.println(dataFinal);
			
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			Date dataIni = formatador.parse(dataInicio);
			Date dataFim = formatador.parse(dataFinal);
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("data_inicio", dataIni);
			parametros.put("data_final", dataFim);
			
			String arqJasper = getServletContext().getRealPath(
					"/WEB-INF/relatorios/relatorio_oficios.jasper");
			
			byte[] bytes = RelatorioUtil.criarRelatorio(arqJasper, parametros);
			
			if (bytes != null && bytes.length > 0) {
				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
