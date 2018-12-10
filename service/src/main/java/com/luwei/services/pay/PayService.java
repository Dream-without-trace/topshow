package com.luwei.services.pay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.enums.status.OrderStatus;
import com.luwei.common.enums.type.BillType;
import com.luwei.common.enums.type.TransactionType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.common.property.AppProperties;
import com.luwei.common.utils.AppUtils;
import com.luwei.common.utils.HttpUtil;
import com.luwei.common.utils.RandomUtil;
import com.luwei.models.activity.order.ActivityOrder;
import com.luwei.models.activity.order.subcard.ActivitySubCardOrder;
import com.luwei.models.activity.order.subcard.ActivitySubCardOrderDao;
import com.luwei.models.bill.Bill;
import com.luwei.models.integralset.IntegralSet;
import com.luwei.models.integralset.IntegralSetDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.order.Order;
import com.luwei.models.user.User;
import com.luwei.services.activity.order.ActivityOrderService;
import com.luwei.services.bill.BillService;
import com.luwei.services.integral.bill.IntegralBillService;
import com.luwei.services.integral.bill.web.IntegralBillDTO;
import com.luwei.services.membershipCard.order.MembershipCardOrderService;
import com.luwei.services.order.OrderService;
import com.luwei.services.pay.pojos.NotifyDTO;
import com.luwei.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.*;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-05
 **/
@Slf4j
@Service
@Transactional
public class PayService {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private AppProperties appProperties;

    @Resource
    private BillService billService;

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @Resource
    private ActivityOrderService activityOrderService;

    @Resource
    private MembershipCardOrderService membershipCardOrderService;

    @Resource
    private IntegralBillService integralBillService;

    @Resource
    private ActivitySubCardOrderDao activitySubCardOrderDao;

    @Resource
    private IntegralSetDao integralSetDao;

    /**
     * 小程序支付
     *
     * @param total 总金额
     * @throws Exception
     */
    public Map<String, String> xcxPay(Integer total, String openid, String outTradeNo, HttpServletRequest request) {
        String nonce_str = RandomUtil.getNum(24);
        String spbill_create_ip = AppUtils.getIpAddress(request);
        if (!AppUtils.isIp(spbill_create_ip)) {
            spbill_create_ip = "127.0.0.1";
        }
        SortedMap<String, String> reqMap = new TreeMap<>();
        reqMap.put("appid", appProperties.getWx().getApp_id());
        reqMap.put("mch_id", appProperties.getWx().getMch_id());
        reqMap.put("nonce_str", nonce_str);
        reqMap.put("body", "小程序支付");
        reqMap.put("out_trade_no", outTradeNo);
        reqMap.put("total_fee", total.toString());
        reqMap.put("spbill_create_ip", spbill_create_ip);
        reqMap.put("notify_url", appProperties.getWx().getNotify_url());
        reqMap.put("trade_type", appProperties.getWx().getTrade_type());
        reqMap.put("openid", openid);
        String sign = AppUtils.createSign(reqMap, appProperties.getWx().getApi_key());
        reqMap.put("sign", sign);
        String xml = AppUtils.mapToXml(reqMap);
        log.info("xml:{}", xml);
        String result = HttpUtil.sendPostXml(appProperties.getWx().getCreate_order(), xml, null);
        log.info(result);
        Map<String, String> resData = AppUtils.xmlToMap(result);
        System.out.println(resData);
        if ("SUCCESS".equals(resData.get("return_code"))) {
            Map<String, String> resultMap = new LinkedHashMap<>();
            //返回的预付单信息
            String prepay_id = resData.get("prepay_id");
            System.out.println(prepay_id);
            resultMap.put("appId", appProperties.getWx().getApp_id());
            resultMap.put("nonceStr", nonce_str);
            resultMap.put("package", "prepay_id=" + prepay_id);
            resultMap.put("signType", "MD5");
            resultMap.put("timeStamp", RandomUtil.getDateStr(14));
            String paySign = AppUtils.createSign(resultMap, appProperties.getWx().getApi_key());
            resultMap.put("paySign", paySign);
            return resultMap;
        } else {
            throw new ValidateException(ExceptionMessage.WEI_XIN_PAY_FAIL);
        }
    }
    /*{
        "appId": "wx97230c01aa72758e",
        "nonceStr": "a92dc7244879c098a4f8eff1d6071201",
        "package": "prepay_id=wx06155800330768dcea8e7fa23627719445",
        "paySign": "BFAD32640DAB566AA913C3DC5EC603AD",
        "signType": "MD5",
        "timeStamp": "20180906155800"
    }*/

