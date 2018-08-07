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

import model.Contato;
import util.HibernateUtil;

@ManagedBean
@ViewScoped
public class ContatoController implements Serializable {
   
	private static final long serialVersionUID = 1L;
	
	private Contato contato;
	private List<Contato>contatos;
	
	
	@PostConstruct
	public void inicializa() {
		contato = new Contato();
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
			sessao.merge(contato);
			t.commit();
			listarContatos();
			contato = new Contato();
			addMessage("Cadastro", "Contato cadastrado com sucesso");
		}catch(Exception e) {
			if(t!=null) {
				t.rollback();
			}
			throw(e);
		}finally {
			sessao.close();
		}
	}
	
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
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
