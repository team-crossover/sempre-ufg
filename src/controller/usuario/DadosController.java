package controller.usuario;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import controller.LoginController;
import model.bean.UsuarioBean;
import model.entity.Usuario;

/**
 * SessionScoped Bean que age como Controller responsável por alterar dados de
 * Usuários.
 */
@Named
@RequestScoped
public class DadosController implements Serializable {

	private static final long serialVersionUID = -6004071469836183158L;

	@Inject
	private UsuarioBean usuarioEjb;

	@Inject
	private LoginController loginController;

	private String email;
	private String senha;
	private String cpf;
	private String nome;
	private boolean recebeDivulgacao;
	private String genero;
	private Date dataNascimento;

	// Executado no inicio da requisicao que usar este Bean
	@PostConstruct
	public void updateDados() {
		Usuario usuario = loginController.getUsuario();
		email = usuario.getEmail();
		senha = null;
		cpf = usuario.getCpf();
		nome = usuario.getNome();
		recebeDivulgacao = usuario.isRecebeDivulgacao();
		genero = usuario.getGenero();
		dataNascimento = usuario.getDataNascimento();
	}

	/**
	 * Ação para atualizar os dados do usuário logado salvar os dados atuais.
	 */
	public String doSave() {
		Usuario oldUsuario = loginController.getUsuario();
		Usuario newUsuario = new Usuario();

		newUsuario.setId(oldUsuario.getId());
		newUsuario.setEmail(email);
		String oldEncodedSenha = oldUsuario.getEncodedSenha();
		if (senha == null || senha.isEmpty() || senha.replace(" ", "").isEmpty()) {
			// Mantem a senha antiga
			newUsuario.setEncodedSenha(oldEncodedSenha);
		} else {
			// Muda a senha
			newUsuario.setDecodedSenha(senha);
		}
		newUsuario.setCpf(cpf);
		newUsuario.setNome(nome);
		newUsuario.setRecebeDivulgacao(recebeDivulgacao);
		newUsuario.setGenero(genero);
		newUsuario.setDataNascimento(dataNascimento);

		Usuario savedUsuario = usuarioEjb.update(newUsuario);
		if (savedUsuario == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível atualizar", null));
			return null;
		}

		loginController.setUsuario(savedUsuario);
		updateDados();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados atualizados", null));
		return null;
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
