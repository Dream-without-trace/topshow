package com.luwei.services.membershipCard.order;

import com.luwei.common.Response;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.utils.RandomUtil;
import com.luwei.models.membershipcard.MembershipCard;
import com.luwei.models.membershipcard.MembershipCardDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.membershipcard.order.MembershipCardOrderDao;
import com.luwei.services.membershipCard.order.web.MembershipCardAddOrder;
import com.luwei.services.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Timestamp;
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
public class MembershipCardOrderService {

    @Resource
    private MembershipCardOrderDao membershipCardOrderDao;
    @Resource
    private MembershipCardDao membershipCardDao;
    @Resource
    private PayService payService;

    public Response save(@Valid MembershipCardAddOrder dto, HttpServletRequest request) {
        String outTradeNo = "C" + RandomUtil.getNum(15);
        Integer membershipCardId = dto.getMembershipCardId();
        Integer userId = dto.getUserId();
        List<MembershipCardOrder> list = membershipCardOrderDao.findMembershipCardOrdersByUserId(userId);
        if (list != null && list.size()>0) {
            for (MembershipCardOrder membershipCardOrder:list) {
                if (membershipCardOrder != null && membershipCardOrder.getStatus().equals(MembershipCardOrderStatus.CREATE)) {
                    throw new IllegalStateException("\"您有未支付的订单！\"");
                }
            }
        }
        Optional<MembershipCard> membershipCardOpt = membershipCardDao.findById(membershipCardId);
        Assert.isTrue(membershipCardOpt.isPresent(), "会员卡ID不可用！");
        MembershipCard membershipCard = membershipCardOpt.get();
        Assert.isTrue(membershipCard == null, "会员卡ID不可用！");
        int price = membershipCard.getPrice() == null?0:membershipCard.getPrice();
        MembershipCardOrder membershipCardOrder = new MembershipCardOrder(dto.getShopId(),dto.getUserId(),
                membershipCardId,price,outTradeNo,membershipCard.getEffective(),membershipCard.getTitle(),
                membershipCard.getPicture(),membershipCard.getDetail(),null,null);
        membershipCardOrderDao.save(membershipCardOrder);
        return Response.build(20000, "success", payService.xcxPay(price, dto.getOpenid(),
                outTradeNo, request));
    }


    public List<MembershipCardOrder> findMembershipCardOrdersByOutTradeNo(String outTradeNo) {
        return membershipCardOrderDao.findMembershipCardOrdersByOutTradeNo(outTradeNo);
    }

    public void saveAll(List<MembershipCardOrder> membershipCardOrderList) {
        membershipCardOrderDao.saveAll(membershipCardOrderList);
    }

    public MembershipCardOrder findAllById(Integer id) {
        return membershipCardOrderDao.findMembershipCardOrdersByMembershipCardOrderId(id);
    }
}
