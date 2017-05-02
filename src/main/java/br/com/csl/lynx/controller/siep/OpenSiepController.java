package br.com.csl.lynx.controller.siep;

import java.util.Calendar;
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
import br.com.csl.lynx.exception.SiepException;
import br.com.csl.lynx.filter.AddressFilter;
import br.com.csl.lynx.generic.AbstractSiepAction;
import br.com.csl.lynx.handler.EnderecoHandler;
import br.com.csl.lynx.model.OcorrenciaSiep;
import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.exception.ServiceException;

/**
 * @author Caio Cesar
 * 
 */

@ManagedBean
@ViewScoped
public class OpenSiepController extends AbstractSiepAction {

	private static final long serialVersionUID = 1L;
	
	private Siep selection;
	
	private String protocolo;
	private OcorrenciaSiep ocorrencia;

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
		
		
		selection = new Siep();
		newOccurrence = false;
		protocolo = "";
	}
	
	public void save() {
		try {
			siep.setStatus(RipStatus.OPEN);
			siep.setEndereco(enderecoHandler.getEndereco());
			if (siep.getPrioridade() == null) {
				siep.setPrioridade(3);
			}
			
			Calendar siepMinDate = CalendarUtil.getToday();
			siepMinDate.add(Calendar.DAY_OF_MONTH, 7);
			if(siep.getDataEvento().before(siepMinDate)){
				addFacesErrorMessage("Evento deve ser marcado pelo menos 7 (sete) dias antes");
				return;
			}
			
			siep = siepService.save(siep);
			
			movementHandler.open(siep);
			movementHandler.getUserSession().setLastSiep(siep);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SIEP cadastrada com sucesso.", "Protocolo nº: " + siep.getId().toString());          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
	  
	        clear();
		
				
			  
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao cadastrar SIEP. Tente novamente.");
			e.printStackTrace();
			clear();
		} catch (MovementException e) {
			addFacesErrorMessage("Erro ao cadastrar SIEP. Tente novamente.");
			e.printStackTrace();
			siepService.remove(siep);
			clear();
		} 
		
	}
	
	public void loadLastSiep() {
		enderecoHandler.setEndereco(movementHandler.getUserSession().getLastSiep().getEndereco());
		enderecoHandler.selectEndereco();
		siep.setSolicitante(movementHandler.getUserSession().getLastSiep().getSolicitante());
		siep.setTelefone(movementHandler.getUserSession().getLastSiep().getTelefone());
	}
	
	public void saveOccurrence() {
		ocorrencia.setSiep(siep);
		ocorrencia.setData(CalendarUtil.getNow());
		
		try {
			ocorrenciaService.save(ocorrencia);
			
			if (siep.getStatus().equals(RipStatus.DONE)) {
				movementHandler.reversible(siep, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
				addFacesInfoMessage("Ocorrência cadastrada com sucesso. SIEP passível de estorno.");
			} else if (siep.getStatus().equals(RipStatus.EVALUATING)) {
				movementHandler.feedback(siep, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
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
	
	public void loadSiepSelecao() {
		if (siep == null) {
			addFacesErrorMessage("SIEP não carregada.");
		}
		

		if (siep.getStatus().equals(RipStatus.CLOSED) ||
			siep.getStatus().equals(RipStatus.CANCELED) ||
			siep.getStatus().equals(RipStatus.PAYED)) {
			load();
		
			addFacesErrorMessage("SIEP já encerrada.");
		} else {
			load();
			
			newOccurrence = true;
			ocorrencia = new OcorrenciaSiep();
			ocorrencia.setNome("");
			ocorrencia.setObservacao("");
			
			addFacesErrorMessage("SIEP em andamento.", "Cadastre uma ocorrência.");
		}
		
	}
	
	public void selectPosteAndCheckSiep() {
		
		
		List<Siep> openSieps = siepService.list(Restrictions.and(
						Restrictions.and(
								Restrictions.ne("status", RipStatus.CLOSED),
								Restrictions.ne("status", RipStatus.CANCELED)),
						Restrictions.ne("status", RipStatus.PAYED)), null);
		
		if (openSieps != null && openSieps.size() > 0) {
			siep = openSieps.get(0);
			loadSiepSelecao();
		}
	}
	
	public void select() {
		if (selection != null) {
			siep = selection;
			loadSiepSelecao();
		}
	}
	
	
	@Override
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
			
		selection = siepService.find(Long.parseLong(protocolo));
		
		if (selection == null) {
			addFacesErrorMessage("Siep não encontrada!");
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

	public Siep getSelection() {
		return selection;
	}

	public void setSelection(Siep selection) {
		this.selection = selection;
	}

	public OcorrenciaSiep getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(OcorrenciaSiep ocorrencia) {
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
