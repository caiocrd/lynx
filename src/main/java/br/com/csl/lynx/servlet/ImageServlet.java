package br.com.csl.lynx.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.csl.utils.controller.CommonController;

@ManagedBean
@ApplicationScoped
public class ImageServlet extends CommonController {
	
	private static final long serialVersionUID = 1L;
	
	public final static String IMG_DIR = "/var/lynx/"; 
	
    public StreamedContent getImage() throws IOException {
    	StreamedContent result = null;
    	
        if (getFacesContext().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else {
            String path = getExternalContext().getRequestParameterMap().get("path");
            
    		File arquivo = new File(IMG_DIR + path);
            result = new DefaultStreamedContent(new FileInputStream(arquivo));
        }
        
        return result;
    }

}
