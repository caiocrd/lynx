package br.com.csl.lynx.facade;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.csl.lynx.model.MovimentacaoSvpa;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.exception.DataAccessException;
import br.com.csl.utils.exception.ServiceException;

@Service("svpaFacade")
@Transactional(rollbackFor = ServiceException.class, readOnly = true)
public class SvpaFacadeImpl implements SvpaFacade {

	private static final long serialVersionUID = 7713807888019956221L;
	
	@Autowired private DataAccess<Svpa> svpaDAO;
	@Autowired private DataAccess<MovimentacaoSvpa> movimentacaoDAO;

	@SuppressWarnings("unchecked")
	@Override
 	public List<Svpa> getSvpaWithMovements(Criterion svpaCriterion, Criterion movCriterion) {
 		Criteria criteria = svpaDAO.getSession().createCriteria(Svpa.class);
 		criteria.createAlias("endereco", "e", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.bairro", "bairro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.fk.logradouro", "logradouro", JoinType.LEFT_OUTER_JOIN);
 		criteria.createAlias("e.conjunto", "conjunto", JoinType.LEFT_OUTER_JOIN);
 		
 		
 		criteria.add(svpaCriterion);
				
 		Criteria movCriteria = criteria.createCriteria("movimentacoes");
 		
 		movCriteria.createAlias("usuario", "u");
 		
 		movCriteria.add(movCriterion);
 		 		
 		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
 		
 		return (List<Svpa>) criteria.list();
 	}

	@Override
 	public void printSvpaList(List<Svpa> svpas, Usuario usuario) throws ServiceException{
 		int count=0;
 		MovimentacaoSvpa mov;
 		
 		for (Svpa svpa : svpas) {
 			mov = new MovimentacaoSvpa();
 			mov.setSvpa(svpa);
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
 	public void receiveSvpaList(List<Svpa> svpas, Usuario usuario) throws ServiceException {
 		int count=0;
 		MovimentacaoSvpa mov;
 		
 		for (Svpa svpa : svpas) {
 			mov = new MovimentacaoSvpa();
 			mov.setSvpa(svpa);
 			mov.setMovimento(Movement.RECEIVE);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			svpa.setStatus(RipStatus.EXECUTING);
 			
 			try {
				svpaDAO.save(svpa);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        svpaDAO.getSession().flush();
		        svpaDAO.getSession().clear();
 		    }
 		}
 		
 	}	

	@Override
 	public void paySvpaList(List<Svpa> svpas, Usuario usuario) throws ServiceException {
 		int count=0;
 		MovimentacaoSvpa mov = new MovimentacaoSvpa();
 		
 		for (Svpa svpa : svpas) {
 			mov = new MovimentacaoSvpa();
 			mov.setSvpa(svpa);
 			mov.setMovimento(Movement.PAY);
 			mov.setData(CalendarUtil.getNow());
 			mov.setUsuario(usuario);
 			
 			try {
				movimentacaoDAO.save(mov);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
 			
 			svpa.setStatus(RipStatus.PAYED);
 			
 			try {
				svpaDAO.save(svpa);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
 			
 		    if ( ++count % 50 == 0 ) {
 		        movimentacaoDAO.getSession().flush();
 		        movimentacaoDAO.getSession().clear();
 		        svpaDAO.getSession().flush();
		        svpaDAO.getSession().clear();
 		    }
 		}
 		
 	}	
}
