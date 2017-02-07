package br.com.csl.lynx.utils;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.lang3.ArrayUtils;

import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.Permission;
import br.com.csl.lynx.support.Problem;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.support.Types;
import br.com.csl.lynx.support.Zona;

@ManagedBean
@ApplicationScoped
public class EnumLists implements Serializable{

	private static final long serialVersionUID = 1L;

	public RipStatus[] getStatuses() {
		return RipStatus.values();
	}
	
	public Problem[] getProblems() {
		Problem[] result = Problem.values();
		result = ArrayUtils.remove(result, 6);
		result = ArrayUtils.remove(result, 6);
		return result;
	}
	
	public Movement[] getMovements() {
		return Movement.values();
	}
	
	public Types[] getTypes() {
		return Types.values();
	}	
	
	public Permission[] getPermissions() {
		return Permission.values();
	}
	
	public String permissionLabel(String perm) {
		return Permission.valueOf(perm).getLabel();
	}
	
	public Zona[] getZonas() {
		return Zona.values();
	}

}
