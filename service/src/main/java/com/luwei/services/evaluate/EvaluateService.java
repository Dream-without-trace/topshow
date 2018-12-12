package com.luwei.services.evaluate;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.luwei.common.enums.level.EvaluateLevel;
import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.enums.status.OrderStatus;
import com.luwei.common.enums.type.EvaluateType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.activity.order.ActivityOrder;
import com.luwei.models.evaluate.Evaluate;
import com.luwei.models.evaluate.EvaluateDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.order.Order;
import com.luwei.models.order.detail.OrderDetail;
import com.luwei.models.user.User;
import com.luwei.services.activity.ActivityService;
import com.luwei.services.activity.order.ActivityOrderService;
import com.luwei.services.evaluate.cms.EvaluateCmsVO;
import com.luwei.services.evaluate.web.EvaluateDTO;
import com.luwei.services.evaluate.web.EvaluateWebListVO;
import com.luwei.services.evaluate.web.EvaluateWebVO;
import com.luwei.services.goods.GoodsService;
import com.luwei.services.membershipCard.order.MembershipCardOrderService;
import com.luwei.services.order.OrderService;
import com.luwei.services.order.detail.OrderDetailService;
import com.luwei.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@Service
@Transactional
public class EvaluateService {

    @Resource
    private EvaluateDao evaluateDao;

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityOrderService activityOrderService;
    @Resource
    private MembershipCardOrderService membershipCardOrderService;

    /**
     * 获取活动或商品或门店的评价
     *
     * @param tripartiteId
     * @param type
     */
    public List<Evaluate> list(Integer tripartiteId, EvaluateType type) {
        return evaluateDao.findEvaluatesByTypeAndTripartiteIdAndDeletedIsFalse(type, tripartiteId);
    }

    /**
     * 获取活动或商品或门店的评价cmsVO
     *
     * @param tripartiteId
     * @param type
     */
    public List<EvaluateCmsVO> cmsVOList(Integer tripartiteId, EvaluateType type) {
        List<Evaluate> list = null;
        if (tripartiteId != null && tripartiteId != 0){
            list = this.findByActivityIdAndType(tripartiteId, type);
        }else{
            list = evaluateDao.findAllByTypeAndDeletedIsFalse(type);
        }
        if (list == null || list.size()<1) {
            return new ArrayList<>();
        }
        return list.stream().map(e -> {
            EvaluateCmsVO vo = new EvaluateCmsVO();
            User user = userService.findOne(e.getUserId());
            BeanUtils.copyProperties(e, vo);
            vo.setNickname(user.getNickname());
            vo.setAvatarUrl(user.getAvatarUrl());
            List<String> pictureList;
            if (!StringUtils.isEmpty(e.getPicture())) {
                pictureList = Arrays.asList(e.getPicture().split(","));
            } else {
                pictureList = new ArrayList<>();
            }
            vo.setPicture(pictureList);
            vo.setFlagType(e.getFlagType());
            return vo;
        }).collect(Collectors.toList());
    }


    /**
     * 添加评价
     *
     * @param dto
     */
    public void save(EvaluateDTO dto) {
        Evaluate evaluate = new Evaluate();
        if (EvaluateType.GOODS.equals(dto.getEvaluateType())) {
            Order order = orderService.findOne(dto.getId());
            if (!order.getStatus().equals(OrderStatus.RECEIVING)) {
                throw new ValidateException(ExceptionMessage.ORDER_STATUS_FAIL);
            } else {
                orderDetailService.updateFlagType(dto.getId(), dto.getTripartiteId());
                // 判断所有订单详情是否全部评价完成
                List<OrderDetail> orderDetailList = orderDetailService.list(order.getOrderId());
                List<OrderDetail> flag = orderDetailList.stream().filter(e -> FlagType.RIGHT.equals(e.getFlagType())).collect(Collectors.toList());
                if (flag.size() == orderDetailList.size()) {
                    order.setStatus(OrderStatus.COMPLETE);
                    orderService.save(order);
                }
                evaluate.setTripartiteId(dto.getTripartiteId());
            }
        } else if (EvaluateType.ACTIVITY.equals(dto.getEvaluateType())) {
            ActivityOrder activityOrder = activityOrderService.findOne(dto.getId());
            if (!ActivityOrderStatus.JOIN.equals(activityOrder.getStatus())) {
                throw new ValidateException(ExceptionMessage.ACTIVITY_ORDER_STATUS_FAIL);
            } else {
                evaluate.setTripartiteId(dto.getTripartiteId());
                activityOrder.setStatus(ActivityOrderStatus.COMPLETE);
                activityOrderService.save(activityOrder);
            }
        } else if (EvaluateType.SHOP.equals(dto.getEvaluateType())) {
            MembershipCardOrder membershipCardOrder = membershipCardOrderService.findAllById(dto.getId());
            if (membershipCardOrder == null) {
                throw new ValidateException(ExceptionMessage.ORDER_NOT_EXIST);
            }
            MembershipCardOrderStatus status = membershipCardOrder.getStatus();
            if (status == null || !status.equals(MembershipCardOrderStatus.PAY)) {
                throw new ValidateException(ExceptionMessage.ORDER_STATUS_FAIL);
            }
            Integer effective = membershipCardOrder.getEffective();
            effective = effective == null?0:effective;
            Date payTime = membershipCardOrder.getPayTime();
            long time = payTime.getTime()+(effective*24*3600*1000);
            long currentTime = System.currentTimeMillis();
            if (currentTime > time) {
                throw new ValidateException(ExceptionMessage.MEMBERSHIP_STATUS_EXPIRED);
            }
        }
        BeanUtils.copyProperties(dto, evaluate);
        evaluate.setPicture(Joiner.on(",").join(dto.getPicture()));
        evaluate.setFlagType(FlagType.RIGHT);
        evaluate.setLevel(EvaluateLevel.GOOD);
        evaluate.setPraise(0);
        evaluate.setAddress("");
        evaluate.setType(dto.getEvaluateType());
        evaluateDao.save(evaluate);
    }


