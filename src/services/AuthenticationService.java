package services;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.bean.UsuarioBean;
import model.entity.Usuario;

/**
 * Managed Bean que controla o servico de autenticacao.
 */
@Named
@RequestScoped
public class AuthenticationService implements Serializable {

	private static final long serialVersionUID = 3370300250980225782L;

	@Inject
	private UsuarioBean usuarioBean;

	public Usuario authenticate(String email, String senha) throws ServletException {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		
		// Tenta efetuar o login. Se falhar, sera lancada uma excecao.
		request.login(email, senha);
		
		// Obtem o Usuario logado e o adiciona na sessao.
		Usuario usuarioLogado = usuarioBean.findUsuarioByEmail(request.getUserPrincipal().getName());
		Map<String, Object> sessionMap = external.getSessionMap();
		sessionMap.put("usuario", usuarioLogado);
		
		return usuarioLogado;
	}

	public void deauthenticate() throws ServletException {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		HttpSession session = (HttpSession) external.getSession(false);

		request.logout();
		session.invalidate();
	}

	/**
	 * Verifica se o usuario atualmente autenticado possui determinado Papel.
	 */
	public boolean usuarioPossuiPapel(String papel) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		
		return request.isUserInRole(papel);
	}
}
