package com.luwei.models.bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface BillDao extends JpaRepository<Bill, Integer>, JpaSpecificationExecutor<Bill> {



}
