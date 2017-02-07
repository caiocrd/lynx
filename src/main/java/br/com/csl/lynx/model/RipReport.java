package br.com.csl.lynx.model;

import java.io.Serializable;
import java.util.Date;

import br.com.csl.lynx.utils.CalendarUtil;

public class RipReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String previsao;
	private String lastOccurrence;

	private String opened;
	private String canceled;
	private String received;
	private String executed;
	private String evaluated;
	private String adequated;
	private String reversed;
	private String closed;
	private String payed;

	private Integer evaluations;
	private Integer adequations;
	private Integer reversions;
	private Integer occurrences;

	public RipReport(Long id, Date previsao) {
		this.id = id;
		this.previsao = CalendarUtil.dateToString(previsao);
		
		evaluations = 0;
		adequations = 0;
		reversions = 0;
		occurrences = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrevisao() {
		return previsao;
	}

	public void setPrevisao(String previsao) {
		this.previsao = previsao;
	}

	public String getLastOccurrence() {
		return lastOccurrence;
	}

	public void setLastOccurrence(String lastOccurRence) {
		this.lastOccurrence = lastOccurRence;
	}

	public String getOpened() {
		return opened;
	}

	public void setOpened(String opened) {
		this.opened = opened;
	}

	public String getCanceled() {
		return canceled;
	}

	public void setCanceled(String canceled) {
		this.canceled = canceled;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getExecuted() {
		return executed;
	}

	public void setExecuted(String executed) {
		this.executed = executed;
	}

	public String getEvaluated() {
		return evaluated;
	}

	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}

	public String getAdequated() {
		return adequated;
	}

	public void setAdequated(String adequated) {
		this.adequated = adequated;
	}

	public String getReversed() {
		return reversed;
	}

	public void setReversed(String reversed) {
		this.reversed = reversed;
	}

	public String getClosed() {
		return closed;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	public String getPayed() {
		return payed;
	}

	public void setPayed(String payed) {
		this.payed = payed;
	}

	public Integer getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Integer evaluations) {
		this.evaluations = evaluations;
	}

	public Integer getAdequations() {
		return adequations;
	}

	public void setAdequations(Integer adequations) {
		this.adequations = adequations;
	}

	public Integer getReversions() {
		return reversions;
	}

	public void setReversions(Integer reversions) {
		this.reversions = reversions;
	}

	public Integer getOccurrences() {
		return occurrences;
	}

	public void setOccurences(Integer occurrences) {
		this.occurrences = occurrences;
	}

	public void setDates(Movimentacao movimentacao) {
		switch (movimentacao.getMovimento()) {
			case OPEN:
				opened = CalendarUtil.dateToString(movimentacao.getData().getTime());
				break;
			case CANCEL:
				canceled = CalendarUtil.dateToString(movimentacao.getData().getTime());
				break;
			case RECEIVE:
				received = CalendarUtil.dateToString(movimentacao.getData().getTime());
				break;
			case EXECUTE:
				executed = CalendarUtil.dateToString(movimentacao.getData().getTime());
				break;
			case EVALUATE:
				evaluated = CalendarUtil.dateToString(movimentacao.getData().getTime());
				evaluations++;
				break;
			case ADEQUATE:
				adequated = CalendarUtil.dateToString(movimentacao.getData().getTime());
				adequations++;
				break;
			case REVERSE:
				reversed = CalendarUtil.dateToString(movimentacao.getData().getTime());
				reversions++;
				break;
			case CLOSE:
				closed = CalendarUtil.dateToString(movimentacao.getData().getTime());
				break;
			case PAY:
				payed = CalendarUtil.dateToString(movimentacao.getData().getTime());
				break;				
			default:
				break;
		}
	}
}
