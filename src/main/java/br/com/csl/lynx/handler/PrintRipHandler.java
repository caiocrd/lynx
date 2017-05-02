package br.com.csl.lynx.handler;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.exception.PrintRipException;
import br.com.csl.lynx.facade.RipFacade;
import br.com.csl.lynx.model.CostReport;
import br.com.csl.lynx.model.Report;
import br.com.csl.lynx.model.Rip;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class PrintRipHandler extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{movementHandler}")
	private MovementHandler movementHandler;
	
	@ManagedProperty("#{reportHandler}")
	private ReportHandler reportHandler;
	
	@ManagedProperty("#{ripFacade}")
	private RipFacade ripFacade;
	
	public StreamedContent print(List<Rip> rips) throws PrintRipException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getRipReport(rips);
			
			ripFacade.printRipList(rips, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintRipException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printSimple(List<Rip> rips) throws PrintRipException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSimpleRipReport(rips);
			
			ripFacade.printRipList(rips, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintRipException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printReport(Report report) throws PrintRipException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getStatisticReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintRipException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printSimpleReport(Report report) throws PrintRipException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getStatisticSimpleReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintRipException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printCostReport(CostReport report) throws PrintRipException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getCostReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintRipException(e);
		} 
		return pdfFile;
	}

	public void setMovementHandler(MovementHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setReportHandler(ReportHandler reportHandler) {
		this.reportHandler = reportHandler;
	}
	
	public void setRipFacade(RipFacade ripFacade) {
		this.ripFacade = ripFacade;
	}

}