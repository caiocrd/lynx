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
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.session.UserSession;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class MovementHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Movimentacao movimentacao;
	
	@ManagedProperty("#{ripService}")
	private DataService<Rip> ripService;
	
	@ManagedProperty("#{movimentacaoService}")
	private DataService<Movimentacao> movimentacaoService;
	
	@ManagedProperty("#{userSession}")
	private UserSession userSession;
	
	public List<Movimentacao> getMovements(Rip rip, Criterion criterion) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("rip", rip), criterion), Order.asc("id"));
		} else {
			return movimentacaoService.list("rip", rip, Order.asc("id"));
		}
		
	}
	
	public List<Movimentacao> getMovements(Rip rip, Criterion criterion, Order order) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("rip", rip), criterion), order);
		} else {
			return movimentacaoService.list("rip", rip, order);
		}
		
	}
	
	public void newMovement(Movement movement, Rip rip, String obs, Integer folder) throws MovementException {
		Movimentacao mov = new Movimentacao();
		
		mov.setData(CalendarUtil.getNow());
		mov.setMovimento(movement);
		mov.setRip(rip);
		mov.setUsuario(userSession.getUser());
		
		if (obs != null) { mov.setObservacao(obs); }
		if (folder != null) { mov.setPasta(folder); }
		
		try {
			movimentacaoService.save(mov);
		} catch (ServiceException e) {
			throw new MovementException(e);
		}
	}
	
	public void newMovement(Movement movement, Rip rip, RipStatus status, String obs, Integer folder) throws MovementException, RipException {
		newMovement(movement, rip, obs, folder);
		if (status != null) {
			changeStatus(rip, status);
		}
	}
	
	public void changeStatus(Rip rip, RipStatus status) throws RipException {
		try {
			rip.setStatus(status);
			ripService.save(rip);
		} catch (ServiceException e) {
			throw new RipException(e);
		}
	}
	
	public void open(Rip rip) throws MovementException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.OPEN), Restrictions.eq("rip", rip))) == 0) {	
			newMovement(Movement.OPEN, rip, null, null);
		}
	}

	public void receive(Rip rip) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.RECEIVE), Restrictions.eq("rip", rip))) == 0) {	
			newMovement(Movement.RECEIVE, rip, RipStatus.EXECUTING, null, null);
		} else if (!rip.getStatus().equals(RipStatus.EXECUTING)) {
			changeStatus(rip, RipStatus.EXECUTING);
		}
	}
	
	public void print(Rip rip) throws MovementException {
		newMovement(Movement.PRINT, rip, null, null);
	}
	
	public void execute(Rip rip, String obs, Integer folder) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.EXECUTE), Restrictions.eq("rip", rip))) == 0) {
			newMovement(Movement.EXECUTE, rip, RipStatus.EVALUATING, obs, folder);
		} else if (!rip.getStatus().equals(RipStatus.EVALUATING)) {
			changeStatus(rip, RipStatus.EVALUATING);
		}
	}
	
	public void evaluate(Rip rip) throws MovementException {
		newMovement(Movement.EVALUATE, rip, null, null);
	}
	
	public void feedback(Rip rip, String obs) throws MovementException, RipException {
		newMovement(Movement.NEGATIVE_FEEDBACK, rip, RipStatus.EVALUATING_FEEDBACK, obs, null);
	}	
	
	public void toAdequate(Rip rip, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.TO_ADEQUATE, rip, RipStatus.ADEQUATING, obs, folder);
	}
	
	public void adequate(Rip rip, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.ADEQUATE, rip, RipStatus.EVALUATING, obs, folder);
	}
	
	public void done(Rip rip, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.DONE, rip, RipStatus.DONE, obs, folder);
	}
	
	public void reversible(Rip rip, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSIBLE, rip, RipStatus.REVERSING, obs, null);
	}	

	public void reverse(Rip rip, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSE, rip, RipStatus.REVERSED, obs, null);
	}	
	
	public void cancel(Rip rip, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CANCEL), Restrictions.eq("rip", rip))) == 0) {		
			newMovement(Movement.CANCEL, rip, RipStatus.CANCELED, obs, null);
		} else if (!rip.getStatus().equals(RipStatus.CANCELED)) {
			changeStatus(rip, RipStatus.CANCELED);
		}
	}
	
	public void close(Rip rip, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CLOSE), Restrictions.eq("rip", rip))) == 0) {
			newMovement(Movement.CLOSE, rip, RipStatus.CLOSED, obs, null);
		} else if (!rip.getStatus().equals(RipStatus.CLOSED)) {
			changeStatus(rip, RipStatus.CLOSED);
		}
	}
	
	public void payed(Rip rip, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.PAY), Restrictions.eq("rip", rip))) == 0) {
			newMovement(Movement.PAY, rip, RipStatus.PAYED, obs, null);
		} else if (!rip.getStatus().equals(RipStatus.PAYED)) {
			changeStatus(rip, RipStatus.PAYED);
		}
	}	
	
	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public void setRipService(DataService<Rip> ripService) {
		this.ripService = ripService;
	}

	public DataService<Movimentacao> getMovimentacaoService() {
		return movimentacaoService;
	}

	public void setMovimentacaoService(DataService<Movimentacao> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

}
