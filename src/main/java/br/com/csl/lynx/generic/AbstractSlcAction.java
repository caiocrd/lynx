package br.com.csl.lynx.generic;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.bean.ManagedProperty;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.api.SlcAction;
import br.com.csl.lynx.handler.MovementSlcHandler;
import br.com.csl.lynx.handler.SlcFotoHandler;
import br.com.csl.lynx.utils.StringToMD5;

public abstract class AbstractSlcAction extends AbstractSlcPrint implements SlcAction {

	private static final long serialVersionUID = 1L;
	
	protected String obs;

	@ManagedProperty("#{movementSlcHandler}")
	protected MovementSlcHandler movementHandler;
	
	@ManagedProperty("#{slcFotoHandler}")
	protected SlcFotoHandler fotoHandler;
	
	@Override
	public void clear() {
		super.clear();
		
		clearAction();
	}
	
	public void clearAction() {
		fotoHandler.clear();
	}

	public void upload(FileUploadEvent event) throws IOException {
		String path = fotoHandler.newMovementFolder(slc);
		
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

	public void setMovementHandler(MovementSlcHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setFotoHandler(SlcFotoHandler fotoHandler) {
		this.fotoHandler = fotoHandler;
	}
	
	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
}
