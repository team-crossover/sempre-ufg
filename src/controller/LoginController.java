package controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import model.entity.Papel;
import model.entity.Usuario;
import services.AuthenticationService;

@Named
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = -4231487397652507098L;

	private static Logger logger = Logger.getLogger(AuthenticationService.class.getName());

	@Inject
	private AuthenticationService authenticationService;

	private String email;
	private String senha;
	private Usuario usuario;

	public String doLogin() {

		// Realiza autenticacao
		try {
			setUsuario(authenticationService.authenticate(email, senha));
		} catch (ServletException e) {
			if (e.getMessage().equals("Login failed")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario e/ou senha incorreto(s)", null));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			}
			return null;
		}

		// Redireciona com base no papel
		if (authenticationService.usuarioPossuiPapel(Papel.PAPEL_GESTOR)) {
			return "/view/gestor/home?faces-redirect=true";
		} else if (authenticationService.usuarioPossuiPapel(Papel.PAPEL_USUARIO)) {
			return "/view/usuario/home?faces-redirect=true";
		} else {
			logger.log(Level.SEVERE,
					"O usuário " + this.getUsuario().getId() + " autenticou-se mas não possui Papel definido.");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O usuário " + this.getUsuario().getId() + " autenticou-se mas não possui Papel definido.", null));
			return null;
		}
	}

	public String doLogout() {
		try {
			authenticationService.deauthenticate();
			this.email = null;
			this.senha = null;
			this.setUsuario(null);
		} catch (ServletException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		return "/view/login?faces-redirect=true";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
