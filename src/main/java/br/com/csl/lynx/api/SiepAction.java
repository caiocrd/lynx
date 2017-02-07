package br.com.csl.lynx.api;

import java.io.IOException;
import java.io.Serializable;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.handler.MovementSiepHandler;
import br.com.csl.lynx.handler.SiepFotoHandler;

public interface SiepAction extends Serializable {

	void clear();
	void clearAction();
	
	void upload(FileUploadEvent event) throws IOException;
	
	void setMovementHandler(MovementSiepHandler movementHandler);
	
	void setFotoHandler(SiepFotoHandler fotoHandler);

	String getObs();
	
	void setObs(String obs);
}
