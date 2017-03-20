package br.com.csl.lynx.generic;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.bean.ManagedProperty;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.api.SlpAction;
import br.com.csl.lynx.handler.MovementSlpHandler;
import br.com.csl.lynx.handler.SlpFotoHandler;
import br.com.csl.lynx.utils.StringToMD5;

public abstract class AbstractSlpAction extends AbstractSlpPrint implements SlpAction {

	private static final long serialVersionUID = 1L;
	
	protected String obs;

	@ManagedProperty("#{movementSlpHandler}")
	protected MovementSlpHandler movementHandler;
	
	@ManagedProperty("#{slpFotoHandler}")
	protected SlpFotoHandler fotoHandler;
	
	@Override
	public void clear() {
		super.clear();
		
		clearAction();
	}
	
	public void clearAction() {
		fotoHandler.clear();
	}

	public void upload(FileUploadEvent event) throws IOException {
		String path = fotoHandler.newMovementFolder(slp);
		
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

	public void setMovementHandler(MovementSlpHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setFotoHandler(SlpFotoHandler fotoHandler) {
		this.fotoHandler = fotoHandler;
	}
	
	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
}
