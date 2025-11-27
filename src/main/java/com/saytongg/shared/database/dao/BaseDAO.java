package com.saytongg.shared.database.dao;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.criteria.CriteriaDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.saytongg.shared.beans.PageBean;
import com.saytongg.shared.database.entity.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class BaseDAO<T extends BaseEntity> {

    @Autowired
    protected SessionFactory sessionFactory;

    private final Class<T> clazz;

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    // For page queries
    private static final int ITEMS_PER_PAGE_INCREMENT = 5;
    private static final int MIN_ITEMS_PER_PAGE = 5;
    private static final int MAX_ITEMS_PER_PAGE = 100;
    

    public BaseDAO(Class<T> t){
        this.clazz = t;
    }
    
    // For paging
    protected int getMaxPage(int itemsPerPage, long maxCount){
        if(maxCount == 0){
            return 1;
        }

        return (int) maxCount / itemsPerPage + (maxCount % itemsPerPage == 0 ? 0 : 1);
    }
    
    // Avoid querying too much items
    private int recalibrateItemsPerPage(int itemsPerPage) {
    	if(itemsPerPage < MIN_ITEMS_PER_PAGE) {
    		return MIN_ITEMS_PER_PAGE;
    	}
    	
    	if(itemsPerPage > MAX_ITEMS_PER_PAGE) {
    		return MAX_ITEMS_PER_PAGE;
    	}
    	
    	// Rounds "items per page" to multiple of 5
    	return (itemsPerPage / ITEMS_PER_PAGE_INCREMENT) * ITEMS_PER_PAGE_INCREMENT;
    }
    
    
    protected PageBean<T> getPage(CriteriaDefinition<T> criteria, int itemsPerPage, int page) throws Exception {
    	itemsPerPage = recalibrateItemsPerPage(itemsPerPage);
    	
    	long maxCount = countAllByCriteria(criteria);
        int maxPage = getMaxPage(itemsPerPage, maxCount);

        if(page > maxPage){
            throw new Exception("Page is invalid.");
        }

        List<T> items = findAllByCriteriaAndPage(criteria, itemsPerPage, page);
        PageBean<T> currentPage = new PageBean<>(items, maxCount, page, maxPage, itemsPerPage);

        return currentPage;
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<T> findById(long id) throws Exception{
    	Session session = sessionFactory.getCurrentSession();
        T t = session.find(clazz, id);

        if(t == null){
            return Optional.empty();
        }

        if(t.getDeletedOn() == 0){
            return Optional.ofNullable(t);
        }

        return Optional.empty();
    }
    
    
    @Transactional(propagation = Propagation.MANDATORY)
    public List<T> findAll() throws Exception {
    	Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(clazz);
        Root<T> root = cr.from(clazz);
        cr.select(root)
            .where(cb.equal(root.get("deletedOn"), 0));

        Query<T> query = session.createQuery(cr);
        List<T> results = query.list();
        
        return results;
    }
    

    
    @Transactional(propagation = Propagation.MANDATORY)
	public long countAll() throws Exception {
    	Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<T> root = cr.from(clazz);
        cr.select(cb.count(root))
            .where(cb.equal(root.get("deletedOn"), 0));

        Query<Long> query = session.createQuery(cr);
        long result = query.getSingleResult();

        return result;
	}
    

    @Transactional(propagation = Propagation.MANDATORY)
    protected Optional<T> findByCriteria(CriteriaDefinition<T> criteria) throws Exception {
        if(criteria == null){
            throw new Exception("Criteria is null.");
        }
        
        Session session = sessionFactory.getCurrentSession();
        T result = session.createSelectionQuery(criteria).uniqueResult();

        return Optional.ofNullable(result);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected List<T> findAllByCriteria(CriteriaDefinition<T> criteria) throws Exception {
    	if(criteria == null){
            throw new Exception("Criteria is null.");
        }
        
    	Session session = sessionFactory.getCurrentSession();
        SelectionQuery<T> query = session.createSelectionQuery(criteria);
        List<T> results = query.list();

        return results;
    }
    
    @Transactional(propagation = Propagation.MANDATORY)
    protected List<T> findAllByCriteriaAndPage(CriteriaDefinition<T> criteria, int itemsPerPage, int page) throws Exception {
        if(criteria == null || page <= 0 || itemsPerPage <= 0){
            throw new Exception(String.format("Page is %d or items per page %d or criteria is null.", page, itemsPerPage));
        }
        
        itemsPerPage = recalibrateItemsPerPage(itemsPerPage);
        
        Session session = sessionFactory.getCurrentSession();
        SelectionQuery<T> query = session.createSelectionQuery(criteria);
        query.setFirstResult((page - 1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        
        List<T> results = query.list();

        return results;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected long countAllByCriteria(CriteriaDefinition<T> criteria) throws Exception {
    	if(criteria == null){
            throw new Exception("Criteria is null.");
        }
        
    	Session session = sessionFactory.getCurrentSession();
        SelectionQuery<T> query = session.createSelectionQuery(criteria);
        long count = query.getResultCount();

        return count;
    }


    /*
     * Create, update, delete operations
     */
    
    @Transactional(propagation = Propagation.MANDATORY)
    public void insert(T t) throws Exception {
        if(t == null){
            throw new Exception("Object to be inserted is null.");
        }

        Session session = sessionFactory.getCurrentSession();
        session.persist(t);
    }

    
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(T t) throws Exception {
    	if(t == null){
            throw new Exception("Object to be updated is null.");
        }

        Session session = sessionFactory.getCurrentSession();
        session.merge(t);
    }

    // Doesn't really delete the entity but invalidate it
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(T t) throws Exception {
    	if(t == null){
            throw new Exception("Object to be inserted is null.");
        }

    	long time = Instant.now().getEpochSecond();
        t.setDeletedOn(time);

        Session session = sessionFactory.getCurrentSession();
        session.merge(t);
    }

    // This really deletes the entity
    @Transactional(propagation = Propagation.MANDATORY)
    public void erase(T t) throws Exception {
    	if(t == null){
            throw new Exception("Object to be inserted is null.");
        }

    	Session session = sessionFactory.getCurrentSession();
        session.remove(t);
    }
}
