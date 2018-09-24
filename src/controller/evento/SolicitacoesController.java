package controller.evento;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.bean.EventoBean;
import model.entity.Evento;

@Named
@RequestScoped
public class SolicitacoesController implements Serializable {

	private static final long serialVersionUID = -6004071469836173158L;
	
	@Inject
	private EventoBean eventoBean;
	
	@Inject
	private EventosController eventosController;
	
	private String assunto;
	private String descricao;
	private String nomeSolicitante;
	private String emailSolicitante;
	
	public String doSolicitar() {
		if (assunto.toLowerCase().contains("anúncio")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Solicitação rejeitada", null)); 
			return null;
		}
		
		Evento novoEvento = new Evento();
		novoEvento.setAssunto(assunto);
		novoEvento.setDescricao(descricao);
		novoEvento.setNomeSolicitante(nomeSolicitante);
		novoEvento.setEmailSolicitante(emailSolicitante);
		novoEvento.setAprovado(false);
		novoEvento.setAvaliado(false);
		eventoBean.persist(novoEvento);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação enviada", null));
		
		this.assunto = null;
		this.descricao = null;
		this.nomeSolicitante = null;
		this.emailSolicitante = null;
		return null;
	}
	
	public String doAprovar(Evento evento) {
		evento.setAprovado(true);
		evento.setAvaliado(true);
		eventoBean.update(evento);
		eventosController.atualizarListas();
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento aprovado!", null));
		return null;
	}
	
	public String doRejeitar(Evento evento) {
		evento.setAprovado(false);
		evento.setAvaliado(true);
		eventoBean.update(evento);
		eventosController.atualizarListas();
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento rejeitado!", null));
		return null;
	}
	
	/**
	 * Retorna um evento ao status de 'solicitacao', ou seja, ainda nao avaliado.
	 */
	public String doDesavaliar(Evento evento) {
		evento.setAvaliado(false);
		eventoBean.update(evento);
		eventosController.atualizarListas();
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aprovação revogada", null));
		return null;
	}
	
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}
	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}
	public String getEmailSolicitante() {
		return emailSolicitante;
	}
	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}
		
}
