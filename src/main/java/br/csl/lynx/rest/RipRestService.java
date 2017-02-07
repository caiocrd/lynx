package br.csl.lynx.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.com.csl.lynx.model.Rip;

@Path("RIP")
public class RipRestService {

	@GET
	@Path("OPEN")
	private List<Rip> getOpenRips(){
		List<Rip> ripsAbertas = new ArrayList<>();
		
		return ripsAbertas;
	}
	
	
}
