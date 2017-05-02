package br.com.csl.lynx.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.facade.EnderecoFacade;
import br.com.csl.lynx.model.Bairro;
import br.com.csl.lynx.model.Conjunto;
import br.com.csl.lynx.model.Endereco;
import br.com.csl.lynx.model.Logradouro;
import br.com.csl.lynx.model.LogradouroBairro;
import br.com.csl.lynx.support.Zona;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;

@ViewScoped
@ManagedBean
public class EnderecoHandler extends CommonController {

	private static final long serialVersionUID = -1394971602498597465L;

	@ManagedProperty(value = "#{enderecoFacade}")
	private EnderecoFacade enderecoFacade;
	
	@ManagedProperty(value = "#{enderecoDataModel}")
	private DataModel<Endereco> enderecoDataModel;

	private String logradouroNome;
	private String bairroNome;
	private String conjuntoNome;
	private Zona zonaNome;
	private String cep;

	private Endereco endereco;
	private Logradouro logradouro;
	private Bairro bairro;
	private Conjunto conjunto;

	private List<Bairro> specificBairros;
	private List<Bairro> bairros;
	private List<Conjunto> conjuntos;
	
	@PostConstruct
	public void clear() {
		specificBairros = new ArrayList<Bairro>();
		bairroNome = "";
		conjuntoNome = "";
		logradouroNome = "";
		logradouro = null;
		bairro = null;
		conjunto = null;
		zonaNome = null;

		resetLists();
		
		clearEndereco();
	}

	public void resetLists() {
		bairros = enderecoFacade.getBairroService().list();
		conjuntos = enderecoFacade.getConjuntoService().list();
	}
	
	/**
	 * Endereco Provider
	 */

	public Endereco providedEndereco() {
		if (endereco == null) {
			endereco = new Endereco();
			endereco.setBairro(bairro);
			endereco.setLogradouro(logradouro);
			endereco.setCep(cep);
			endereco.setConjunto(conjunto);

			if (endereco.getLogradouro() != null && endereco.getBairro() != null) {
				List<Bairro> bairros = new ArrayList<Bairro>();
				
				for (LogradouroBairro aux : enderecoFacade.getLogBairroService().list("logradouro", endereco.getLogradouro(), null)) {
					bairros.add(aux.getBairro());
				}
				
				if (!bairros.contains(endereco.getBairro())) {
					LogradouroBairro logradouroBairro = new LogradouroBairro();
					logradouroBairro.setBairro(endereco.getBairro());
					logradouroBairro.setLogradouro(endereco.getLogradouro());
					enderecoFacade.getLogBairroService().save(logradouroBairro);
					
					logradouroBairro = null;
				}
			}
			
			endereco = enderecoFacade.save(endereco);
			selectEndereco();
		} else if (endereco != null && conjunto != null	&& endereco.getConjunto() == null) {
			endereco.setConjunto(conjunto);
			endereco = enderecoFacade.save(endereco);
		}

		return endereco;
	}

	/**
	 * Endereco Implementation.
	 */
	
	public void loadEnderecos() {
		enderecoDataModel.removeRestraints();
		
		if (bairro != null) 
			enderecoDataModel.addRestraint("fk.bairro", bairro);
		if (logradouro != null) 
			enderecoDataModel.addRestraint("fk.logradouro", logradouro);
		if (zonaNome != null) 
			enderecoDataModel.addRestraint("bairro.zona", zonaNome);
		if (conjunto != null) 
			enderecoDataModel.addRestraint("conjunto", conjunto);
	}

	public void checkEndereco() {
		if (endereco != null
				&& !endereco.getBairro().getZona().equals(zonaNome)) {
			clearEndereco();
		}
		if (endereco != null && !endereco.getBairro().equals(bairro)) {
			clearEndereco();
		}
		if (endereco != null && !endereco.getLogradouro().equals(logradouro)) {
			clearEndereco();
		}
		if (endereco != null && endereco.getConjunto() != null
				&& !endereco.getConjunto().equals(conjunto)) {
			clearEndereco();
		}

	}

	public void clearEndereco() {
		endereco = null;
		cep = "";
	}

	public void searchCep() {
		if (!cep.isEmpty()) {
			endereco = enderecoFacade.find("cep", cep);
			if (endereco != null) {
				selectEndereco();
			} 
		}
	}

