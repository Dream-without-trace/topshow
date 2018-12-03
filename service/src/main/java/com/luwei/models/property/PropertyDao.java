package com.luwei.models.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface PropertyDao extends JpaRepository<Property, Integer>, JpaSpecificationExecutor<Property> {


}
