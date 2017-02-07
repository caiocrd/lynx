package br.com.csl.lynx.config;

import java.beans.PropertyVetoException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class JpaConfiguration {

	@Bean
	public ComboPooledDataSource dataSource(){
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {	e.printStackTrace(); }
		
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/lynx");
		dataSource.setUser("root");
		dataSource.setPassword("");
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager( localContainerEntityManagerFactoryBean().getObject() );
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		
		lef.setDataSource(dataSource());
		lef.setPersistenceUnitName("LynxPU");
		
		return lef;
	}
	
	@Bean
	public PersistenceAnnotationBeanPostProcessor paPostPrecessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

}
