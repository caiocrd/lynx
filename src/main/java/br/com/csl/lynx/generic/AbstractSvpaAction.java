package br.com.csl.lynx.generic;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.bean.ManagedProperty;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.api.SvpaAction;
import br.com.csl.lynx.handler.MovementSvpaHandler;
import br.com.csl.lynx.handler.SvpaFotoHandler;
import br.com.csl.lynx.utils.StringToMD5;

public abstract class AbstractSvpaAction extends AbstractSvpaPrint implements SvpaAction {

	private static final long serialVersionUID = 1L;
	
	protected String obs;

	@ManagedProperty("#{movementSvpaHandler}")
	protected MovementSvpaHandler movementHandler;
	
	@ManagedProperty("#{svpaFotoHandler}")
	protected SvpaFotoHandler fotoHandler;
	
	@Override
	public void clear() {
		super.clear();
		
		clearAction();
	}
	
	public void clearAction() {
		fotoHandler.clear();
	}

	public void upload(FileUploadEvent event) throws IOException {
		String path = fotoHandler.newMovementFolder(svpa);
		
		String nome = StringToMD5.md5(event.getFile().getFileName()) + ".jpg";

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path + nome);
			fos.write(event.getFile().getContents());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMovementHandler(MovementSvpaHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setFotoHandler(SvpaFotoHandler fotoHandler) {
		this.fotoHandler = fotoHandler;
	}
	
	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
}
