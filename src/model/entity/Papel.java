package model.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entidade que representa o Papel de um Usuário do sistema.
 */
@Entity
public class Papel implements Serializable {

	private static final long serialVersionUID = 7838865647607440615L;

	/**
	 * Papel para usuários finais do sistema.
	 */
	public static final String PAPEL_USUARIO = "usuario";

	/**
	 * Papel para gestores do sistema.
	 */
	public static final String PAPEL_GESTOR = "gestor";

	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String papel = PAPEL_USUARIO;

	public Papel() {
	}

	public Papel(String email, String papel) {
		this.email = email;
		this.papel = papel;
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

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

}
