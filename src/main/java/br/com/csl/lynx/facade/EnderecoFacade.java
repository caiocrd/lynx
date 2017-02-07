package br.com.csl.lynx.facade;

import br.com.csl.lynx.model.Bairro;
import br.com.csl.lynx.model.Conjunto;
import br.com.csl.lynx.model.Endereco;
import br.com.csl.lynx.model.Logradouro;
import br.com.csl.lynx.model.LogradouroBairro;
import br.com.csl.utils.data.DataService;

public interface EnderecoFacade extends DataService<Endereco> {

	DataService<Bairro> getBairroService();

	DataService<Logradouro> getLogradouroService();

	DataService<Conjunto> getConjuntoService();
	
	DataService<LogradouroBairro> getLogBairroService();
	
}
