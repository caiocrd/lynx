package br.com.csl.lynx.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import br.com.csl.lynx.domain.DomainObject;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.utils.CalendarUtil;

/**
 * @author Caio Cesar
 * @version 2.0
 * Solicitacao de Vistoria de Poda de Arvores
 */
@Entity
@Table(name = "slc")
public class Slc implements DomainObject {

	private static final long serialVersionUID = -2941699076701021655L;

	private Long id;
	private String solicitante;
	private String telefone;
	//
	private RipStatus status;
	private String observacoes;
	private Calendar previsao;
	private Integer prioridade;
	private Endereco endereco;
	private List<MovimentacaoSlc> movimentacoes;
	private List<QtdServicoSlc> qtdServicos;
	private List<OcorrenciaSlc> ocorrencias;

	@PrePersist
	public void prepare() {
		Calendar ripPrevision = CalendarUtil.getToday();		
		if(this.prioridade > 0) {
			for (int i = 0; i < this.prioridade; i++) {
				ripPrevision.add(Calendar.DATE, 1);
				while (ripPrevision.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
				       ripPrevision.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					ripPrevision.add(Calendar.DATE, 1);	
				}				
			}			
			this.previsao = ripPrevision;
		} else {			
			while (ripPrevision.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| ripPrevision.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				ripPrevision.add(Calendar.DATE, 1);
			}
			this.previsao = ripPrevision;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SlcIdGenerator")
	@GenericGenerator(name = "SlcIdGenerator", strategy = "br.com.csl.lynx.generator.SlcIdGenerator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Enumerated(EnumType.STRING)
	public RipStatus getStatus() {
		return status;
	}

	public void setStatus(RipStatus status) {
		this.status = status;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Calendar getPrevisao() {
		return previsao;
	}

	public void setPrevisao(Calendar previsao) {
		this.previsao = previsao;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	@PrimaryKeyJoinColumn
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Fetch(FetchMode.SELECT)
	@BatchSize(size = 500)
	@OrderBy("id ASC")
	@OneToMany(mappedBy = "slc", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	public List<MovimentacaoSlc> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<MovimentacaoSlc> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	@Fetch(FetchMode.SELECT)
	@BatchSize(size = 500)
	@OrderBy("id ASC")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "slc_qtdservico")
	public List<QtdServicoSlc> getQtdServicos() {
		return qtdServicos;
	}

	public void setQtdServicos(List<QtdServicoSlc> qtdServicos) {
		this.qtdServicos = qtdServicos;
	}

	@Fetch(FetchMode.SELECT)
	@BatchSize(size = 500)
	@OrderBy("id ASC")
	@OneToMany(mappedBy = "slc", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<OcorrenciaSlc> getOcorrencias() {
		return ocorrencias;
	}
	
	public void setOcorrencias(List<OcorrenciaSlc> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Slc other = (Slc) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SIEP [id=" + String.valueOf(id) + ", solicitante=" + solicitante + ", status=" + status + ", observacoes="
				+ observacoes + ", prioridade=" + prioridade + "]";
	}

}