	public void selectEndereco() {
		logradouro = endereco.getLogradouro();
		bairro = endereco.getBairro();
		conjunto = endereco.getConjunto();
		if (logradouro != null) {
			logradouroNome = logradouro.getNome();
		}
		if (bairro != null) {
			zonaNome = bairro.getZona();
		}
		if (bairro != null) {
			bairroNome = bairro.getNome();
		}
		if (conjunto != null) {
			conjuntoNome = conjunto.getNome();
		}
		if (endereco.getCep() != null) {
			cep = endereco.getCep();
		}
		loadBairros();
	}

	/**
	 * Bairro Implementation.
	 */

	public void selectZona() {
		loadBairros();

		if (!specificBairros.isEmpty() && specificBairros.size() == 1) {
			bairro = specificBairros.get(0);
			bairroNome = bairro.getNome();
			selectBairro();
			return;
		}

	}

	public void loadBairros() {
		if (zonaNome != null) {
			bairros = enderecoFacade.getBairroService().list("zona", zonaNome,
					null);

			if (bairro != null && !bairros.contains(bairro)) {
				bairroNome = "";
				bairro = null;
				checkEndereco();
			}

		} else {
			resetLists();
		}

		if (logradouro != null) {
			specificBairros.clear();
			
			for (LogradouroBairro aux : enderecoFacade.getLogBairroService().list(Restrictions.eq("logradouro", logradouro)))
				specificBairros.add(aux.getBairro());

			specificBairros.retainAll(bairros);
			if (!specificBairros.isEmpty()) {
				bairros.removeAll(specificBairros);
			}
		}

		loadConjuntos();
	}

	public void selectBairro() {
		if (bairroNome != null && !bairroNome.isEmpty()) {
			if ((bairro == null)
					|| (bairro != null && !bairro.getNome().equals(bairroNome))) {
				bairro = enderecoFacade.getBairroService().find("nome",
						bairroNome);
			}
			if (bairro != null) {
				checkEndereco();
				if (logradouro != null && endereco == null) {
					Conjunction conjunction = new Conjunction();
					conjunction.add(Restrictions.eq("fk.bairro", bairro)).add(
							Restrictions.eq("fk.logradouro", logradouro));
					if (conjunto != null) {
						conjunction.add(Restrictions.eq("conjunto", conjunto));
					}
					List<Endereco> enderecos = enderecoFacade.list(conjunction);
					if (enderecos != null && enderecos.size() == 1) {
						endereco = enderecos.get(0);
						selectEndereco();
						return;
					}
				}
				zonaNome = bairro.getZona();
			}
			loadBairros();
		}

	}

	/**
	 * Conjunto Implementation.
	 */

	public List<String> searchConjuntos(String query) {
		List<String> results = new ArrayList<String>();

		if (query != null && !query.isEmpty()) {
			if (conjuntos != null && !conjuntos.isEmpty()) {
				for (Conjunto aux : conjuntos) {
					if (aux.getNome().contains(query)) {
						results.add(aux.getNome());
					}
				}
			}
		}
		return results;
	}

	public void loadConjuntos() {
		if (bairro != null) {
			conjuntos = enderecoFacade.getConjuntoService().list("bairro",
					bairro, null);
		} else {
			if (zonaNome != null && zonaNome != null) {
				conjuntos = enderecoFacade.getConjuntoService().listAliased(
						"bairro.zona", zonaNome, null);
			} else {
				conjuntos = enderecoFacade.getConjuntoService().list();
			}
		}

		if (conjunto != null && !conjuntos.contains(conjunto)) {
			conjuntoNome = "";
			conjunto = null;
		}
	}

	public void selectConjunto() {
		if (conjuntoNome != null && !conjuntoNome.isEmpty()) {
			conjunto = enderecoFacade.getConjuntoService().find("nome",
					conjuntoNome);
			if (conjunto != null) {
				checkEndereco();
				if (endereco == null) {
					bairro = conjunto.getBairro();
					bairroNome = bairro.getNome();
					selectBairro();

				}
			}
		}
	}

	/**
	 * Logradouro Implementation.
	 */

