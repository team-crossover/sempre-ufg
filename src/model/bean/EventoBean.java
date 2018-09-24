package model.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import model.entity.Evento;

/**
 * Managed Bean responsável por lidar com requisições da entidade Evento ao
 * repositório.
 */
@Named
@RequestScoped
public class EventoBean {

	@PersistenceContext(unitName = "sempreufgPU")
	private EntityManager em;

	@Transactional
	public void persist(Evento evento) {
		em.persist(evento);
	}

	@Transactional
	public Evento update(Evento evento) {
		// Checha se o evento ja existe
		Evento existingEvento = em.find(Evento.class, evento.getId());
		if (existingEvento == null) {
			System.out.println("uaiaiaiaia");
			return null;
		}

		// Atualiza o evento
		existingEvento.setAssunto(evento.getAssunto());
		existingEvento.setDescricao(evento.getDescricao());
		existingEvento.setNomeSolicitante(evento.getNomeSolicitante());
		existingEvento.setEmailSolicitante(evento.getEmailSolicitante());
		existingEvento.setAprovado(evento.isAprovado());
		existingEvento.setAvaliado(evento.isAvaliado());

		return existingEvento;
	}

	public List<Evento> findEventosAprovados() {
		TypedQuery<Evento> query = em
				.createQuery("SELECT e FROM Evento e WHERE (e.avaliado = TRUE AND e.aprovado = TRUE)", Evento.class);
		return query.getResultList();
	}

	public List<Evento> findEventosNaoAvaliados() {
		TypedQuery<Evento> query = em.createQuery("SELECT e FROM Evento e WHERE e.avaliado = FALSE", Evento.class);
		return query.getResultList();
	}

}
