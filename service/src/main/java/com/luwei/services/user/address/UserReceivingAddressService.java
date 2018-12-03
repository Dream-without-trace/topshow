package com.luwei.services.user.address;

import com.luwei.models.user.receiving.ReceivingAddress;
import com.luwei.models.user.receiving.ReceivingAddressDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-28
 **/
@Slf4j
@Service
@Transactional
public class UserReceivingAddressService {

    @Resource
    private ReceivingAddressDao receivingAddressDao;

    /**
     * 保存
     *
     * @param receivingAddress
     */
    public void save(ReceivingAddress receivingAddress) {
        receivingAddressDao.save(receivingAddress);
    }


    /**
     * 根据用户id查找收货地址
     *
     * @param userId
     * @return
     */
    public ReceivingAddress findByUserId(Integer userId) {
        ReceivingAddress receivingAddress = receivingAddressDao.findFirstByUserIdAndDeletedIsFalse(userId);
        Assert.notNull(receivingAddress, "用户收货地址不存在");
        return receivingAddress;
    }

    /**
     * 根据用户id查找收货地址
     *
     * @param userId
     * @return
     */
    public ReceivingAddress findByUserIdNew(Integer userId) {
        return receivingAddressDao.findFirstByUserIdAndDeletedIsFalse(userId);
    }

    /**
     * 根据用户id查找收货地址（不判断null）
     *
     * @param userId
     * @return
     */
    public ReceivingAddress findReceivingAddressByUserId(Integer userId) {
        return receivingAddressDao.findFirstByUserIdAndDeletedIsFalse(userId);
    }

}
