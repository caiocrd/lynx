package br.com.csl.lynx.api;

import java.io.IOException;
import java.io.Serializable;

import org.primefaces.event.FileUploadEvent;

import br.com.csl.lynx.handler.MovementSvpaHandler;
import br.com.csl.lynx.handler.SvpaFotoHandler;

public interface SvpaAction extends Serializable {

	void clear();
	void clearAction();
	
	void upload(FileUploadEvent event) throws IOException;
	
	void setMovementHandler(MovementSvpaHandler movementHandler);
	
	void setFotoHandler(SvpaFotoHandler fotoHandler);

	String getObs();
	
	void setObs(String obs);
}
