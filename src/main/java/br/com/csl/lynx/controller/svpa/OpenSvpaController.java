package br.com.csl.lynx.controller.svpa;

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
import br.com.csl.lynx.generic.AbstractSvpaAction;
import br.com.csl.lynx.handler.EnderecoHandler;
import br.com.csl.lynx.model.OcorrenciaSvpa;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.exception.ServiceException;

/**
 * @author Caio Cesar
 * 
 */

@ManagedBean
@ViewScoped
public class OpenSvpaController extends AbstractSvpaAction {

	private static final long serialVersionUID = 1L;
	
	private Svpa selection;
	
	private String protocolo;
	private OcorrenciaSvpa ocorrencia;

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
		
		
		selection = new Svpa();
		newOccurrence = false;
		protocolo = "";
	}
	
	public void save() {
		try {
			svpa.setStatus(RipStatus.OPEN);
			svpa.setEndereco(enderecoHandler.getEndereco());
			if (svpa.getPrioridade() == null) {
				svpa.setPrioridade(3);
			}

			svpa = svpaService.save(svpa);
			
			movementHandler.open(svpa);
			movementHandler.getUserSession().setLastSvpa(svpa);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SVPA cadastrada com sucesso.", "Protocolo nº: " + svpa.getId().toString());          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
	  
	        clear();
	        
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao cadastrar SVPA. Tente novamente.");
			e.printStackTrace();
			clear();
		} catch (MovementException e) {
			addFacesErrorMessage("Erro ao cadastrar SVPA. Tente novamente.");
			e.printStackTrace();
			svpaService.remove(svpa);
			clear();
		} 
		
	}
	
	public void loadLastSvpa() {
		enderecoHandler.setEndereco(movementHandler.getUserSession().getLastSvpa().getEndereco());
		enderecoHandler.selectEndereco();
		svpa.setSolicitante(movementHandler.getUserSession().getLastSvpa().getSolicitante());
		svpa.setTelefone(movementHandler.getUserSession().getLastSvpa().getTelefone());
	}
	
	public void saveOccurrence() {
		ocorrencia.setSvpa(svpa);
		ocorrencia.setData(CalendarUtil.getNow());
		
		try {
			ocorrenciaService.save(ocorrencia);
			
			if (svpa.getStatus().equals(RipStatus.DONE)) {
				movementHandler.reversible(svpa, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
				addFacesInfoMessage("Ocorrência cadastrada com sucesso. SVPA passível de estorno.");
			} else if (svpa.getStatus().equals(RipStatus.EVALUATING)) {
				movementHandler.feedback(svpa, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
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
	
	public void loadSvpaSelecao() {
		if (svpa == null) {
			addFacesErrorMessage("SVPA não carregada.");
		}
		

		if (svpa.getStatus().equals(RipStatus.CLOSED) ||
			svpa.getStatus().equals(RipStatus.CANCELED) ||
			svpa.getStatus().equals(RipStatus.PAYED)) {
			load();
		
			addFacesErrorMessage("SVPA já encerrada.");
		} else {
			load();
			
			newOccurrence = true;
			ocorrencia = new OcorrenciaSvpa();
			ocorrencia.setNome("");
			ocorrencia.setObservacao("");
			
			addFacesErrorMessage("SVPA em andamento.", "Cadastre uma ocorrência.");
		}
		
	}
	
	public void selectPosteAndCheckSvpa() {
		
		
		List<Svpa> openSvpas = svpaService.list(Restrictions.and(
						Restrictions.and(
								Restrictions.ne("status", RipStatus.CLOSED),
								Restrictions.ne("status", RipStatus.CANCELED)),
						Restrictions.ne("status", RipStatus.PAYED)), null);
		
		if (openSvpas != null && openSvpas.size() > 0) {
			svpa = openSvpas.get(0);
			loadSvpaSelecao();
		}
	}
	
	public void select() {
		if (selection != null) {
			svpa = selection;
			loadSvpaSelecao();
		}
	}
	
	
	@Override
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
			
		selection = svpaService.find(Long.parseLong(protocolo));
		
		if (selection == null) {
			addFacesErrorMessage("Svpa não encontrada!");
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

	public Svpa getSelection() {
		return selection;
	}

	public void setSelection(Svpa selection) {
		this.selection = selection;
	}

	public OcorrenciaSvpa getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(OcorrenciaSvpa ocorrencia) {
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