    /**
     * 查找一个
     *
     * @param evaluateId
     * @return
     */
    public Evaluate findOne(Integer evaluateId) {
        Evaluate evaluate = evaluateDao.findById(evaluateId).orElse(null);
        Assert.notNull(evaluate, "评价不存在");
        return evaluate;
    }

    public void delete(Set<Integer> dis) {
    }

    /**
     * 根据类型和商品或活动id获取评论列表
     *
     * @param id           活动或商品id
     * @param evaluateType 类型
     * @return
     */
    public List<Evaluate> findByActivityIdAndType(Integer id, EvaluateType evaluateType) {
        return evaluateDao.findEvaluatesByTypeAndTripartiteIdAndDeletedIsFalse(evaluateType, id);
    }

    /**
     * 显示隐藏
     *
     * @param evaluateId
     * @return
     */
    public List<EvaluateCmsVO> display(Integer evaluateId,EvaluateType evaluateType, Integer tripartiteId) {
        Evaluate evaluate = this.findOne(evaluateId);
        if (FlagType.RIGHT.equals(evaluate.getFlagType())) {
            evaluate.setFlagType(FlagType.DENY);
        } else {
            evaluate.setFlagType(FlagType.RIGHT);
        }
        Evaluate entity = evaluateDao.save(evaluate);
        List<Evaluate> evaluateList = evaluateDao.findEvaluatesByTypeAndTripartiteIdAndDeletedIsFalse(evaluateType,
                tripartiteId);
        return evaluateList.stream().map(this::toEvaluateCmsVO).collect(Collectors.toList());
    }


    /**
     * 转换
     *
     * @param evaluate
     * @return
     */
    public EvaluateCmsVO toEvaluateCmsVO(Evaluate evaluate) {
        User user = userService.findOne(evaluate.getUserId());
        EvaluateCmsVO vo = new EvaluateCmsVO();
        BeanUtils.copyProperties(evaluate, vo);
        String picture = evaluate.getPicture();
        if (picture != null && !picture.equals("")) {
            List<String> strings = Arrays.asList();
            vo.setPicture(Arrays.asList(picture.split(",")));
        }
        vo.setFlagType(evaluate.getFlagType());
        vo.setNickname(user.getNickname());
        vo.setUserId(user.getUserId());
        vo.setAvatarUrl(user.getAvatarUrl());
        return vo;
    }


    /**
     * 根据活动id获取评论列表
     *
     * @param activityId
     * @return
     */
    public List<EvaluateCmsVO> list(Integer activityId) {
        List<Evaluate> evaluateList = evaluateDao.findEvaluatesByTypeAndTripartiteIdAndDeletedIsFalse(EvaluateType.ACTIVITY, activityId);
        return evaluateList.stream().map(this::toEvaluateCmsVO).collect(Collectors.toList());
    }


    /**
     * 根据类型和id获取评论列表
     *
     * @param id
     * @param type
     * @return
     */
    public List<EvaluateWebVO> findEvaluateByIdAndType(Integer id, EvaluateType type) {
        return evaluateDao.findEvaluateByIdAndType(id, type, FlagType.DENY);
    }

    /**
     * 评论分页接口
     *
     * @param pageable
     * @param id
     * @param type
     * @return
     */
    public Page<EvaluateWebListVO> page(Pageable pageable, Integer id, EvaluateType type) {
        Specification<Evaluate> specification = (Root<Evaluate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("tripartiteId").as(Integer.class), id));
            list.add(criteriaBuilder.equal(root.get("type").as(EvaluateType.class), type));
            list.add(criteriaBuilder.equal(root.get("flagType").as(FlagType.class), FlagType.RIGHT));
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("evaluateId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return evaluateDao.findAll(specification, pageable).map(this::toEvaluateWebListVO);
    }

    /**
     * 转换
     *
     * @param evaluate
     * @return
     */
    private EvaluateWebListVO toEvaluateWebListVO(Evaluate evaluate) {
        EvaluateWebListVO evaluateWebListVo = new EvaluateWebListVO();
        BeanUtils.copyProperties(evaluate, evaluateWebListVo);
        User user = userService.findOne(evaluate.getUserId());
        evaluateWebListVo.setAvatarUrl(user.getAvatarUrl());
        evaluateWebListVo.setNickname(user.getNickname());
        if (!StringUtils.isEmpty(evaluate.getPicture())) {
            evaluateWebListVo.setPictureList(Arrays.asList(evaluate.getPicture().split(",")));
        } else {
            evaluateWebListVo.setPictureList(Lists.newArrayList());
        }
        return evaluateWebListVo;
    }


}
