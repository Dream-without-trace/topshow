package com.luwei.services.membershipCard.order;

import com.luwei.common.Response;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.utils.RandomUtil;
import com.luwei.models.courseEnrolment.CourseEnrolment;
import com.luwei.models.courseEnrolment.CourseEnrolmentDao;
import com.luwei.models.membershipcard.MembershipCard;
import com.luwei.models.membershipcard.MembershipCardDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.membershipcard.order.MembershipCardOrderDao;
import com.luwei.services.membershipCard.order.web.MembershipCardAddOrder;
import com.luwei.services.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @program: topshow
 * @description: 会员卡订单service
 * @author: ZhangHongJie
 * @create: 2018-12-03 19:50
 **/
@Slf4j
@Service
@Transactional
public class MembershipCardOrderService {

    @Resource
    private MembershipCardOrderDao membershipCardOrderDao;
    @Resource
    private MembershipCardDao membershipCardDao;
    @Resource
    private PayService payService;
    @Resource
    private CourseEnrolmentDao courseEnrolmentDao;


    public Response save(@Valid MembershipCardAddOrder dto, HttpServletRequest request) {
        String outTradeNo = "C" + RandomUtil.getNum(15);
        Integer membershipCardId = dto.getMembershipCardId();
        Integer userId = dto.getUserId();
        Optional<MembershipCard> membershipCardOpt = membershipCardDao.findById(membershipCardId);
        boolean present = membershipCardOpt.isPresent();
        Assert.isTrue(present, "会员卡ID不可用！");
        MembershipCard membershipCard = membershipCardOpt.get();
        Assert.notNull(membershipCard, "会员卡ID不可用！");
        membershipCardOrderDao.deleteAllByUserIdAndStatus(userId,MembershipCardOrderStatus.CREATE);
        String title1 = membershipCard.getTitle();
        Assert.notNull(title1, "会员卡ID不可用！");
        if (title1.equals("体验会员")) {
            List<MembershipCardOrder> list = membershipCardOrderDao.findAllByUserIdAndTitleAndDeletedFalse(userId,title1);
            if (list != null && list.size()>0) {
                Assert.isTrue(false, "您已办理过体验会员卡！");
            }
        }
        List<MembershipCardOrder> list = membershipCardOrderDao.findMembershipCardOrdersByUserIdAndDeletedFalse(userId);
        if (list != null && list.size()>0) {
            for (MembershipCardOrder membershipCardOrder:list) {
                if(membershipCardOrder == null ){
                    continue;
                }
                String title = membershipCardOrder.getTitle();
                if (title == null || "".equals(title)) {
                    continue;
                }
                if (title.equals("体验会员")) {
                    List<CourseEnrolment> courseEnrolments = courseEnrolmentDao.findAllByUserId(userId);
                        Assert.isTrue((courseEnrolments != null && courseEnrolments.size() > 0), "您办理的会员卡还未过期！");
                }else{
                    Integer effective = membershipCardOrder.getEffective();
                    long time = membershipCardOrder.getPayTime().getTime()+effective*24*3600*1000;
                    long l = System.currentTimeMillis();
                    Assert.isTrue((time<l), "您办理的会员卡未过期！");
                }
            }
        }
        int price = membershipCard.getPrice() == null?0:membershipCard.getPrice();
        MembershipCardOrder membershipCardOrder = new MembershipCardOrder(dto.getShopId(),membershipCard.getAreaId(),dto.getUserId(),
                membershipCardId,price,outTradeNo,membershipCard.getEffective(),membershipCard.getTitle(),
                membershipCard.getPicture(),membershipCard.getDetail(),membershipCard.getMemberBenefits(),
                null,MembershipCardOrderStatus.CREATE);
        membershipCardOrderDao.save(membershipCardOrder);
        return Response.build(20000, "success", payService.xcxPay(price, dto.getOpenid(),
                outTradeNo, request));
    }


    public List<MembershipCardOrder> findMembershipCardOrdersByOutTradeNo(String outTradeNo) {
        return membershipCardOrderDao.findMembershipCardOrdersByOutTradeNoAndDeletedFalse(outTradeNo);
    }

    public void saveAll(List<MembershipCardOrder> membershipCardOrderList) {
        membershipCardOrderDao.saveAll(membershipCardOrderList);
    }

    public MembershipCardOrder findAllById(Integer id) {
        return membershipCardOrderDao.findMembershipCardOrdersByMembershipCardOrderIdAndDeletedFalse(id);
    }

    public List<MembershipCardOrder> findAllByUserIdAndStatus(Integer userId, MembershipCardOrderStatus status) {
        return membershipCardOrderDao.findAllByUserIdAndStatusAndDeletedFalseOrderByPayTimeDesc(userId,status);
    }

    public List<MembershipCardOrder> findAllByUserIdAndStatusAndAreaId(Integer userId, MembershipCardOrderStatus status,Integer areaId) {
        return membershipCardOrderDao.findAllByUserIdAndStatusAndAreaIdAndDeletedIsFalseOrderByPayTimeDesc(userId,status,areaId);
    }

    public List<MembershipCardOrder> findAllByUserId(Integer userId) {
        return membershipCardOrderDao.findAllByUserIdAndDeletedIsFalse(userId);
    }
}
