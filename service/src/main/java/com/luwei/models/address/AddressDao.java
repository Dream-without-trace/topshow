package com.luwei.models.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface AddressDao extends JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address> {



}
