package br.com.csl.lynx.facade;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.exception.ServiceException;

public interface SiepFacade extends Serializable {

	List<Siep> getSiepWithMovements(Criterion siepCriterion, Criterion movCriterion);

	void printSiepList(List<Siep> sieps, Usuario usuario) throws ServiceException;

	void receiveSiepList(List<Siep> sieps, Usuario usuario) throws ServiceException;

	void paySiepList(List<Siep> sieps, Usuario usuario) throws ServiceException;
	
}
