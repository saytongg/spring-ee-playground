package com.saytongg.mock;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.saytongg.shared.service.BaseCrudService;

@Service("mockService")
public class MockService extends BaseCrudService<MockEntity>{

	public MockService(@Qualifier("mockDAO") MockDAO mockDAO) {
		super(mockDAO);
	}
}
