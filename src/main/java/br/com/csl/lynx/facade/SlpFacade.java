package br.com.csl.lynx.facade;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.exception.ServiceException;

public interface SlpFacade extends Serializable {

	List<Slp> getSlpWithMovements(Criterion slpCriterion, Criterion movCriterion);

	void printSlpList(List<Slp> slps, Usuario usuario) throws ServiceException;

	void receiveSlpList(List<Slp> slps, Usuario usuario) throws ServiceException;

	void paySlpList(List<Slp> slps, Usuario usuario) throws ServiceException;
	
}
