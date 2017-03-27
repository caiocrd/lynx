package br.com.csl.lynx.controller.slc;

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
import br.com.csl.lynx.generic.AbstractSlcAction;
import br.com.csl.lynx.handler.EnderecoHandler;
import br.com.csl.lynx.model.OcorrenciaSlc;
import br.com.csl.lynx.model.Slc;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.exception.ServiceException;

/**
 * @author Caio Cesar
 * 
 */

@ManagedBean
@ViewScoped
public class OpenSlcController extends AbstractSlcAction {

	private static final long serialVersionUID = 1L;
	
	private Slc selection;
	
	private String protocolo;
	private OcorrenciaSlc ocorrencia;

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
		
		
		selection = new Slc();
		newOccurrence = false;
		protocolo = "";
	}
	
	public void save() {
		try {
			slc.setStatus(RipStatus.OPEN);
			slc.setEndereco(enderecoHandler.getEndereco());
			if (slc.getPrioridade() == null) {
				slc.setPrioridade(3);
			}

			slc = slcService.save(slc);
			
			movementHandler.open(slc);
			movementHandler.getUserSession().setLastSlc(slc);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SLC cadastrada com sucesso.", "Protocolo nº: " + slc.getId().toString());          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
	  
	        clear();
	        
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao cadastrar SLC. Tente novamente.");
			e.printStackTrace();
			clear();
		} catch (MovementException e) {
			addFacesErrorMessage("Erro ao cadastrar SLC. Tente novamente.");
			e.printStackTrace();
			slcService.remove(slc);
			clear();
		} 
		
	}
	
	public void loadLastSlc() {
		enderecoHandler.setEndereco(movementHandler.getUserSession().getLastSlc().getEndereco());
		enderecoHandler.selectEndereco();
		slc.setSolicitante(movementHandler.getUserSession().getLastSlc().getSolicitante());
		slc.setTelefone(movementHandler.getUserSession().getLastSlc().getTelefone());
	}
	
	public void saveOccurrence() {
		ocorrencia.setSlc(slc);
		ocorrencia.setData(CalendarUtil.getNow());
		
		try {
			ocorrenciaService.save(ocorrencia);
			
			if (slc.getStatus().equals(RipStatus.DONE)) {
				movementHandler.reversible(slc, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
				addFacesInfoMessage("Ocorrência cadastrada com sucesso. SLC passível de estorno.");
			} else if (slc.getStatus().equals(RipStatus.EVALUATING)) {
				movementHandler.feedback(slc, "[FEEDBACK DE " + ocorrencia.getNome().toUpperCase() + "]: " + ocorrencia.getObservacao());
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
	
	public void loadSlcSelecao() {
		if (slc == null) {
			addFacesErrorMessage("SLC não carregada.");
		}
		

		if (slc.getStatus().equals(RipStatus.CLOSED) ||
			slc.getStatus().equals(RipStatus.CANCELED) ||
			slc.getStatus().equals(RipStatus.PAYED)) {
			load();
		
			addFacesErrorMessage("SLC já encerrada.");
		} else {
			load();
			
			newOccurrence = true;
			ocorrencia = new OcorrenciaSlc();
			ocorrencia.setNome("");
			ocorrencia.setObservacao("");
			
			addFacesErrorMessage("SLC em andamento.", "Cadastre uma ocorrência.");
		}
		
	}
	
	public void selectPosteAndCheckSlc() {
		
		
		List<Slc> openSlcs = slcService.list(Restrictions.and(
						Restrictions.and(
								Restrictions.ne("status", RipStatus.CLOSED),
								Restrictions.ne("status", RipStatus.CANCELED)),
						Restrictions.ne("status", RipStatus.PAYED)), null);
		
		if (openSlcs != null && openSlcs.size() > 0) {
			slc = openSlcs.get(0);
			loadSlcSelecao();
		}
	}
	
	public void select() {
		if (selection != null) {
			slc = selection;
			loadSlcSelecao();
		}
	}
	
	
	@Override
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
			
		selection = slcService.find(Long.parseLong(protocolo));
		
		if (selection == null) {
			addFacesErrorMessage("Slc não encontrada!");
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

	public Slc getSelection() {
		return selection;
	}

	public void setSelection(Slc selection) {
		this.selection = selection;
	}

	public OcorrenciaSlc getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(OcorrenciaSlc ocorrencia) {
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
