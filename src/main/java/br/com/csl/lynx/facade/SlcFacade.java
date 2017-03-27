package br.com.csl.lynx.facade;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import br.com.csl.lynx.model.Slc;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.exception.ServiceException;

public interface SlcFacade extends Serializable {

	List<Slc> getSlcWithMovements(Criterion slcCriterion, Criterion movCriterion);

	void printSlcList(List<Slc> slcs, Usuario usuario) throws ServiceException;

	void receiveSlcList(List<Slc> slcs, Usuario usuario) throws ServiceException;

	void paySlcList(List<Slc> slcs, Usuario usuario) throws ServiceException;
	
}
