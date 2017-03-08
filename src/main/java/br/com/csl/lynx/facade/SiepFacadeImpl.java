package br.com.csl.lynx.facade;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.csl.lynx.model.MovimentacaoSiep;
import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.exception.DataAccessException;
import br.com.csl.utils.exception.ServiceException;

@Service("siepFacade")
@Transactional(rollbackFor = ServiceException.class, readOnly = true)
public class SiepFacadeImpl implements SiepFacade {

	private static final long serialVersionUID = 7713807888019956221L;
	
	@Autowired private DataAccess<Siep> siepDAO;
	@Autowired private DataAccess<MovimentacaoSiep> movimentacaoDAO;

	@SuppressWarnings("unchecked")
	@Override
 	public List<Siep> getSiepWithMovements(Criterion siepCriterion, Criterion movCriterion) {
 		Criteria criteria = siepDAO.getSession().createCriteria(Siep.class);
 		criteria.createAlias("endereco", "e", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.bairro", "bairro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.logradouro", "logradouro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.conjunto", "conjunto", JoinType.LEFT_OUTER_JOIN);
 		
 		
 		criteria.add(siepCriterion);
				
 		Criteria movCriteria = criteria.createCriteria("movimentacoes");
 		
 		movCriteria.createAlias("usuario", "u");
 		
 		movCriteria.add(movCriterion);
 		 		
 		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
 		
 		return (List<Siep>) criteria.list();
 	}

	@Override
 	public void printSiepList(List<Siep> sieps, Usuario usuario) throws ServiceException{
 		int count=0;
 		MovimentacaoSiep mov;
 		
 		for (Siep siep : sieps) {
 			mov = new MovimentacaoSiep();
 			mov.setSiep(siep);
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
 	public void receiveSiepList(List<Siep> sieps, Usuario usuario) throws ServiceException {
 		int count=0;
 		MovimentacaoSiep mov;
 		
 		for (Siep siep : sieps) {
 			mov = new MovimentacaoSiep();
 			mov.setSiep(siep);
 			mov.setMovimento(Movement.RECEIVE);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			siep.setStatus(RipStatus.EXECUTING);
 			
 			try {
				siepDAO.save(siep);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        siepDAO.getSession().flush();
		        siepDAO.getSession().clear();
 		    }
 		}
 		
 	}	

	@Override
 	public void paySiepList(List<Siep> sieps, Usuario usuario) throws ServiceException {
 		int count=0;
 		MovimentacaoSiep mov = new MovimentacaoSiep();
 		
 		for (Siep siep : sieps) {
 			mov = new MovimentacaoSiep();
 			mov.setSiep(siep);
 			mov.setMovimento(Movement.PAY);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			siep.setStatus(RipStatus.PAYED);
 			
 			try {
				siepDAO.save(siep);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        siepDAO.getSession().flush();
		        siepDAO.getSession().clear();
 		    }
 		}
 		
 	}	
}
