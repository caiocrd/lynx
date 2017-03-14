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
import br.com.csl.lynx.model.MovimentacaoSvpa;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.session.UserSession;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class MovementSvpaHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private MovimentacaoSvpa movimentacao;
	
	@ManagedProperty("#{svpaService}")
	private DataService<Svpa> svpaService;
	
	@ManagedProperty("#{movimentacaoSvpaService}")
	private DataService<MovimentacaoSvpa> movimentacaoService;
	
	@ManagedProperty("#{userSession}")
	private UserSession userSession;
	
	public List<MovimentacaoSvpa> getMovements(Svpa svpa, Criterion criterion) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("svpa", svpa), criterion), Order.asc("id"));
		} else {
			return movimentacaoService.list("svpa", svpa, Order.asc("id"));
		}
		
	}
	
	public List<MovimentacaoSvpa> getMovements(Svpa svpa, Criterion criterion, Order order) {
		if (criterion != null) {
			return movimentacaoService.list(Restrictions.and(Restrictions.eq("svpa", svpa), criterion), order);
		} else {
			return movimentacaoService.list("svpa", svpa, order);
		}
		
	}
	
	public void newMovement(Movement movement, Svpa svpa, String obs, Integer folder) throws MovementException {
		MovimentacaoSvpa mov = new MovimentacaoSvpa();
		
		mov.setData(CalendarUtil.getNow());
		mov.setMovimento(movement);
		mov.setSvpa(svpa);
		mov.setUsuario(userSession.getUser());
		
		if (obs != null) { mov.setObservacao(obs); }
		if (folder != null) { mov.setPasta(folder); }
		
		try {
			movimentacaoService.save(mov);
		} catch (ServiceException e) {
			throw new MovementException(e);
		}
	}
	
	public void newMovement(Movement movement, Svpa svpa, RipStatus status, String obs, Integer folder) throws MovementException, RipException {
		newMovement(movement, svpa, obs, folder);
		if (status != null) {
			changeStatus(svpa, status);
		}
	}
	
	public void changeStatus(Svpa svpa, RipStatus status) throws RipException {
		try {
			svpa.setStatus(status);
			svpaService.save(svpa);
		} catch (ServiceException e) {
			throw new RipException(e);
		}
	}
	
	public void open(Svpa svpa) throws MovementException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.OPEN), Restrictions.eq("svpa", svpa))) == 0) {	
			newMovement(Movement.OPEN, svpa, null, null);
		}
	}

	public void receive(Svpa svpa) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.RECEIVE), Restrictions.eq("svpa", svpa))) == 0) {	
			newMovement(Movement.RECEIVE, svpa, RipStatus.EXECUTING, null, null);
		} else if (!svpa.getStatus().equals(RipStatus.EXECUTING)) {
			changeStatus(svpa, RipStatus.EXECUTING);
		}
	}
	
	public void print(Svpa svpa) throws MovementException {
		newMovement(Movement.PRINT, svpa, null, null);
	}
	
	public void execute(Svpa svpa, String obs, Integer folder) throws MovementException, RipException {		
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.EXECUTE), Restrictions.eq("svpa", svpa))) == 0) {
			newMovement(Movement.EXECUTE, svpa, RipStatus.EVALUATING, obs, folder);
		} else if (!svpa.getStatus().equals(RipStatus.EVALUATING)) {
			changeStatus(svpa, RipStatus.EVALUATING);
		}
	}
	
	public void evaluate(Svpa svpa) throws MovementException {
		newMovement(Movement.EVALUATE, svpa, null, null);
	}
	
	public void feedback(Svpa svpa, String obs) throws MovementException, RipException {
		newMovement(Movement.NEGATIVE_FEEDBACK, svpa, RipStatus.EVALUATING_FEEDBACK, obs, null);
	}	
	
	public void toAdequate(Svpa svpa, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.TO_ADEQUATE, svpa, RipStatus.ADEQUATING, obs, folder);
	}
	
	public void adequate(Svpa svpa, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.ADEQUATE, svpa, RipStatus.EVALUATING, obs, folder);
	}
	
	public void done(Svpa svpa, String obs, Integer folder) throws MovementException, RipException {
		newMovement(Movement.DONE, svpa, RipStatus.DONE, obs, folder);
	}
	
	public void reversible(Svpa svpa, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSIBLE, svpa, RipStatus.REVERSING, obs, null);
	}	

	public void reverse(Svpa svpa, String obs) throws MovementException, RipException {
		newMovement(Movement.REVERSE, svpa, RipStatus.REVERSED, obs, null);
	}	
	
	public void cancel(Svpa svpa, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CANCEL), Restrictions.eq("svpa", svpa))) == 0) {		
			newMovement(Movement.CANCEL, svpa, RipStatus.CANCELED, obs, null);
		} else if (!svpa.getStatus().equals(RipStatus.CANCELED)) {
			changeStatus(svpa, RipStatus.CANCELED);
		}
	}
	
	public void close(Svpa svpa, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.CLOSE), Restrictions.eq("svpa", svpa))) == 0) {
			newMovement(Movement.CLOSE, svpa, RipStatus.CLOSED, obs, null);
		} else if (!svpa.getStatus().equals(RipStatus.CLOSED)) {
			changeStatus(svpa, RipStatus.CLOSED);
		}
	}
	
	public void payed(Svpa svpa, String obs) throws MovementException, RipException {
		if (movimentacaoService.count(Restrictions.and(Restrictions.eq("movimento", Movement.PAY), Restrictions.eq("svpa", svpa))) == 0) {
			newMovement(Movement.PAY, svpa, RipStatus.PAYED, obs, null);
		} else if (!svpa.getStatus().equals(RipStatus.PAYED)) {
			changeStatus(svpa, RipStatus.PAYED);
		}
	}	
	
	public MovimentacaoSvpa getMovimentacaoSvpa() {
		return movimentacao;
	}

	public void setMovimentacaoSvpa(MovimentacaoSvpa movimentacao) {
		this.movimentacao = movimentacao;
	}

	public void setSvpaService(DataService<Svpa> svpaService) {
		this.svpaService = svpaService;
	}

	public DataService<MovimentacaoSvpa> getMovimentacaoService() {
		return movimentacaoService;
	}

	public void setMovimentacaoService(DataService<MovimentacaoSvpa> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

}
