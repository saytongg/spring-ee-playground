package com.saytongg.mock;

import org.springframework.stereotype.Repository;

import com.saytongg.shared.database.dao.BaseDAO;

@Repository("mockDAO") 
public class MockDAO extends BaseDAO<MockEntity>{

	public MockDAO() {
		super(MockEntity.class);
	}
}
