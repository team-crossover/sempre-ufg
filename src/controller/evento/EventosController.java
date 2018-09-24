package controller.evento;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.bean.EventoBean;
import model.entity.Evento;

@Named
@RequestScoped
public class EventosController implements Serializable {

	private static final long serialVersionUID = -6001071469836173158L;
	
	@Inject
	private EventoBean eventoBean;
	
	private List<Evento> eventosNaoAvaliados;
	
	private List<Evento> eventosAprovados;
	
	@PostConstruct
	public void atualizarListas() {
		//talvez tirar os nao avaliados daq e marcar eles como solicitacao no solicitacos. inclui renomar o isAvaliado pra isSolicitacao
		eventosNaoAvaliados = eventoBean.findEventosNaoAvaliados();
		eventosAprovados = eventoBean.findEventosAprovados();
	}

	public List<Evento> getEventosNaoAvaliados() {
		return eventosNaoAvaliados;
	}

	public void setEventosNaoAvaliados(List<Evento> eventosNaoAvaliados) {
		this.eventosNaoAvaliados = eventosNaoAvaliados;
	}

	public List<Evento> getEventosAprovados() {
		return eventosAprovados;
	}

	public void setEventosAprovados(List<Evento> eventosAprovados) {
		this.eventosAprovados = eventosAprovados;
	}
	
}
