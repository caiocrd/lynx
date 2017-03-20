package br.com.csl.lynx.api;

import java.io.IOException;
import java.io.Serializable;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.handler.MovementSlpHandler;
import br.com.csl.lynx.handler.SlpFotoHandler;

public interface SlpAction extends Serializable {

	void clear();
	void clearAction();
	
	void upload(FileUploadEvent event) throws IOException;
	
	void setMovementHandler(MovementSlpHandler movementHandler);
	
	void setFotoHandler(SlpFotoHandler fotoHandler);

	String getObs();
	
	void setObs(String obs);
}
