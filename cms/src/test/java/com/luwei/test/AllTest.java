package com.luwei.test;

import com.luwei.CmsApplication;
import com.luwei.common.enums.type.BillType;
import com.luwei.models.manager.Manager;
import com.luwei.models.manager.ManagerDao;
import com.luwei.models.user.User;
import com.luwei.services.integral.bill.IntegralBillService;
import com.luwei.services.integral.bill.web.IntegralBillDTO;
import com.luwei.services.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-14
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmsApplication.class)
public class AllTest {

    @Resource
    private ManagerDao managerDao;

    @Resource
    private UserService userService;

    @Resource
    private IntegralBillService integralBillService;

    @Test
    public void test() {
//        Manager manager = managerDao.findFirstByAccountAndDisabledIsFalse("chen");
//        System.out.println(manager);
//        User user = userService.findByOpenidNew("ow1r64lfsZZBx1zjT9kuazfpOOyE");
//        integralBillService.save(new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(), 10, user.getIntegral(), BillType.INCOME, "用户绑定手机号"));
    }

}
