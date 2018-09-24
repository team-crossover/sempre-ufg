package model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Evento implements Serializable {

	private static final long serialVersionUID = 5192754964931404639L;

	@Id
	@GeneratedValue
	private int id;

	private String assunto = "";

	@Column(length = 500)
	private String descricao = "";

	private String nomeSolicitante = "";

	private String emailSolicitante = "";
	
	private boolean avaliado = false;
	
	private boolean aprovado = false;

	public Evento() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}
	
	public boolean isAprovado() {
		return this.aprovado;
	}

	public boolean isAvaliado() {
		return avaliado;
	}

	public void setAvaliado(boolean avaliado) {
		this.avaliado = avaliado;
	}

}
