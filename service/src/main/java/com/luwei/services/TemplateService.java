package com.luwei.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class TemplateService {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Object> page(Pageable pageable, Object query) {

        return null;
    }

    public List list(Object q) {
        String sql = "select g from Goods g";
        log.info("exec query");
        Query query = entityManager.createQuery(sql);
        // query.setParameter(1, 2);
        return query.getResultList();
    }

    public void save(Object object) {
    }

    public void update(Object object) {
    }

    public Object one(Integer id) {
        return null;
    }

    public void delete(Set<Integer> ids) {
    }

    public Object findOne(Integer id) {
        return null;
    }

}