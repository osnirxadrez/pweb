package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Aluno;

@FacesConverter("Aluno")
public class AlunoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext facescontext, UIComponent uiComponent, String value) {
		if(value!=null && !value.isEmpty()) {
			return (Aluno) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facescontext, UIComponent uiComponent, Object value) {
	if(value instanceof Aluno) {	
		Aluno a=(Aluno) value;
		if(a !=null && a instanceof Aluno && a.getId() != null) {
			uiComponent.getAttributes().put(a.getId().toString(), a);
			return a.getId().toString();
		}
	}
	return "";
		/*
		Aluno a =(Aluno)value;
		return a.toString();
	*/
	}
    
}
