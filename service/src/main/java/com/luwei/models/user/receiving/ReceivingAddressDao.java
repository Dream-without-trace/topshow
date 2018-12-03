package com.luwei.models.user.receiving;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leone
 * @since 2018-08-29
 **/
public interface ReceivingAddressDao extends JpaRepository<ReceivingAddress, Integer>, JpaSpecificationExecutor<ReceivingAddress> {

    ReceivingAddress findFirstByUserIdAndDeletedIsFalse(Integer userId);

}
