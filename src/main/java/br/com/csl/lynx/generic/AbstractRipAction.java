package br.com.csl.lynx.generic;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.bean.ManagedProperty;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.api.RipAction;
import br.com.csl.lynx.handler.FotoHandler;
import br.com.csl.lynx.handler.MovementHandler;
import br.com.csl.lynx.utils.StringToMD5;

public abstract class AbstractRipAction extends AbstractRipPrint implements RipAction {

	private static final long serialVersionUID = 1L;
	
	protected String obs;

	@ManagedProperty("#{movementHandler}")
	protected MovementHandler movementHandler;
	
	@ManagedProperty("#{fotoHandler}")
	protected FotoHandler fotoHandler;
	
	@Override
	public void clear() {
		super.clear();
		
		clearAction();
	}
	
	public void clearAction() {
		fotoHandler.clear();
	}

	public void upload(FileUploadEvent event) throws IOException {
		String path = fotoHandler.newMovementFolder(rip);
		
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

	public void setMovementHandler(MovementHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setFotoHandler(FotoHandler fotoHandler) {
		this.fotoHandler = fotoHandler;
	}
	
	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
}
