package br.com.csl.lynx.handler;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.model.MovimentacaoSiep;
import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.session.UserSession;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class MovementSiepHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private MovimentacaoSiep movimentacao;
	
	@ManagedProperty("#{siepService}")
	private DataService<Siep> siepService;
	
	@ManagedProperty("#{movimentacaoSiepService}")
	private DataService<MovimentacaoSiep> movimentacaoService;
	
	@ManagedProperty("#{userSession}")
	private UserSession userSession;
	
	public List<MovimentacaoSiep> getMovements(Siep siep, Criterion criterion) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("siep", siep), criterion), Order.asc("id"));
		} else {
			return movimentacaoService.list("siep", siep, Order.asc("id"));
		}
		
	}
	
	public List<MovimentacaoSiep> getMovements(Siep siep, Criterion criterion, Order order) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("siep", siep), criterion), order);
		} else {
			return movimentacaoService.list("siep", siep, order);
		}
		
	}
	
	public void newMovement(Movement movement, Siep siep, String obs, Integer folder) throws MovementException {
		MovimentacaoSiep mov = new MovimentacaoSiep();
		
		mov.setData(CalendarUtil.getNow());
		mov.setMovimento(movement);
		mov.setSiep(siep);
		mov.setUsuario(userSession.getUser());
		
		if (obs != null) { mov.setObservacao(obs); }
		if (folder != null) { mov.setPasta(folder); }
		
		try {
			movimentacaoService.save(mov);
		} catch (ServiceException e) {
			throw new MovementException(e);
		}
	}
	
	public void newMovement(Movement movement, Siep siep, RipStatus status, String obs, Integer folder) throws MovementException, RipException {
		newMovement(movement, siep, obs, folder);
		if (status != null) {
			changeStatus(siep, status);
		}
	}
	
	public void changeStatus(Siep siep, RipStatus status) throws RipException {
		try {
			siep.setStatus(status);
			siepService.save(siep);
		} catch (ServiceException e) {
			throw new RipException(e);
		}
	}
	
	public void open(Siep siep) throws MovementException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.OPEN), Restrictions.eq("siep", siep))) == 0) {	
			newMovement(Movement.OPEN, siep, null, null);
		}
	}

	public void receive(Siep siep) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.RECEIVE), Restrictions.eq("siep", siep))) == 0) {	
			newMovement(Movement.RECEIVE, siep, RipStatus.EXECUTING, null, null);
		} else if (!siep.getStatus().equals(RipStatus.EXECUTING)) {
			changeStatus(siep, RipStatus.EXECUTING);
		}
	}
	
	public void print(Siep siep) throws MovementException {
		newMovement(Movement.PRINT, siep, null, null);
	}
	
	public void execute(Siep siep, String obs, Integer folder) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.EXECUTE), Restrictions.eq("siep", siep))) == 0) {
			newMovement(Movement.EXECUTE, siep, RipStatus.EVALUATING, obs, folder);
		} else if (!siep.getStatus().equals(RipStatus.EVALUATING)) {
			changeStatus(siep, RipStatus.EVALUATING);
		}
	}
	
	public void evaluate(Siep siep) throws MovementException {
		newMovement(Movement.EVALUATE, siep, null, null);
	}
	
	public void feedback(Siep siep, String obs) throws MovementException, RipException {
		newMovement(Movement.NEGATIVE_FEEDBACK, siep, RipStatus.EVALUATING_FEEDBACK, obs, null);
	}	
	
	public void toAdequate(Siep siep, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.TO_ADEQUATE, siep, RipStatus.ADEQUATING, obs, folder);
	}
	
	public void adequate(Siep siep, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.ADEQUATE, siep, RipStatus.EVALUATING, obs, folder);
	}
	
	public void done(Siep siep, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.DONE, siep, RipStatus.DONE, obs, folder);
	}
	
	public void reversible(Siep siep, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSIBLE, siep, RipStatus.REVERSING, obs, null);
	}	

	public void reverse(Siep siep, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSE, siep, RipStatus.REVERSED, obs, null);
	}	
	
	public void cancel(Siep siep, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CANCEL), Restrictions.eq("siep", siep))) == 0) {		
			newMovement(Movement.CANCEL, siep, RipStatus.CANCELED, obs, null);
		} else if (!siep.getStatus().equals(RipStatus.CANCELED)) {
			changeStatus(siep, RipStatus.CANCELED);
		}
	}
	
	public void close(Siep siep, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CLOSE), Restrictions.eq("siep", siep))) == 0) {
			newMovement(Movement.CLOSE, siep, RipStatus.CLOSED, obs, null);
		} else if (!siep.getStatus().equals(RipStatus.CLOSED)) {
			changeStatus(siep, RipStatus.CLOSED);
		}
	}
	
	public void payed(Siep siep, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.PAY), Restrictions.eq("siep", siep))) == 0) {
			newMovement(Movement.PAY, siep, RipStatus.PAYED, obs, null);
		} else if (!siep.getStatus().equals(RipStatus.PAYED)) {
			changeStatus(siep, RipStatus.PAYED);
		}
	}	
	
	public MovimentacaoSiep getMovimentacaoSiep() {
		return movimentacao;
	}

	public void setMovimentacaoSiep(MovimentacaoSiep movimentacao) {
		this.movimentacao = movimentacao;
	}

	public void setSiepService(DataService<Siep> siepService) {
		this.siepService = siepService;
	}

	public DataService<MovimentacaoSiep> getMovimentacaoService() {
		return movimentacaoService;
	}

	public void setMovimentacaoService(DataService<MovimentacaoSiep> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

}
