package com.pgm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pgm.model.Procurador;
import com.pgm.repository.Procuradores;
import com.pgm.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Procurador.class)
public class ProcuradorConverter implements Converter<Object>{
	
	//@Inject
	private Procuradores procuradores;
	
	public ProcuradorConverter(){
		procuradores = CDIServiceLocator.getBean(Procuradores.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Procurador retorno = null;
		
		if(value != null){
			Long id = new Long(value);
			retorno = procuradores.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			return ((Procurador)value).getId().toString();
		}
		return "";
	}



}
