package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Disciplina;
import util.HibernateUtil;

@ManagedBean
@ViewScoped
public class DisciplinaController implements Serializable {

	
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	private static final long serialVersionUID = 1L;

	Disciplina disciplina;
	
	List<Disciplina> disciplinas;
	
	@PostConstruct
	public void instanciaDisciplina() {
		disciplina = new Disciplina();
	}
	
	public void edita(ActionEvent evt){
		disciplina = (Disciplina)evt.getComponent().getAttributes().get("disciplinaEdita");
	}
	public void excluir(ActionEvent evt){
		disciplina = (Disciplina)evt.getComponent().getAttributes().get("disciplinaExcluir");
		exclui();
	}
	
	@SuppressWarnings("unchecked")
	public void listarTodas() {
		Session sessao=HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Disciplina.class);	
			disciplinas=consulta.list();
		}catch(Exception e) {
			addMessage("ERRO","ERRO");
		}finally {
			sessao.close();
		}
	}
	public void salvar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.merge(disciplina);
			t.commit();
			listarTodas();
			disciplina = new Disciplina();
			addMessage("Cadastro", "Disciplina cadastrada com sucesso");
		}catch(Exception e) {
			if(t!=null) {
				t.rollback();
			}
			throw(e);
		}finally {
			sessao.close();
		}
	}
	public void exclui() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.delete(disciplina);
			t.commit();
			listarTodas();
			disciplina = new Disciplina();
			addMessage("Exclusão", "Disciplina excluída com sucesso");
		}catch(Exception e) {
			if(t!=null) {
				t.rollback();
			}
			throw(e);
		}finally {
			sessao.close();
		}
	}
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public void addMessage(String summary, String detail) {
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		 FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
