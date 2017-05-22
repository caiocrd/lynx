package br.com.csl.lynx.controller.rip;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

import br.com.csl.lynx.controller.poste.PosteController;
import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.PosteException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.filter.AddressFilter;
import br.com.csl.lynx.generic.AbstractRipAction;
import br.com.csl.lynx.handler.EnderecoHandler;
import br.com.csl.lynx.model.Ocorrencia;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.exception.ServiceException;

/**
 * @author Caio Cesar
 * 
 */

@ManagedBean
@ViewScoped
public class EditRipController extends AbstractRipAction {

	private static final long serialVersionUID = 1L;
	
	private Rip selection;
	
	private String protocolo;
	private Ocorrencia ocorrencia;

	private boolean newOccurrence;

	@ManagedProperty("#{enderecoHandler}")
	private EnderecoHandler enderecoHandler;

	@ManagedProperty("#{posteController}")
	private PosteController posteController;
	
	@PostConstruct
	public void init() {
		Conjunction conjunction = new Conjunction();
		conjunction.add(Restrictions.ne("status", RipStatus.CLOSED));
		conjunction.add(Restrictions.ne("status", RipStatus.PAYED));
		conjunction.add(Restrictions.ne("status", RipStatus.CANCELED));
		
		filter = new AddressFilter(conjunction, enderecoHandler);
		
		clear();
	}
	
	public void clear() {
		super.clear();
		
		java.util.Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
				
	  String action = params.get("ripId");
		selection = new Rip();
		newOccurrence = false;
		posteController.clear();
		protocolo = "";
		rip = ripService.find(Long.parseLong(action));
	}
	
	public void save() {
		try {
			rip.setPoste(posteController.providePoste());
			
			rip.setEndereco(rip.getPoste().getEndereco());
			rip.setStatus(RipStatus.OPEN);
			
			if (rip.getPrioridade() == null) {
				rip.setPrioridade(3);
			}

			rip = ripService.save(rip);
			
			movementHandler.open(rip);
			movementHandler.getUserSession().setLastRip(rip);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "RIP cadastrada com sucesso.", "Protocolo nº: " + rip.getId().toString());          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
	  
	        clear();
	        
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao cadastrar RIP. Tente novamente.");
			e.printStackTrace();
			clear();
		} catch (MovementException e) {
			addFacesErrorMessage("Erro ao cadastrar RIP. Tente novamente.");
			e.printStackTrace();
			ripService.remove(rip);
			clear();
		} catch (PosteException e) {
			addFacesErrorMessage(e.getMessage());
			
			String solicitante = rip.getSolicitante();
			String observacoes = rip.getObservacoes();
			String telefone = rip.getTelefone();
			
			selectPosteAndCheckRip();
			
			if (newOccurrence == true) {
				ocorrencia.setNome(solicitante);
				ocorrencia.setObservacao(observacoes);
				ocorrencia.setTelefone(telefone);
			}
		}
	}
	
	public void loadLastRip() {
		enderecoHandler.setEndereco(movementHandler.getUserSession().getLastRip().getEndereco());
		enderecoHandler.selectEndereco();
		rip.setSolicitante(movementHandler.getUserSession().getLastRip().getSolicitante());
		rip.setTelefone(movementHandler.getUserSession().getLastRip().getTelefone());
	}
	
	public void saveOccurrence() {
		ocorrencia.setRip(rip);
		ocorrencia.setData(CalendarUtil.getNow());
		
		try {
			ocorrenciaService.save(ocorrencia);
			
			if (rip.getStatus().equals(RipStatus.DONE)) {
				movementHandler.reversible(rip, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
				addFacesInfoMessage("Ocorrência cadastrada com sucesso. RIP passível de estorno.");
			} else if (rip.getStatus().equals(RipStatus.EVALUATING)) {
				movementHandler.feedback(rip, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
				addFacesInfoMessage("Ocorrência cadastrada com sucesso. Feedback negativo registrado.");
			} else
				addFacesInfoMessage("Ocorrência cadastrada com sucesso.");
			
		} catch (ServiceException e) {
			addFacesErrorMessage("Ocorreu um erro no sistema. A ocorrência não pôde ser cadastrada.");
			e.printStackTrace();
		} catch (MovementException | RipException e) {
			addFacesErrorMessage("Ocorrência cadastrada. Porém informações adicionais não registradas.");
			e.printStackTrace();			
		} finally {
			ocorrencia = null;
			clear();
		}

	}
	
	public void loadRipSelecao() {
		if (rip == null) {
			addFacesErrorMessage("RIP não carregada.");
		}
		

		if (rip.getStatus().equals(RipStatus.CLOSED) ||
			rip.getStatus().equals(RipStatus.CANCELED) ||
			rip.getStatus().equals(RipStatus.PAYED)) {
			load();
		
			addFacesErrorMessage("RIP já encerrada.");
		} else {
			load();
			
			newOccurrence = true;
			ocorrencia = new Ocorrencia();
			ocorrencia.setNome("");
			ocorrencia.setObservacao("");
			
			addFacesErrorMessage("RIP em andamento.", "Cadastre uma ocorrência.");
		}
		
	}
	
	public void selectPosteAndCheckRip() {
		posteController.select();
		
		List<Rip> openRips = ripService.list(Restrictions.and(
				Restrictions.and(
						Restrictions.and(
								Restrictions.ne("status", RipStatus.CLOSED),
								Restrictions.ne("status", RipStatus.CANCELED)),
						Restrictions.ne("status", RipStatus.PAYED)), Restrictions.eq("poste", posteController.getPoste())), null);
		
		if (openRips != null && openRips.size() > 0) {
			rip = openRips.get(0);
			loadRipSelecao();
		}
	}
	
	public void select() {
		if (selection != null) {
			rip = selection;
			loadRipSelecao();
		}
	}
	
	public void checkPosteAndRip() {
		posteController.checkPoste();
		
		if (posteController.getPoste().getId() != null)
			selectPosteAndCheckRip();
	}
	
	@Override
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
			
		selection = ripService.find(Long.parseLong(protocolo));
		
		if (selection == null) {
			addFacesErrorMessage("Rip não encontrada!");
		} else	{
			select();
		}
	}
	
	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Rip getSelection() {
		return selection;
	}

	public void setSelection(Rip selection) {
		this.selection = selection;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public boolean getNewOccurrence() {
		return newOccurrence;
	}

	public void setNewOccurrence(boolean newOccurrence) {
		this.newOccurrence = newOccurrence;
	}

	public void setEnderecoHandler(EnderecoHandler enderecoHandler) {
		this.enderecoHandler = enderecoHandler;
	}

	public void setPosteController(PosteController posteController) {
		this.posteController = posteController;
	}

	
}
