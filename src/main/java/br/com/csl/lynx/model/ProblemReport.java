package br.com.csl.lynx.model;

import java.io.Serializable;

import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.Problem;
import br.com.csl.lynx.support.RipStatus;

public class ProblemReport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Problem problem;

	private Integer movOpened;
	private Integer movCanceled;
	private Integer movReceived;
	private Integer movExecuted;
	private Integer movEvaluated;
	private Integer movAdequated;
	private Integer movFeedback;
	private Integer movReversed;
	private Integer movClosed;
	private Integer movPayed;

	private Integer stOpen;
	private Integer stExecuting;
	private Integer stEvaluating;
	private Integer stAdequating;
	private Integer stEnding;
	private Integer stReversing;
	private Integer stReversed;
	private Integer stCanceled;
	private Integer stFeedback;
	private Integer stClosed;
	private Integer stPayed;

	private Integer total;
	private Integer expired;
	
	public ProblemReport(Problem problem) {
		this.problem = problem;
		
		movOpened = 0;
		movCanceled = 0;
		movReceived = 0;
		movExecuted = 0;
		movEvaluated = 0;
		movAdequated = 0;
		movFeedback = 0;
		movReversed = 0;
		movClosed = 0;
		movPayed = 0;
		
		stOpen = 0;
		stExecuting = 0;
		stEvaluating = 0;
		stAdequating = 0;
		stEnding = 0;
		stReversing = 0;
		stReversed = 0;
		stCanceled = 0;
		stFeedback = 0;
		stClosed = 0;
		stPayed = 0;
		
		total = 0;
		expired = 0;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Integer getMovOpened() {
		return movOpened;
	}

	public void setMovOpened(Integer movOpened) {
		this.movOpened = movOpened;
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

	public Integer getStOpen() {
		return stOpen;
	}

	public void setStOpen(Integer stOpen) {
		this.stOpen = stOpen;
	}

	public Integer getStExecuting() {
		return stExecuting;
	}

	public void setStExecuting(Integer stExecuting) {
		this.stExecuting = stExecuting;
	}

	public Integer getStEvaluating() {
		return stEvaluating;
	}

	public void setStEvaluating(Integer stEvaluating) {
		this.stEvaluating = stEvaluating;
	}

	public Integer getStAdequating() {
		return stAdequating;
	}

	public void setStAdequating(Integer stAdequating) {
		this.stAdequating = stAdequating;
	}

	public Integer getStEnding() {
		return stEnding;
	}

	public void setStEnding(Integer stEnding) {
		this.stEnding = stEnding;
	}

	public Integer getStReversing() {
		return stReversing;
	}

	public void setStReversing(Integer stReversing) {
		this.stReversing = stReversing;
	}

	public Integer getStReversed() {
		return stReversed;
	}

	public void setStReversed(Integer stReversed) {
		this.stReversed = stReversed;
	}

	public Integer getStCanceled() {
		return stCanceled;
	}

	public void setStCanceled(Integer stCanceled) {
		this.stCanceled = stCanceled;
	}

	public Integer getStFeedback() {
		return stFeedback;
	}

	public void setStFeedback(Integer stFeedback) {
		this.stFeedback = stFeedback;
	}

	public Integer getStClosed() {
		return stClosed;
	}

	public void setStClosed(Integer stClosed) {
		this.stClosed = stClosed;
	}

	public Integer getStPayed() {
		return stPayed;
	}

	public void setStPayed(Integer stPayed) {
		this.stPayed = stPayed;
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
				movOpened++;
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
	
	public void increment(RipStatus ripStatus) {
		switch (ripStatus) {
			case OPEN:
				stOpen++;
				break;
			case EXECUTING:
				stExecuting++;
				break;
			case EVALUATING:
				stEvaluating++;
				break;
			case ADEQUATING:
				stAdequating++;
				break;
			case DONE:
				stEnding++;
				break;
			case REVERSING:
				stReversing++;
				break;
			case REVERSED:
				stReversed++;
				break;
			case EVALUATING_FEEDBACK:
				stFeedback++;
				break;
			case CLOSED:
				stClosed++;
				break;
			case CANCELED:
				stCanceled++;
				break;
			case PAYED:
				stPayed++;
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
