package br.com.csl.lynx.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import br.com.csl.lynx.model.CostReport;
import br.com.csl.lynx.model.Report;
import br.com.csl.lynx.model.Rip;

@Component
public class ReportHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	public synchronized StreamedContent getRipReport(List<Rip> rips) throws JRException, IOException {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(rips);
		
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/jasper/receiveRip.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap<String, Object>(), beanCollectionDataSource);  
	    ByteArrayInputStream stream = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));

	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	    return new DefaultStreamedContent(stream, "application/pdf", "rips-"+  sdf.format(now.getTime())  +".pdf"); 
	}	
	
	public synchronized StreamedContent getSimpleRipReport(List<Rip> rips) throws JRException, IOException {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(rips);
		
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/jasper/rip.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap<String, Object>(), beanCollectionDataSource);  
	    ByteArrayInputStream stream = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));

	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	    return new DefaultStreamedContent(stream, "application/pdf", "rips-"+  sdf.format(now.getTime())  +".pdf"); 
	}
	
	public synchronized StreamedContent getStatisticReport(Report report) throws JRException, IOException {
		List<Report> reports = new ArrayList<>();
		
		reports.add(report);
		
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(reports);
		
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/jasper/relatorio.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap<String, Object>(), beanCollectionDataSource);
	    ByteArrayInputStream stream = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));

	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	    return new DefaultStreamedContent(stream, "application/pdf", "report-"+  sdf.format(now.getTime())  +".pdf"); 
	}
	
	public synchronized StreamedContent getCostReport(CostReport report) throws JRException, IOException {
		List<CostReport> costReport = new ArrayList<>();
		
		costReport.add(report);
		
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(costReport);
		
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/jasper/cost.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap<String, Object>(), beanCollectionDataSource);
	    ByteArrayInputStream stream = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));

	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	    return new DefaultStreamedContent(stream, "application/pdf", "custo-"+  sdf.format(now.getTime())  +".pdf"); 
	}
}
