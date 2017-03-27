package br.com.csl.lynx.handler;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.exception.PrintSlcException;
import br.com.csl.lynx.facade.SlcFacade;
import br.com.csl.lynx.model.CostReport;
import br.com.csl.lynx.model.Report;
import br.com.csl.lynx.model.Slc;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class PrintSlcHandler extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{movementSlcHandler}")
	private MovementSlcHandler movementHandler;
	
	@ManagedProperty("#{slcReportHandler}")
	private SlcReportHandler reportHandler;
	
	@ManagedProperty("#{slcFacade}")
	private SlcFacade slcFacade;
	
	public StreamedContent print(List<Slc> slcs) throws PrintSlcException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSlcReport(slcs);
			
			slcFacade.printSlcList(slcs, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintSlcException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printSimple(List<Slc> slcs) throws PrintSlcException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSimpleSlcReport(slcs);
			
			slcFacade.printSlcList(slcs, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintSlcException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printReport(Report report) throws PrintSlcException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getStatisticReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintSlcException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printCostReport(CostReport report) throws PrintSlcException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getCostReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintSlcException(e);
		} 
		return pdfFile;
	}

	public void setMovementHandler(MovementSlcHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setReportHandler(SlcReportHandler reportHandler) {
		this.reportHandler = reportHandler;
	}
	
	public void setSlcFacade(SlcFacade slcFacade) {
		this.slcFacade = slcFacade;
	}

}