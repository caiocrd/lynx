package br.com.csl.lynx.facade;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.exception.DataAccessException;
import br.com.csl.utils.exception.ServiceException;

@Service("ripFacade")
@Transactional(rollbackFor = ServiceException.class, readOnly = true)
public class RipFacadeImpl implements RipFacade {

	private static final long serialVersionUID = 7713807888019956221L;
	
	@Autowired private DataAccess<Rip> ripDAO;
	@Autowired private DataAccess<Movimentacao> movimentacaoDAO;

	@SuppressWarnings("unchecked")
	@Override
 	public List<Rip> getRipWithMovements(Criterion ripCriterion, Criterion movCriterion) {
 		Criteria criteria = ripDAO.getSession().createCriteria(Rip.class);
 		criteria.createAlias("endereco", "e", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.bairro", "bairro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.logradouro", "logradouro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.conjunto", "conjunto", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("poste", "p", JoinType.LEFT_OUTER_JOIN);
 		
 		criteria.add(ripCriterion);
				
 		Criteria movCriteria = criteria.createCriteria("movimentacoes");
 		
 		movCriteria.createAlias("usuario", "u");
 		
 		movCriteria.add(movCriterion);
 		 		
 		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
 		
 		return (List<Rip>) criteria.list();
 	}

	@Override
 	public void printRipList(List<Rip> rips, Usuario usuario) throws ServiceException{
 		int count=0;
 		Movimentacao mov;
 		
 		for (Rip rip : rips) {
 			mov = new Movimentacao();
 			mov.setRip(rip);
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
 	public void receiveRipList(List<Rip> rips, Usuario usuario) throws ServiceException {
 		int count=0;
 		Movimentacao mov;
 		
 		for (Rip rip : rips) {
 			mov = new Movimentacao();
 			mov.setRip(rip);
 			mov.setMovimento(Movement.RECEIVE);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			rip.setStatus(RipStatus.EXECUTING);
 			
 			try {
				ripDAO.save(rip);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        ripDAO.getSession().flush();
		        ripDAO.getSession().clear();
 		    }
 		}
 		
 	}	

	@Override
 	public void payRipList(List<Rip> rips, Usuario usuario) throws ServiceException {
 		int count=0;
 		Movimentacao mov = new Movimentacao();
 		
 		for (Rip rip : rips) {
 			mov = new Movimentacao();
 			mov.setRip(rip);
 			mov.setMovimento(Movement.PAY);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			rip.setStatus(RipStatus.PAYED);
 			
 			try {
				ripDAO.save(rip);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        ripDAO.getSession().flush();
		        ripDAO.getSession().clear();
 		    }
 		}
 		
 	}	
}
