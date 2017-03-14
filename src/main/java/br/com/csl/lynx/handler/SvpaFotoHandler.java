package br.com.csl.lynx.handler;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.MovimentacaoSvpa;
import br.com.csl.lynx.model.Svpa;

@ManagedBean
@ViewScoped
public class SvpaFotoHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final static String IMG_DIR = "/var/lynx/";
	
	private Integer movFolder;
	private String movFullPath;
	
	private List<String> existingFotos;

	@ManagedProperty("#{movementSvpaHandler}")
	private MovementSvpaHandler movementHandler;

	public void clear() {
		movFolder = null;
		movFullPath = null;
	}
	
	public void load() {
		if (movementHandler.getMovimentacaoSvpa() == null || movementHandler.getMovimentacaoSvpa().getPasta() == null)
			return;
		
		List<String> files = new ArrayList<String>();
		
		for (String aux : Arrays.asList((new File(IMG_DIR + getExistingMovFolder())).list())) {
			files.add(getExistingMovFolder() + aux);
		}
		
		existingFotos = files;
	}
	
	public String getExistingMovFolder() {
		if (movementHandler.getMovimentacaoSvpa() == null || movementHandler.getMovimentacaoSvpa().getPasta() == null)
			return "";
		
		return movementHandler.getMovimentacaoSvpa().getSvpa().getId().toString() + "/" + movementHandler.getMovimentacaoSvpa().getPasta().toString() + "/";			
	}
	
	public List<String> getFotos() {
		if (movFolder == null || movFullPath == null)
			return null;
		
		List<String> files = new ArrayList<String>();
		
		for (String aux : Arrays.asList((new File(IMG_DIR + movFullPath)).list())) {
			files.add(movFullPath + aux);
		}
		
		return files;
	}
	
	public String newMovementFolder(Svpa svpa) throws IOException {
		if (movFolder == null) {
			File movementFile = getMovFolderFile(svpa);
			
			if (movementFile.exists()) {
				deleteDirectory(movementFile);
			}
			
			movementFile.mkdirs();
			
			movFullPath = getSvpaFolder(svpa) + movFolder.toString() + "/";
		}
		
		return IMG_DIR + movFullPath;
	}
	
	private Integer checkMovFolder(Svpa svpa) {
		if (movFolder == null) {
			List<MovimentacaoSvpa> mov = movementHandler.getMovements(svpa, Restrictions.isNotNull("pasta"), Order.desc("pasta"));

			if (mov != null && !mov.isEmpty()) { 
				movFolder = mov.get(0).getPasta() + 1;
			} else {
				movFolder = 1;
			}
		}
		return movFolder;
	}
	
	private File getMovFolderFile(Svpa svpa) {
		return new File(IMG_DIR + getSvpaFolder(svpa), String.valueOf(checkMovFolder(svpa)));
	}
	
	public String getSvpaFolder(Svpa svpa) {
		return svpa.getId().toString() + "/";
	}
	
    private void deleteDirectory(File file) throws IOException {
    	if (file.isDirectory()) {
    		if (file.list().length == 0) {
    		   file.delete();     
    		} else {
        	   String files[] = file.list();
 
        	   for (String aux : files) {
        	      File fileDelete = new File(file, aux);
        	      deleteDirectory(fileDelete);
        	   }
 
        	   if (file.list().length == 0){
           	     file.delete();
        	   }
    		}
    	} else {
    		file.delete();
    	}
    }

	public Integer getMovFolder() {
		return movFolder;
	}
	
	public String getMovFullPath() {
		return movFullPath;
	}
	
	public List<String> getExistingFotos() {
		return existingFotos;
	}

	public void setExistingFotos(List<String> existingFotos) {
		this.existingFotos = existingFotos;
	}

	public void setMovementHandler(MovementSvpaHandler movementHandler) {
		this.movementHandler = movementHandler;
	}
}
