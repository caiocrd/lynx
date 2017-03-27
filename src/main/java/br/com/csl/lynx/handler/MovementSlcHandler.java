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
import br.com.csl.lynx.model.MovimentacaoSlc;
import br.com.csl.lynx.model.Slc;
import br.com.csl.lynx.session.UserSession;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class MovementSlcHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private MovimentacaoSlc movimentacao;
	
	@ManagedProperty("#{slcService}")
	private DataService<Slc> slcService;
	
	@ManagedProperty("#{movimentacaoSlcService}")
	private DataService<MovimentacaoSlc> movimentacaoService;
	
	@ManagedProperty("#{userSession}")
	private UserSession userSession;
	
	public List<MovimentacaoSlc> getMovements(Slc slc, Criterion criterion) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("slc", slc), criterion), Order.asc("id"));
		} else {
			return movimentacaoService.list("slc", slc, Order.asc("id"));
		}
		
	}
	
	public List<MovimentacaoSlc> getMovements(Slc slc, Criterion criterion, Order order) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("slc", slc), criterion), order);
		} else {
			return movimentacaoService.list("slc", slc, order);
		}
		
	}
	
	public void newMovement(Movement movement, Slc slc, String obs, Integer folder) throws MovementException {
		MovimentacaoSlc mov = new MovimentacaoSlc();
		
		mov.setData(CalendarUtil.getNow());
		mov.setMovimento(movement);
		mov.setSlc(slc);
		mov.setUsuario(userSession.getUser());
		
		if (obs != null) { mov.setObservacao(obs); }
		if (folder != null) { mov.setPasta(folder); }
		
		try {
			movimentacaoService.save(mov);
		} catch (ServiceException e) {
			throw new MovementException(e);
		}
	}
	
	public void newMovement(Movement movement, Slc slc, RipStatus status, String obs, Integer folder) throws MovementException, RipException {
		newMovement(movement, slc, obs, folder);
		if (status != null) {
			changeStatus(slc, status);
		}
	}
	
	public void changeStatus(Slc slc, RipStatus status) throws RipException {
		try {
			slc.setStatus(status);
			slcService.save(slc);
		} catch (ServiceException e) {
			throw new RipException(e);
		}
	}
	
	public void open(Slc slc) throws MovementException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.OPEN), Restrictions.eq("slc", slc))) == 0) {	
			newMovement(Movement.OPEN, slc, null, null);
		}
	}

	public void receive(Slc slc) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.RECEIVE), Restrictions.eq("slc", slc))) == 0) {	
			newMovement(Movement.RECEIVE, slc, RipStatus.EXECUTING, null, null);
		} else if (!slc.getStatus().equals(RipStatus.EXECUTING)) {
			changeStatus(slc, RipStatus.EXECUTING);
		}
	}
	
	public void print(Slc slc) throws MovementException {
		newMovement(Movement.PRINT, slc, null, null);
	}
	
	public void execute(Slc slc, String obs, Integer folder) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.EXECUTE), Restrictions.eq("slc", slc))) == 0) {
			newMovement(Movement.EXECUTE, slc, RipStatus.EVALUATING, obs, folder);
		} else if (!slc.getStatus().equals(RipStatus.EVALUATING)) {
			changeStatus(slc, RipStatus.EVALUATING);
		}
	}
	
	public void evaluate(Slc slc) throws MovementException {
		newMovement(Movement.EVALUATE, slc, null, null);
	}
	
	public void feedback(Slc slc, String obs) throws MovementException, RipException {
		newMovement(Movement.NEGATIVE_FEEDBACK, slc, RipStatus.EVALUATING_FEEDBACK, obs, null);
	}	
	
	public void toAdequate(Slc slc, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.TO_ADEQUATE, slc, RipStatus.ADEQUATING, obs, folder);
	}
	
	public void adequate(Slc slc, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.ADEQUATE, slc, RipStatus.EVALUATING, obs, folder);
	}
	
	public void done(Slc slc, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.DONE, slc, RipStatus.DONE, obs, folder);
	}
	
	public void reversible(Slc slc, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSIBLE, slc, RipStatus.REVERSING, obs, null);
	}	

	public void reverse(Slc slc, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSE, slc, RipStatus.REVERSED, obs, null);
	}	
	
	public void cancel(Slc slc, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CANCEL), Restrictions.eq("slc", slc))) == 0) {		
			newMovement(Movement.CANCEL, slc, RipStatus.CANCELED, obs, null);
		} else if (!slc.getStatus().equals(RipStatus.CANCELED)) {
			changeStatus(slc, RipStatus.CANCELED);
		}
	}
	
	public void close(Slc slc, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CLOSE), Restrictions.eq("slc", slc))) == 0) {
			newMovement(Movement.CLOSE, slc, RipStatus.CLOSED, obs, null);
		} else if (!slc.getStatus().equals(RipStatus.CLOSED)) {
			changeStatus(slc, RipStatus.CLOSED);
		}
	}
	
	public void payed(Slc slc, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.PAY), Restrictions.eq("slc", slc))) == 0) {
			newMovement(Movement.PAY, slc, RipStatus.PAYED, obs, null);
		} else if (!slc.getStatus().equals(RipStatus.PAYED)) {
			changeStatus(slc, RipStatus.PAYED);
		}
	}	
	
	public MovimentacaoSlc getMovimentacaoSlc() {
		return movimentacao;
	}

	public void setMovimentacaoSlc(MovimentacaoSlc movimentacao) {
		this.movimentacao = movimentacao;
	}

	public void setSlcService(DataService<Slc> slcService) {
		this.slcService = slcService;
	}

	public DataService<MovimentacaoSlc> getMovimentacaoService() {
		return movimentacaoService;
	}

	public void setMovimentacaoService(DataService<MovimentacaoSlc> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

}
