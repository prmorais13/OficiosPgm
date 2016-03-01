package com.pgm.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pgm.controller.DocPdfBean;

@WebServlet(value = "/docpdf")
public class OficioPdfServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DocPdfBean docPdfBean;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Long id = Long.parseLong(req.getParameter("codigo"));
		//String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
		byte[] pdf = this.docPdfBean.lerPdf(id);
		resp.setContentType("application/pdf");
		ServletOutputStream saida = resp.getOutputStream();
		saida.write(pdf);
		saida.close();
		
	}

}
