package br.com.csl.lynx.handler;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.exception.PrintSlpException;
import br.com.csl.lynx.facade.SlpFacade;
import br.com.csl.lynx.model.CostReport;
import br.com.csl.lynx.model.Report;
import br.com.csl.lynx.model.Slp;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class PrintSlpHandler extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{movementSlpHandler}")
	private MovementSlpHandler movementHandler;
	
	@ManagedProperty("#{slpReportHandler}")
	private SlpReportHandler reportHandler;
	
	@ManagedProperty("#{slpFacade}")
	private SlpFacade slpFacade;
	
	public StreamedContent print(List<Slp> slps) throws PrintSlpException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSlpReport(slps);
			
			slpFacade.printSlpList(slps, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintSlpException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printSimple(List<Slp> slps) throws PrintSlpException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSimpleSlpReport(slps);
			
			slpFacade.printSlpList(slps, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintSlpException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printReport(Report report) throws PrintSlpException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getStatisticReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintSlpException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printCostReport(CostReport report) throws PrintSlpException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getCostReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintSlpException(e);
		} 
		return pdfFile;
	}

	public void setMovementHandler(MovementSlpHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setReportHandler(SlpReportHandler reportHandler) {
		this.reportHandler = reportHandler;
	}
	
	public void setSlpFacade(SlpFacade slpFacade) {
		this.slpFacade = slpFacade;
	}

}