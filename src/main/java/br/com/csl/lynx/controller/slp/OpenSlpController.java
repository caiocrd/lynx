package br.com.csl.lynx.controller.slp;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.filter.AddressFilter;
import br.com.csl.lynx.generic.AbstractSlpAction;
import br.com.csl.lynx.handler.EnderecoHandler;
import br.com.csl.lynx.model.OcorrenciaSlp;
import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.exception.ServiceException;

/**
 * @author Caio Cesar
 * 
 */

@ManagedBean
@ViewScoped
public class OpenSlpController extends AbstractSlpAction {

	private static final long serialVersionUID = 1L;
	
	private Slp selection;
	
	private String protocolo;
	private OcorrenciaSlp ocorrencia;

	private boolean newOccurrence;

	@ManagedProperty("#{enderecoHandler}")
	private EnderecoHandler enderecoHandler;

	
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
		
		
		selection = new Slp();
		newOccurrence = false;
		protocolo = "";
	}
	
	public void save() {
		try {
			slp.setStatus(RipStatus.OPEN);
			slp.setEndereco(enderecoHandler.getEndereco());
			if (slp.getPrioridade() == null) {
				slp.setPrioridade(3);
			}

			slp = slpService.save(slp);
			
			movementHandler.open(slp);
			movementHandler.getUserSession().setLastSlp(slp);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SLP cadastrada com sucesso.", "Protocolo nº: " + slp.getId().toString());          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
	  
	        clear();
	        
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao cadastrar SLP. Tente novamente.");
			e.printStackTrace();
			clear();
		} catch (MovementException e) {
			addFacesErrorMessage("Erro ao cadastrar SLP. Tente novamente.");
			e.printStackTrace();
			slpService.remove(slp);
			clear();
		} 
		
	}
	
	public void loadLastSlp() {
		enderecoHandler.setEndereco(movementHandler.getUserSession().getLastSlp().getEndereco());
		enderecoHandler.selectEndereco();
		slp.setSolicitante(movementHandler.getUserSession().getLastSlp().getSolicitante());
		slp.setTelefone(movementHandler.getUserSession().getLastSlp().getTelefone());
	}
	
	public void saveOccurrence() {
		ocorrencia.setSlp(slp);
		ocorrencia.setData(CalendarUtil.getNow());
		
		try {
			ocorrenciaService.save(ocorrencia);
			
			if (slp.getStatus().equals(RipStatus.DONE)) {
				movementHandler.reversible(slp, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
				addFacesInfoMessage("Ocorrência cadastrada com sucesso. SLP passível de estorno.");
			} else if (slp.getStatus().equals(RipStatus.EVALUATING)) {
				movementHandler.feedback(slp, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
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
	
	public void loadSlpSelecao() {
		if (slp == null) {
			addFacesErrorMessage("SLP não carregada.");
		}
		

		if (slp.getStatus().equals(RipStatus.CLOSED) ||
			slp.getStatus().equals(RipStatus.CANCELED) ||
			slp.getStatus().equals(RipStatus.PAYED)) {
			load();
		
			addFacesErrorMessage("SLP já encerrada.");
		} else {
			load();
			
			newOccurrence = true;
			ocorrencia = new OcorrenciaSlp();
			ocorrencia.setNome("");
			ocorrencia.setObservacao("");
			
			addFacesErrorMessage("SLP em andamento.", "Cadastre uma ocorrência.");
		}
		
	}
	
	public void selectPosteAndCheckSlp() {
		
		
		List<Slp> openSlps = slpService.list(Restrictions.and(
						Restrictions.and(
								Restrictions.ne("status", RipStatus.CLOSED),
								Restrictions.ne("status", RipStatus.CANCELED)),
						Restrictions.ne("status", RipStatus.PAYED)), null);
		
		if (openSlps != null && openSlps.size() > 0) {
			slp = openSlps.get(0);
			loadSlpSelecao();
		}
	}
	
	public void select() {
		if (selection != null) {
			slp = selection;
			loadSlpSelecao();
		}
	}
	
	
	@Override
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
			
		selection = slpService.find(Long.parseLong(protocolo));
		
		if (selection == null) {
			addFacesErrorMessage("Slp não encontrada!");
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

	public Slp getSelection() {
		return selection;
	}

	public void setSelection(Slp selection) {
		this.selection = selection;
	}

	public OcorrenciaSlp getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(OcorrenciaSlp ocorrencia) {
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

}
