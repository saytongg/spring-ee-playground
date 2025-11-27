package com.saytongg.shared.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.saytongg.shared.database.dao.BaseDAO;
import com.saytongg.shared.database.entity.BaseEntity;

public abstract class BaseCrudService<T extends BaseEntity> extends BaseService{
	
	private final BaseDAO<T> dao;
	
	public BaseCrudService(BaseDAO<T> dao) {
		super();
		
		this.dao = dao;
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public Optional<T> getById(long id) throws Exception {
		return dao.findById(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<T> getAll() throws Exception {
		return dao.findAll();
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	public void create(T entity) throws Exception {
		dao.insert(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(List<T> entityList) throws Exception {
		for(T entity : entityList) {
			create(entity);
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T entity) throws Exception{
		dao.update(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(List<T> entityList) throws Exception{
		for(T entity : entityList) {
			update(entity);
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) throws Exception {
		Optional<T> possibleEntity =  dao.findById(id);
		
		if(possibleEntity.isEmpty()) {
			logger.info("ID {} is not associated with any entity", id);
			throw new Exception("ID is not associated with any entity");
		}
		
		dao.delete(possibleEntity.get());
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(List<Long> idList) throws Exception{
		for(long id : idList) {
			delete(id);
		}
	}
}
