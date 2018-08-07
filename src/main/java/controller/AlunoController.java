package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Aluno;
import model.Contato;
import util.HibernateUtil;

@ViewScoped
@ManagedBean
public class AlunoController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Aluno aluno;
	private List<Aluno> alunos;
	private List<Contato> contatos;
	
	@PostConstruct
	public void inicializa() {
		aluno = new Aluno();
	}
	@SuppressWarnings("unchecked")
	public void listarAlunos() {
		Session sessao=HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Aluno.class);	
			alunos=consulta.list();
		}catch(Exception e) {
			addMessage("ERRO","ERRO");
		}finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void listarContatos() {
		Session sessao=HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Contato.class);	
			contatos=consulta.list();
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
			sessao.merge(aluno);
			t.commit();
			listarContatos();
			aluno = new Aluno();
			addMessage("Cadastro", "Aluno cadastrado com sucesso");
		}catch(Exception e) {
			if(t!=null) {
				t.rollback();
			}
			throw(e);
		}finally {
			sessao.close();
		}
	}
	
	
	
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	public List<Contato> getContatos() {
		return contatos;
	}
	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
	
	public void addMessage(String summary, String detail) {
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		 FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
