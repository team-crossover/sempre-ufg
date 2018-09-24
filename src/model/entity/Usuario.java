package model.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;

/**
 * Entidade que representa um Usuário do sistema.
 */
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 6971039676227754673L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique = true, nullable = false)
	private String email;

	@Column(name = "senha", nullable = false)
	private String encodedSenha;

	@Column(length = 11)
	private String cpf = "";

	private String nome = "";
	
	@Column(length = 1)
	private String genero = "";

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	private boolean recebeDivulgacao = true;

	public Usuario() {
	}

	public Usuario(String email) {
		setEmail(email);
		//Requisitos definem que novo usuario tem senha '0'
		setDecodedSenha("0");
	}

	/**
	 * Converte uma senha original (em UTF-8) para a hash usada internamente.
	 */
	public static String encodeSenha(String decodedSenha) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(decodedSenha.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return DatatypeConverter.printBase64Binary(digest).toString();
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
			return null;
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncodedSenha() {
		return encodedSenha;
	}

	public void setEncodedSenha(String encodedSenha) {
		this.encodedSenha = encodedSenha;
	}

	public void setDecodedSenha(String decodedSenha) {
		this.encodedSenha = encodeSenha(decodedSenha);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isRecebeDivulgacao() {
		return recebeDivulgacao;
	}

	public void setRecebeDivulgacao(boolean recebeDivulgacao) {
		this.recebeDivulgacao = recebeDivulgacao;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