	public List<String> loadLogradouros(String query) {
		List<String> results = new ArrayList<String>();

		if (query != null && !query.isEmpty() && query.length() > 4) {
			
				for (Logradouro aux : enderecoFacade.getLogradouroService()
						.list(0, 15, Restrictions.ilike("nome", query,
								MatchMode.ANYWHERE), Order.asc("nome"))) {
					results.add(aux.getNome());
				}
			}

		return results;
	}

	public void selectLogradouro() {
		if (!logradouroNome.isEmpty()) {
			if ((logradouro == null)
					|| (logradouro != null && !logradouro.getNome().equals(
							logradouroNome))) {
				logradouro = enderecoFacade.getLogradouroService().find("nome",
						logradouroNome);
			}

			if (logradouro != null) {
				checkEndereco();
				
				List<Endereco> logEnderecos = enderecoFacade.list("fk.logradouro", logradouro, null);
				
				List<Bairro> bairros = new ArrayList<Bairro>();
				
				for (LogradouroBairro aux : enderecoFacade.getLogBairroService().list("logradouro", logradouro, null)) {
					bairros.add(aux.getBairro());
				}

				if ((bairro == null)
						|| (bairro != null && bairros.contains(
								bairro))) {
					if (!logEnderecos.isEmpty()) {
						if (logEnderecos.size() == 1) {
							endereco = logEnderecos.get(0);
							selectEndereco();
						} else if (bairros.size() == 1) {
							bairro = bairros.get(0);
							bairroNome = bairro.getNome();
							selectBairro();
							return;
						} else if (bairro != null) {
							Criterion criterion = Restrictions.and(Restrictions.eq("fk.bairro", bairro), Restrictions.eq("fk.logradouro", logradouro));
							List<Endereco> enderecos = enderecoFacade.list(criterion);
							if (enderecos != null && enderecos.size() == 1) {
								endereco = enderecos.get(0);
								selectEndereco();
								return;
							}
						} else if (zonaNome != null) {
							Criterion criterion = Restrictions.and(Restrictions.eq("bairro.zona", zonaNome), Restrictions.eq("fk.logradouro", logradouro));
							List<Endereco> enderecos = enderecoFacade.listAliased(criterion);
							if (enderecos != null && enderecos.size() == 1) {
								endereco = enderecos.get(0);
								selectEndereco();
								return;
							}
						}
					}
				}
			}
			loadBairros();
		}

	}
	
	public Zona[] getZonas() {
		return Zona.values();
	}
	
	public EnderecoFacade getEnderecoFacade() {
		return enderecoFacade;
	}

	public void setEnderecoFacade(EnderecoFacade enderecoFacade) {
		this.enderecoFacade = enderecoFacade;
	}

	public DataModel<Endereco> getEnderecoDataModel() {
		return enderecoDataModel;
	}

	public void setEnderecoDataModel(DataModel<Endereco> enderecoDataModel) {
		this.enderecoDataModel = enderecoDataModel;
	}

	public String getLogradouroNome() {
		return logradouroNome;
	}

	public void setLogradouroNome(String logradouroNome) {
		this.logradouroNome = logradouroNome;
	}

	public String getBairroNome() {
		return bairroNome;
	}

	public void setBairroNome(String bairroNome) {
		this.bairroNome = bairroNome;
	}

	public String getConjuntoNome() {
		return conjuntoNome;
	}

	public void setConjuntoNome(String conjuntoNome) {
		this.conjuntoNome = conjuntoNome;
	}

	public Zona getZonaNome() {
		return zonaNome;
	}

	public void setZonaNome(Zona zonaNome) {
		this.zonaNome = zonaNome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Conjunto getConjunto() {
		return conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}

	public List<Bairro> getSpecificBairros() {
		return specificBairros;
	}

	public void setSpecificBairros(List<Bairro> specificBairros) {
		this.specificBairros = specificBairros;
	}

	public List<Bairro> getBairros() {
		if (bairros == null) {
			bairros = enderecoFacade.getBairroService().list();
		}
		return bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	public List<Conjunto> getConjuntos() {
		if (conjuntos == null) {
			conjuntos = enderecoFacade.getConjuntoService().list();
		}
		return conjuntos;
	}

	public void setConjuntos(List<Conjunto> conjuntos) {
		this.conjuntos = conjuntos;
	}

}
