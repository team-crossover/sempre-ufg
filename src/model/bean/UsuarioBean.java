package model.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import model.entity.Papel;
import model.entity.Usuario;

/**
 * Managed Bean responsável por lidar com requisições da entidade Usuario ao
 * repositório.
 */
@Named
@RequestScoped
public class UsuarioBean {

	@PersistenceContext(unitName = "sempreufgPU")
	private EntityManager em;

	@Inject
	private PapelBean papelBean;

	/**
	 * Sobrescreve um usuário já armazenado por outra instância com novos valores.
	 * Nada é feito se nenhum usuário com o mesmo ID for encontrado.
	 * 
	 * @return O usuario armazenado com os novos valores ou NULL se nada foi
	 *         adicionado.
	 */
	@Transactional
	public Usuario update(Usuario usuario) {
		// Checa se existia primeiro
		Usuario existingUsuario = em.find(Usuario.class, usuario.getId());
		if (existingUsuario == null) {
			return null;
		}
		
		//Checka se existe outro usuario com mesmo email
		Usuario usuarioComNovoEmail = findUsuarioByEmail(usuario.getEmail());
		if (usuarioComNovoEmail != null && usuarioComNovoEmail.getId() != existingUsuario.getId()) {
			return null;
		}
		
		// Atualiza o email do papel correspondente ao usuario
		Papel existingPapel = em.find(Papel.class, papelBean.findPapelByEmail(existingUsuario.getEmail()).getId());
		if (existingPapel != null) {
			existingPapel.setEmail(usuario.getEmail());
		}

		// Atualiza o usuario
		existingUsuario.setEmail(usuario.getEmail());
		existingUsuario.setEncodedSenha(usuario.getEncodedSenha());
		existingUsuario.setCpf(usuario.getCpf());
		existingUsuario.setNome(usuario.getNome());
		existingUsuario.setRecebeDivulgacao(usuario.isRecebeDivulgacao());
		existingUsuario.setGenero(usuario.getGenero());
		existingUsuario.setDataNascimento(usuario.getDataNascimento());

		return existingUsuario;
	}

	/**
	 * Armazena um usuário, automaticamente estabelecendo seu papel como 'usuario'.
	 */
	@Transactional
	public void persistAsUsuario(Usuario usuario) {
		// Cria o valor do papel para o usuário
		Papel papel = new Papel(usuario.getEmail(), Papel.PAPEL_USUARIO);

		// Persiste o usuário e seu papel associado
		em.persist(usuario);
		em.persist(papel);
	}

	/**
	 * Obtém todos os valores de Usuario armazenados.
	 */
	public List<Usuario> findAllUsuarios() {
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		return query.getResultList();
	}

	/**
	 * Obtém o valor de Usuario com determinado e-mail. Retorna null caso não seja
	 * encontrado exatamente um valor.
	 */
	public Usuario findUsuarioByEmail(String email) {
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
		query.setParameter("email", email);
		Usuario user = null;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}