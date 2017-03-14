package br.com.csl.lynx.facade;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.exception.ServiceException;

public interface SvpaFacade extends Serializable {

	List<Svpa> getSvpaWithMovements(Criterion svpaCriterion, Criterion movCriterion);

	void printSvpaList(List<Svpa> svpas, Usuario usuario) throws ServiceException;

	void receiveSvpaList(List<Svpa> svpas, Usuario usuario) throws ServiceException;

	void paySvpaList(List<Svpa> svpas, Usuario usuario) throws ServiceException;
	
}
