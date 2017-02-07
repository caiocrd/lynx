package br.com.csl.lynx.facade;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.exception.ServiceException;

public interface RipFacade extends Serializable {

	List<Rip> getRipWithMovements(Criterion ripCriterion, Criterion movCriterion);

	void printRipList(List<Rip> rips, Usuario usuario) throws ServiceException;

	void receiveRipList(List<Rip> rips, Usuario usuario) throws ServiceException;

	void payRipList(List<Rip> rips, Usuario usuario) throws ServiceException;
	
}
