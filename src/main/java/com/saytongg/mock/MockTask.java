package com.saytongg.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component("mockTask")
public class MockTask {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private final TransactionTemplate transactionTemplate;
	
	
	@Autowired
	public MockTask(@Qualifier("mockTaskTransactionTemplate") TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void execute() {
		logger.info("Mock task started execution!");
		
		transactionTemplate.execute(new TransactionCallback<Object>() {
			
			@Override
			public Object doInTransaction(TransactionStatus transactionStatus) {
				logger.info("Hi from mock transaction template!");
				return null;
			}
			
		});
		
		logger.info("Mock task finished execution!");
	}
}
