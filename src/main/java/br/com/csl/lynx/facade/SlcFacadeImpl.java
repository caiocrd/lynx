package br.com.csl.lynx.facade;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.csl.lynx.model.MovimentacaoSlc;
import br.com.csl.lynx.model.Slc;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.exception.DataAccessException;
import br.com.csl.utils.exception.ServiceException;

@Service("slcFacade")
@Transactional(rollbackFor = ServiceException.class, readOnly = true)
public class SlcFacadeImpl implements SlcFacade {

	private static final long serialVersionUID = 7713807888019956221L;
	
	@Autowired private DataAccess<Slc> slcDAO;
	@Autowired private DataAccess<MovimentacaoSlc> movimentacaoDAO;

	@SuppressWarnings("unchecked")
	@Override
 	public List<Slc> getSlcWithMovements(Criterion slcCriterion, Criterion movCriterion) {
 		Criteria criteria = slcDAO.getSession().createCriteria(Slc.class);
 		criteria.createAlias("endereco", "e", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.bairro", "bairro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.logradouro", "logradouro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.conjunto", "conjunto", JoinType.LEFT_OUTER_JOIN);
 		
 		
 		criteria.add(slcCriterion);
				
 		Criteria movCriteria = criteria.createCriteria("movimentacoes");
 		
 		movCriteria.createAlias("usuario", "u");
 		
 		movCriteria.add(movCriterion);
 		 		
 		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
 		
 		return (List<Slc>) criteria.list();
 	}

	@Override
 	public void printSlcList(List<Slc> slcs, Usuario usuario) throws ServiceException{
 		int count=0;
 		MovimentacaoSlc mov;
 		
 		for (Slc slc : slcs) {
 			mov = new MovimentacaoSlc();
 			mov.setSlc(slc);
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
 	public void receiveSlcList(List<Slc> slcs, Usuario usuario) throws ServiceException {
 		int count=0;
 		MovimentacaoSlc mov;
 		
 		for (Slc slc : slcs) {
 			mov = new MovimentacaoSlc();
 			mov.setSlc(slc);
 			mov.setMovimento(Movement.RECEIVE);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			slc.setStatus(RipStatus.EXECUTING);
 			
 			try {
				slcDAO.save(slc);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        slcDAO.getSession().flush();
		        slcDAO.getSession().clear();
 		    }
 		}
 		
 	}	

	@Override
 	public void paySlcList(List<Slc> slcs, Usuario usuario) throws ServiceException {
 		int count=0;
 		MovimentacaoSlc mov = new MovimentacaoSlc();
 		
 		for (Slc slc : slcs) {
 			mov = new MovimentacaoSlc();
 			mov.setSlc(slc);
 			mov.setMovimento(Movement.PAY);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			slc.setStatus(RipStatus.PAYED);
 			
 			try {
				slcDAO.save(slc);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        slcDAO.getSession().flush();
		        slcDAO.getSession().clear();
 		    }
 		}
 		
 	}	
}
