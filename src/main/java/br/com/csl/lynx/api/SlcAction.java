package br.com.csl.lynx.api;

import java.io.IOException;
import java.io.Serializable;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.handler.MovementSlcHandler;
import br.com.csl.lynx.handler.SlcFotoHandler;

public interface SlcAction extends Serializable {

	void clear();
	void clearAction();
	
	void upload(FileUploadEvent event) throws IOException;
	
	void setMovementHandler(MovementSlcHandler movementHandler);
	
	void setFotoHandler(SlcFotoHandler fotoHandler);

	String getObs();
	
	void setObs(String obs);
}
