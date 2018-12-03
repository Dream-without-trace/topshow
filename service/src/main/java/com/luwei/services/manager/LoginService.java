package com.luwei.services.manager;

import com.luwei.common.constants.RedisKeyPrefix;
import com.luwei.common.enums.type.ManagerType;
import com.luwei.common.enums.type.RoleType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.common.utils.BcryptUtil;
import com.luwei.models.manager.Manager;
import com.luwei.models.manager.ManagerDao;
import com.luwei.models.store.Store;
import com.luwei.models.user.UserDao;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.services.manager.cms.LoginSuccessVO;
import com.luwei.services.manager.cms.ManagerLoginVO;
import com.luwei.services.store.StoreService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@Service
public class LoginService {

    @Resource
    protected UserDao userDao;

    @Resource
    private ManagerDao managerDao;

    @Resource
    private StoreService storeService;

    @Resource
    protected ShiroTokenService shiroTokenService;

    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    /**
     * 管理员登录
     *
     * @param loginVO
     * @return
     */
    public LoginSuccessVO login(ManagerLoginVO loginVO) {
        //从redis中获得该验证码
        String captcha = stringRedisTemplate.opsForValue().get(RedisKeyPrefix.captcha(loginVO.getUuid()));
        if (StringUtils.isEmpty(captcha)) {
            throw new ValidateException(ExceptionMessage.AUTH_CAPTCHA_LOST);
        } else {
            if (!captcha.equals(loginVO.getCaptcha())) {
                throw new ValidateException(ExceptionMessage.AUTH_CAPTCHA_WRONG);
            } else {
                Manager manager = this.findByAccount(loginVO.getAccount());
                Assert.notNull(manager, ExceptionMessage.AUTH_ACCOUNT_PASSWORD_WRONG.getMessage());
                if (BcryptUtil.matching(loginVO.getPassword(), manager.getPassword())) {
                    if (manager.getDisabled()) {
                        throw new ValidateException(ExceptionMessage.ACCOUNT_HAS_DISABLED);
                    }
                    Integer managerId = manager.getManagerId();
                    RoleType role = manager.getRole();
                    String token = shiroTokenService.login(managerId.toString(), role.getCode().toString());
                    if (manager.getManagerType().equals(ManagerType.STORE)) {
                        Store store = storeService.findOne(manager.getStoreId());
                        return new LoginSuccessVO(role, token, manager.getUsername(), store.getStoreId());
                    } else {
                        return new LoginSuccessVO(role, token, manager.getUsername(), 0);
                    }
                } else {
                    throw new ValidateException(ExceptionMessage.AUTH_ACCOUNT_PASSWORD_WRONG);
                }
            }
        }
    }

    /**
     * 登出
     *
     * @param userId
     */
    public void logout(Integer userId) {
        shiroTokenService.logout(userId.toString());
    }

    /**
     * 查找一个
     *
     * @param account
     * @return
     */
    public Manager findByAccount(String account) {
        return managerDao.findFirstByAccountAndDisabledIsFalseAndDeletedIsFalse(account);
    }

    /**
     * 查找一个
     *
     * @param managerId
     * @return
     */
    public Manager findOne(Integer managerId) {
        Manager manager = managerDao.findById(managerId).orElse(null);
        Assert.notNull(manager, "管理员不存在");
        return manager;
    }


}
