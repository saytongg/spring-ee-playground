package com.saytongg.shared.main;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class Initializer implements ServletContextListener {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("Context destoryed.");
	}

	@Override
	public void contextInitialized(ServletContextEvent event){
		final ServletContext context = event.getServletContext();
		
		printInitParamters(context);
		
    	logger.info("Context initialized.");
    }
	
	private void printInitParamters(ServletContext context) {
		List<String> parameterList = Collections.list(context.getInitParameterNames());
		for(String parameter : parameterList) {
			String parameterValue = context.getInitParameter(parameter);
			
			logger.info(String.format("%s : %s", parameter, parameterValue));
		}
	}
}