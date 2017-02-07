package br.com.csl.lynx.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class ContextListener implements ServletContextListener {
  
	public void contextDestroyed(ServletContextEvent arg0) {
      try {
          AbandonedConnectionCleanupThread.shutdown();
      } catch (InterruptedException e) {
    	  e.printStackTrace();
      }
   }

   @Override
   public void contextInitialized(ServletContextEvent arg0) {
   }
}
