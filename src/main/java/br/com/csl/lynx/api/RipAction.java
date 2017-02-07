package br.com.csl.lynx.api;

import java.io.IOException;
import java.io.Serializable;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.handler.FotoHandler;
import br.com.csl.lynx.handler.MovementHandler;

public interface RipAction extends Serializable {

	void clear();
	void clearAction();
	
	void upload(FileUploadEvent event) throws IOException;
	
	void setMovementHandler(MovementHandler movementHandler);
	
	void setFotoHandler(FotoHandler fotoHandler);

	String getObs();
	
	void setObs(String obs);
}
