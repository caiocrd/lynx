package br.com.csl.lynx.model;

import java.io.Serializable;

import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.RipStatus;

public class StatusReport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private RipStatus status;

	private Integer movOpen;
	private Integer movCanceled;
	private Integer movReceived;
	private Integer movExecuted;
	private Integer movEvaluated;
	private Integer movAdequated;
	private Integer movFeedback;
	private Integer movReversed;
	private Integer movClosed;
	private Integer movPayed;

	private Integer total;
	private Integer expired;
	
	public StatusReport(RipStatus status) {
		
		this.status = status;
		
		movOpen = 0;
		movCanceled = 0;
		movReceived = 0;
		movExecuted = 0;
		movEvaluated = 0;
		movAdequated = 0;
		movFeedback = 0;
		movReversed = 0;
		movClosed = 0;
		movPayed = 0;
		
		total = 0;
		expired = 0;
	}

	public RipStatus getStatus() {
		return status;
	}

	public void setStatus(RipStatus status) {
		this.status = status;
	}

	public Integer getMovOpen() {
		return movOpen;
	}

	public void setMovOpen(Integer movOpen) {
		this.movOpen = movOpen;
	}
	
	public Integer getMovCanceled() {
		return movCanceled;
	}

	public void setMovCanceled(Integer movCanceled) {
		this.movCanceled = movCanceled;
	}

	public Integer getMovReceived() {
		return movReceived;
	}

	public void setMovReceived(Integer movReceived) {
		this.movReceived = movReceived;
	}

	public Integer getMovExecuted() {
		return movExecuted;
	}

	public void setMovExecuted(Integer movExecuted) {
		this.movExecuted = movExecuted;
	}

	public Integer getMovEvaluated() {
		return movEvaluated;
	}

	public void setMovEvaluated(Integer movEvaluated) {
		this.movEvaluated = movEvaluated;
	}

	public Integer getMovAdequated() {
		return movAdequated;
	}

	public void setMovAdequated(Integer movAdequated) {
		this.movAdequated = movAdequated;
	}

	public Integer getMovFeedback() {
		return movFeedback;
	}

	public void setMovFeedback(Integer movFeedback) {
		this.movFeedback = movFeedback;
	}

	public Integer getMovReversed() {
		return movReversed;
	}

	public void setMovReversed(Integer movReversed) {
		this.movReversed = movReversed;
	}

	public Integer getMovClosed() {
		return movClosed;
	}

	public void setMovClosed(Integer movClosed) {
		this.movClosed = movClosed;
	}

	public Integer getMovPayed() {
		return movPayed;
	}

	public void setMovPayed(Integer movPayed) {
		this.movPayed = movPayed;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getExpired() {
		return expired;
	}

	public void setExpired(Integer expired) {
		this.expired = expired;
	}
	
	public void increment(Movement movement) {
		switch (movement) {
			case OPEN:
				movOpen++;
				break;
			case CANCEL:
				movCanceled++;
				break;
			case RECEIVE:
				movReceived++;
				break;
			case EXECUTE:
				movExecuted++;
				break;
			case EVALUATE:
				movEvaluated++;
				break;
			case ADEQUATE:
				movAdequated++;
				break;
			case NEGATIVE_FEEDBACK:
				movFeedback++;
				break;
			case REVERSE:
				movReversed++;
				break;
			case CLOSE:
				movClosed++;
				break;
			case PAY:
				movPayed++;
				break;				
			default:
				break;
		}
	}
	
	public void incrementTotal() {
		total++;
	}
	
	public void incrementExpired() {
		expired++;
	}
	
	public Boolean emptyCheck() {
		if (total == 0)
			return true;
		return false;
	}
}
