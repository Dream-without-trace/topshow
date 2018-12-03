package com.luwei.services.bill;

import com.luwei.models.bill.Bill;
import com.luwei.models.bill.BillDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-07
 **/
@Slf4j
@Service
@Transactional
public class BillService {

    @Resource
    private BillDao billDao;


    public void save(Bill bill) {
        billDao.save(bill);
    }


}
