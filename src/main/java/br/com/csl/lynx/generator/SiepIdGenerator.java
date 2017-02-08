package br.com.csl.lynx.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentityGenerator;

import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.entity.PersistentEntity;

public class SiepIdGenerator extends IdentityGenerator implements IdentifierGenerator {

	@Override
	public synchronized Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		if ((((PersistentEntity) obj).getId()) == null) {
			
			/*
			 * Setting the prefix
			 */
			
			Calendar now = CalendarUtil.getToday();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
			
			String date = sdf.format(now.getTime());
			
			Long prefix = Long.valueOf(date + "0000");
			
			Long maxId = prefix + 9999;
			
			Connection connection = session.connection();
	        try {
	        	
	            PreparedStatement ps = connection.prepareStatement("SELECT MAX(id) AS maxId FROM siep WHERE id >= "+ prefix +" AND id <= "+ maxId);

	            ResultSet rs = ps.executeQuery();
	            
	            if (rs.first()) {
	            	Long id = rs.getLong("maxId");
	            	
	            	if (id != 0) {
	            		if (id < prefix + 9999) {	
	            			id = id + 1;
	            		} else throw new HibernateException("O limite de SIEP's por mês foi atingido!");  
	               
	            		return id;
	            	}
	            }
	            
	            return prefix + 1;

	        } catch (SQLException e) {
	            throw new HibernateException("Não foi possível gerar o código para a SIEP.");
	        }
	        
	    } else {
	        return ((PersistentEntity) obj).getId();
	    }
	}

}
