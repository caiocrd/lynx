package br.com.csl.lynx.facade;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.csl.lynx.facade.EnderecoFacade;
import br.com.csl.lynx.model.Bairro;
import br.com.csl.lynx.model.Conjunto;
import br.com.csl.lynx.model.Endereco;
import br.com.csl.lynx.model.Logradouro;
import br.com.csl.lynx.model.LogradouroBairro;
import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.data.impl.AbstractDataService;
import br.com.csl.utils.exception.ServiceException;

@Service("enderecoFacade")
@Transactional(rollbackFor = ServiceException.class)
public class EnderecoFacadeImpl extends AbstractDataService<Endereco> implements EnderecoFacade {

	private static final long serialVersionUID = 7713807888019956221L;
	
	@Autowired private DataAccess<Endereco> enderecoDAO;
	
	@Autowired private DataService<Logradouro> logradouroService;
	@Autowired private DataService<Bairro> bairroService;
	@Autowired private DataService<Conjunto> conjuntoService;
	@Autowired private DataService<LogradouroBairro> logradouroBairroService;
	
	public EnderecoFacadeImpl() {
		setAliases(new HashMap<String,String>());
		getAliases().put("fk.bairro", "bairro");
		getAliases().put("fk.logradouro", "logradouro");
		getAliases().put("conjunto", "conjunto");
	}
	
	@Override
	public DataAccess<Endereco> getDAO() {
		return enderecoDAO;
	}

	@Override
	public DataService<Logradouro> getLogradouroService() {
		return logradouroService;
	}

	@Override
	public DataService<Bairro> getBairroService() {
		return bairroService;
	}

	@Override
	public DataService<Conjunto> getConjuntoService() {
		return conjuntoService;
	}
	
	@Override
	public DataService<LogradouroBairro> getLogBairroService() {
		return logradouroBairroService;
	}

}
