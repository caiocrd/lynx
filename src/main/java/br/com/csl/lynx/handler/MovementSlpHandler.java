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
import br.com.csl.lynx.model.MovimentacaoSlp;
import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.session.UserSession;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class MovementSlpHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private MovimentacaoSlp movimentacao;
	
	@ManagedProperty("#{slpService}")
	private DataService<Slp> slpService;
	
	@ManagedProperty("#{movimentacaoSlpService}")
	private DataService<MovimentacaoSlp> movimentacaoService;
	
	@ManagedProperty("#{userSession}")
	private UserSession userSession;
	
	public List<MovimentacaoSlp> getMovements(Slp slp, Criterion criterion) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("slp", slp), criterion), Order.asc("id"));
		} else {
			return movimentacaoService.list("slp", slp, Order.asc("id"));
		}
		
	}
	
	public List<MovimentacaoSlp> getMovements(Slp slp, Criterion criterion, Order order) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("slp", slp), criterion), order);
		} else {
			return movimentacaoService.list("slp", slp, order);
		}
		
	}
	
	public void newMovement(Movement movement, Slp slp, String obs, Integer folder) throws MovementException {
		MovimentacaoSlp mov = new MovimentacaoSlp();
		
		mov.setData(CalendarUtil.getNow());
		mov.setMovimento(movement);
		mov.setSlp(slp);
		mov.setUsuario(userSession.getUser());
		
		if (obs != null) { mov.setObservacao(obs); }
		if (folder != null) { mov.setPasta(folder); }
		
		try {
			movimentacaoService.save(mov);
		} catch (ServiceException e) {
			throw new MovementException(e);
		}
	}
	
	public void newMovement(Movement movement, Slp slp, RipStatus status, String obs, Integer folder) throws MovementException, RipException {
		newMovement(movement, slp, obs, folder);
		if (status != null) {
			changeStatus(slp, status);
		}
	}
	
	public void changeStatus(Slp slp, RipStatus status) throws RipException {
		try {
			slp.setStatus(status);
			slpService.save(slp);
		} catch (ServiceException e) {
			throw new RipException(e);
		}
	}
	
	public void open(Slp slp) throws MovementException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.OPEN), Restrictions.eq("slp", slp))) == 0) {	
			newMovement(Movement.OPEN, slp, null, null);
		}
	}

	public void receive(Slp slp) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.RECEIVE), Restrictions.eq("slp", slp))) == 0) {	
			newMovement(Movement.RECEIVE, slp, RipStatus.EXECUTING, null, null);
		} else if (!slp.getStatus().equals(RipStatus.EXECUTING)) {
			changeStatus(slp, RipStatus.EXECUTING);
		}
	}
	
	public void print(Slp slp) throws MovementException {
		newMovement(Movement.PRINT, slp, null, null);
	}
	
	public void execute(Slp slp, String obs, Integer folder) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.EXECUTE), Restrictions.eq("slp", slp))) == 0) {
			newMovement(Movement.EXECUTE, slp, RipStatus.EVALUATING, obs, folder);
		} else if (!slp.getStatus().equals(RipStatus.EVALUATING)) {
			changeStatus(slp, RipStatus.EVALUATING);
		}
	}
	
	public void evaluate(Slp slp) throws MovementException {
		newMovement(Movement.EVALUATE, slp, null, null);
	}
	
	public void feedback(Slp slp, String obs) throws MovementException, RipException {
		newMovement(Movement.NEGATIVE_FEEDBACK, slp, RipStatus.EVALUATING_FEEDBACK, obs, null);
	}	
	
	public void toAdequate(Slp slp, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.TO_ADEQUATE, slp, RipStatus.ADEQUATING, obs, folder);
	}
	
	public void adequate(Slp slp, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.ADEQUATE, slp, RipStatus.EVALUATING, obs, folder);
	}
	
	public void done(Slp slp, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.DONE, slp, RipStatus.DONE, obs, folder);
	}
	
	public void reversible(Slp slp, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSIBLE, slp, RipStatus.REVERSING, obs, null);
	}	

	public void reverse(Slp slp, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSE, slp, RipStatus.REVERSED, obs, null);
	}	
	
	public void cancel(Slp slp, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CANCEL), Restrictions.eq("slp", slp))) == 0) {		
			newMovement(Movement.CANCEL, slp, RipStatus.CANCELED, obs, null);
		} else if (!slp.getStatus().equals(RipStatus.CANCELED)) {
			changeStatus(slp, RipStatus.CANCELED);
		}
	}
	
	public void close(Slp slp, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CLOSE), Restrictions.eq("slp", slp))) == 0) {
			newMovement(Movement.CLOSE, slp, RipStatus.CLOSED, obs, null);
		} else if (!slp.getStatus().equals(RipStatus.CLOSED)) {
			changeStatus(slp, RipStatus.CLOSED);
		}
	}
	
	public void payed(Slp slp, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.PAY), Restrictions.eq("slp", slp))) == 0) {
			newMovement(Movement.PAY, slp, RipStatus.PAYED, obs, null);
		} else if (!slp.getStatus().equals(RipStatus.PAYED)) {
			changeStatus(slp, RipStatus.PAYED);
		}
	}	
	
	public MovimentacaoSlp getMovimentacaoSlp() {
		return movimentacao;
	}

	public void setMovimentacaoSlp(MovimentacaoSlp movimentacao) {
		this.movimentacao = movimentacao;
	}

	public void setSlpService(DataService<Slp> slpService) {
		this.slpService = slpService;
	}

	public DataService<MovimentacaoSlp> getMovimentacaoService() {
		return movimentacaoService;
	}

	public void setMovimentacaoService(DataService<MovimentacaoSlp> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

}