    /**
     * 微信异步回调
     *
     * @param request
     * @param response
     */
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String notifyXml = AppUtils.requestDataToXml(request);
        String respXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[error]]></return_msg></xml>";
        log.info("收到微信异步回调,报文为:" + notifyXml);
        Map<String, String> map = AppUtils.xmlToMap(notifyXml);
        NotifyDTO notifyDTO = AppUtils.xmlToBean(NotifyDTO.class, notifyXml);
        Assert.notNull(notifyDTO, "xml转换失败");
        if ("SUCCESS".equals(notifyDTO.getResult_code())) {
            log.info("微信支付回调成功!");
            User user = userService.findByOpenidNew(notifyDTO.getOpenid());
            log.info("user:{}" + user);
            log.info("notifyDTO:{}" + notifyDTO);
            saveBill(notifyDTO, user);
            respXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        }
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(respXml.getBytes());
        out.flush();
        out.close();
    }

    /**
     * 保存订单流水
     *
     * @param dto
     */
    public void saveBill(NotifyDTO dto, User user) {
        log.info("保存支付流水");
        Bill bill = new Bill();
        String outTradeNo = dto.getOut_trade_no();
        bill.setAmount(Integer.parseInt(dto.getTotal_fee()));
        bill.setOutTradeNo(outTradeNo);
        bill.setUserId(user.getUserId());
        bill.setTransactionId(dto.getTransaction_id());
        if (outTradeNo.startsWith("A")) {
            bill.setRemark("购买活动订单");
            bill.setStoreId(0);
            bill.setTransactionType(TransactionType.ACTIVITY);
            ActivityOrder activityOrder = activityOrderService.findByOutTradeNo(outTradeNo);
            Assert.notNull(activityOrder, "活动订单不存在");
            activityOrder.setStatus(ActivityOrderStatus.PAY);
            updateActivityStatus(activityOrder);
            // 给用户添加积分
            userService.addIntegral(user.getUserId(), 20);
            // 保存积分流水
            integralBillService.save(new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(),
                    20, user.getIntegral() + 20, BillType.INCOME, "用户购买活动"));
        } else if (outTradeNo.startsWith("G")) {
            List<Order> orderList = orderService.findByOutTradeNo(outTradeNo);
            updateOrderStatus(orderList);
            bill.setRemark("购买商品订单");
            bill.setTransactionType(TransactionType.GOODS);
            bill.setStoreId(0);
            if (!ObjectUtils.isEmpty(orderList)) {
                bill.setStoreId(orderList.get(0).getStoreId());
            }
            // 给用户添加积分
            userService.addIntegral(user.getUserId(), 20);
            // 保存积分流水
            integralBillService.save(new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(), 20, user.getIntegral() + 20, BillType.INCOME, "用户购买商品"));
        } else if (outTradeNo.startsWith("C")) {
            List<MembershipCardOrder> membershipCardOrderList = membershipCardOrderService.findMembershipCardOrdersByOutTradeNo(outTradeNo);
            updateMembershipCardOrderStatus(membershipCardOrderList);
            bill.setRemark("购买会员卡订单");
            bill.setTransactionType(TransactionType.CARD);
            bill.setStoreId(0);
            if (!ObjectUtils.isEmpty(membershipCardOrderList)) {
                bill.setStoreId(membershipCardOrderList.get(0).getShopId());
            }
            int purchaseMembershipCardIntegral =0;
            List<IntegralSet> integralSets = integralSetDao.findAll();
            if (integralSets != null && integralSets.size()>0) {
                IntegralSet integralSet = integralSets.get(0);
                if (integralSet != null) {
                    purchaseMembershipCardIntegral = integralSet.getPurchaseMembershipCardIntegral();
                }
            }
            // 给用户添加积分
            userService.addIntegral(user.getUserId(), purchaseMembershipCardIntegral);
            // 保存积分流水
            integralBillService.save(new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(),
                    purchaseMembershipCardIntegral, user.getIntegral() + purchaseMembershipCardIntegral,
                    BillType.INCOME, "用户购买会员"));
        } else if (outTradeNo.startsWith("S")) {
            List<ActivitySubCardOrder> activitySubCardOrders = activitySubCardOrderDao.findAllByOutTradeNoOrderByPayTimeDesc(outTradeNo);
            updateActivitySubCardOrderStatus(activitySubCardOrders);
            bill.setRemark("购买活动次卡订单");
            bill.setTransactionType(TransactionType.CARD);
            bill.setStoreId(0);
            if (!ObjectUtils.isEmpty(activitySubCardOrders)) {
                bill.setStoreId(0);
            }
            /*int purchaseMembershipCardIntegral =0;
            List<IntegralSet> integralSets = integralSetDao.findAll();
            if (integralSets != null && integralSets.size()>0) {
                IntegralSet integralSet = integralSets.get(0);
                if (integralSet != null) {
                    purchaseMembershipCardIntegral = integralSet.getPurchaseMembershipCardIntegral();
                }
            }
            // 给用户添加积分
            userService.addIntegral(user.getUserId(), purchaseMembershipCardIntegral);
            // 保存积分流水
            integralBillService.save(new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(),
                    purchaseMembershipCardIntegral, user.getIntegral() + purchaseMembershipCardIntegral,
                    BillType.INCOME, "用户购买会员"));*/
        }
        billService.save(bill);
    }




    /**
     * 修改订单支付时间
     *
     * @param orderList
     */
    public void updateOrderStatus(List<Order> orderList) {
        Date date = new Date();
        orderList.forEach(e -> {
            e.setPayTime(date);
            e.setStatus(OrderStatus.PAY);
        });
        orderService.saveAll(orderList);
    }

    /**
     * 修改活动订单状态
     *
     * @param activityOrder
     */
    public void updateActivityStatus(ActivityOrder activityOrder) {
        activityOrder.setStatus(ActivityOrderStatus.PAY);
        activityOrder.setPayTime(new Date());
        activityOrderService.save(activityOrder);
    }

    private void updateMembershipCardOrderStatus(List<MembershipCardOrder> membershipCardOrderList) {
        Date date = new Date();
        membershipCardOrderList.forEach(e -> {
            e.setPayTime(date);
            e.setStatus(MembershipCardOrderStatus.PAY);
        });
        membershipCardOrderService.saveAll(membershipCardOrderList);
    }

    private void updateActivitySubCardOrderStatus(List<ActivitySubCardOrder> activitySubCardOrders) {
        Date date = new Date();
        int daTeTime = (int)(System.currentTimeMillis()/1000);
        activitySubCardOrders.forEach(e -> {
            e.setPayTime(daTeTime);
            e.setStatus(2);
        });
        activitySubCardOrderDao.saveAll(activitySubCardOrders);
    }

}
