package model.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import model.entity.Papel;

/**
 * Managed Bean responsável por lidar com requisições da entidade Papel ao
 * repositório.
 */
@Named
@RequestScoped
public class PapelBean {

	@PersistenceContext(unitName = "sempreufgPU")
	private EntityManager em;

	/**
	 * Armazena um valor de Papel.
	 */
	@Transactional
	public void persist(Papel papel) {
		em.persist(papel);
	}

	/**
	 * Obtém todos os valores de Papel armazenados.
	 */
	public List<Papel> findAllPapeis() {
		TypedQuery<Papel> query = em.createQuery("SELECT p FROM Papel p", Papel.class);
		return query.getResultList();
	}

	/**
	 * Obtém o valor de Papel para determinado e-mail. Retorna null caso não seja
	 * encontrado exatamente um valor.
	 */
	public Papel findPapelByEmail(String email) {
		TypedQuery<Papel> query = em.createQuery("SELECT p FROM Papel p WHERE p.email = :email", Papel.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}