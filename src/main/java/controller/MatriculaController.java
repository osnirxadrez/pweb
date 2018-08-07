package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Matricula;

public class MatriculaController implements Serializable{

	private static final long serialVersionUID = 1L;

	private Matricula matricula;
	private List<Matricula> matriculas;
	
	private List<SelectItem> alunos;
	private List<SelectItem> disciplinas;
	
	@PostConstruct
	public void inicializar() {
		matricula = new Matricula();
		
	}
}
