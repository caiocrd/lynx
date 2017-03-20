package br.com.csl.lynx.facade;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.csl.lynx.model.MovimentacaoSlp;
import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.exception.DataAccessException;
import br.com.csl.utils.exception.ServiceException;

@Service("slpFacade")
@Transactional(rollbackFor = ServiceException.class, readOnly = true)
public class SlpFacadeImpl implements SlpFacade {

	private static final long serialVersionUID = 7713807888019956221L;
	
	@Autowired private DataAccess<Slp> slpDAO;
	@Autowired private DataAccess<MovimentacaoSlp> movimentacaoDAO;

	@SuppressWarnings("unchecked")
	@Override
 	public List<Slp> getSlpWithMovements(Criterion slpCriterion, Criterion movCriterion) {
 		Criteria criteria = slpDAO.getSession().createCriteria(Slp.class);
 		criteria.createAlias("endereco", "e", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.bairro", "bairro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.logradouro", "logradouro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.conjunto", "conjunto", JoinType.LEFT_OUTER_JOIN);
 		
 		
 		criteria.add(slpCriterion);
				
 		Criteria movCriteria = criteria.createCriteria("movimentacoes");
 		
 		movCriteria.createAlias("usuario", "u");
 		
 		movCriteria.add(movCriterion);
 		 		
 		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
 		
 		return (List<Slp>) criteria.list();
 	}

	@Override
 	public void printSlpList(List<Slp> slps, Usuario usuario) throws ServiceException{
 		int count=0;
 		MovimentacaoSlp mov;
 		
 		for (Slp slp : slps) {
 			mov = new MovimentacaoSlp();
 			mov.setSlp(slp);
 			mov.setMovimento(Movement.PRINT);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		    }
 		}
 		
 	}	
	
	@Override
 	public void receiveSlpList(List<Slp> slps, Usuario usuario) throws ServiceException {
 		int count=0;
 		MovimentacaoSlp mov;
 		
 		for (Slp slp : slps) {
 			mov = new MovimentacaoSlp();
 			mov.setSlp(slp);
 			mov.setMovimento(Movement.RECEIVE);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			slp.setStatus(RipStatus.EXECUTING);
 			
 			try {
				slpDAO.save(slp);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        slpDAO.getSession().flush();
		        slpDAO.getSession().clear();
 		    }
 		}
 		
 	}	

	@Override
 	public void paySlpList(List<Slp> slps, Usuario usuario) throws ServiceException {
 		int count=0;
 		MovimentacaoSlp mov = new MovimentacaoSlp();
 		
 		for (Slp slp : slps) {
 			mov = new MovimentacaoSlp();
 			mov.setSlp(slp);
 			mov.setMovimento(Movement.PAY);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			slp.setStatus(RipStatus.PAYED);
 			
 			try {
				slpDAO.save(slp);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        slpDAO.getSession().flush();
		        slpDAO.getSession().clear();
 		    }
 		}
 		
 	}	
}
