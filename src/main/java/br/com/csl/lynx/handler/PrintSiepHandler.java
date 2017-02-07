package br.com.csl.lynx.handler;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.exception.PrintSiepException;
import br.com.csl.lynx.facade.SiepFacade;
import br.com.csl.lynx.model.CostReport;
import br.com.csl.lynx.model.Report;
import br.com.csl.lynx.model.Siep;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class PrintSiepHandler extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{movementSiepHandler}")
	private MovementSiepHandler movementHandler;
	
	@ManagedProperty("#{siepReportHandler}")
	private SiepReportHandler reportHandler;
	
	@ManagedProperty("#{siepFacade}")
	private SiepFacade siepFacade;
	
	public StreamedContent print(List<Siep> sieps) throws PrintSiepException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSiepReport(sieps);
			
			siepFacade.printSiepList(sieps, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintSiepException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printSimple(List<Siep> sieps) throws PrintSiepException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSimpleSiepReport(sieps);
			
			siepFacade.printSiepList(sieps, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintSiepException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printReport(Report report) throws PrintSiepException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getStatisticReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintSiepException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printCostReport(CostReport report) throws PrintSiepException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getCostReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintSiepException(e);
		} 
		return pdfFile;
	}

	public void setMovementHandler(MovementSiepHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setReportHandler(SiepReportHandler reportHandler) {
		this.reportHandler = reportHandler;
	}
	
	public void setSiepFacade(SiepFacade siepFacade) {
		this.siepFacade = siepFacade;
	}

}