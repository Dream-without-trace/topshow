package com.luwei.services.customer;

import com.luwei.common.enums.type.FlagType;
import com.luwei.models.store.Store;
import com.luwei.services.store.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-07
 **/
@Slf4j
@Service
@Transactional
public class CustomerService {

    @Resource
    private StoreService storeService;

    public FlagType check(String customerId) {
        Store store = storeService.findByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(store)) {
            return FlagType.RIGHT;
        }
        return FlagType.DENY;
    }


}
