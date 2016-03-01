package com.pgm.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.pgm.model.Oficio;
import com.pgm.repository.Oficios;

@WebServlet(urlPatterns = "/doc-pdf")
public class PdfOficioServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Oficios oficios;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 
		
		Long idOficio = Long.parseLong(req.getParameter("oficio"));
		
		Oficio oficio = this.oficios.porId(idOficio);
		
		//resp.getOutputStream().write(oficio.getDocPdf());
		
		resp.setContentType("application/pdf");
		IOUtils.write(oficio.getDocPdf(), resp.getOutputStream());
	}

}
