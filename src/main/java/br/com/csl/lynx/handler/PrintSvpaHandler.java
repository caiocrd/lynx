package br.com.csl.lynx.handler;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.exception.PrintSvpaException;
import br.com.csl.lynx.facade.SvpaFacade;
import br.com.csl.lynx.model.CostReport;
import br.com.csl.lynx.model.Report;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class PrintSvpaHandler extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{movementSvpaHandler}")
	private MovementSvpaHandler movementHandler;
	
	@ManagedProperty("#{svpaReportHandler}")
	private SvpaReportHandler reportHandler;
	
	@ManagedProperty("#{svpaFacade}")
	private SvpaFacade svpaFacade;
	
	public StreamedContent print(List<Svpa> svpas) throws PrintSvpaException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSvpaReport(svpas);
			
			svpaFacade.printSvpaList(svpas, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintSvpaException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printSimple(List<Svpa> svpas) throws PrintSvpaException {
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getSimpleSvpaReport(svpas);
			
			svpaFacade.printSvpaList(svpas, getLoggedUser());
			
		} catch (JRException | IOException | ServiceException e){
			throw new PrintSvpaException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printReport(Report report) throws PrintSvpaException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getStatisticReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintSvpaException(e);
		} 
		return pdfFile;
	}
	
	public StreamedContent printCostReport(CostReport report) throws PrintSvpaException {		
		StreamedContent pdfFile = null;
		
		try {
			pdfFile = reportHandler.getCostReport(report);
			
		} catch (JRException | IOException e){
			throw new PrintSvpaException(e);
		} 
		return pdfFile;
	}

	public void setMovementHandler(MovementSvpaHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setReportHandler(SvpaReportHandler reportHandler) {
		this.reportHandler = reportHandler;
	}
	
	public void setSvpaFacade(SvpaFacade svpaFacade) {
		this.svpaFacade = svpaFacade;
	}

}