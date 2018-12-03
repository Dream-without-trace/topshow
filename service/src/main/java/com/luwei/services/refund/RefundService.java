package com.luwei.services.refund;

import com.luwei.common.enums.status.OrderStatus;
import com.luwei.common.enums.status.RefundStatus;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.order.Order;
import com.luwei.models.order.detail.OrderDetail;
import com.luwei.models.refund.Refund;
import com.luwei.models.refund.RefundDao;
import com.luwei.models.store.Store;
import com.luwei.services.order.OrderService;
import com.luwei.services.order.detail.OrderDetailService;
import com.luwei.services.refund.cms.RefundEditDTO;
import com.luwei.services.refund.cms.RefundPageVO;
import com.luwei.services.refund.cms.RefundQueryDTO;
import com.luwei.services.refund.web.RefundAddDTO;
import com.luwei.services.refund.web.RefundWebVO;
import com.luwei.services.specification.SpecificationService;
import com.luwei.services.store.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p> 退款
 *
 * @author Leone
 * @since 2018-08-20
 **/
@Slf4j
@Service
@Transactional
public class RefundService {

    @Resource
    private RefundDao refundDao;

    @Resource
    private OrderService orderService;

    @Resource
    private StoreService storeService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private SpecificationService specificationService;

    /**
     * 分页
     *
     * @param pageable
     * @param query
     * @return
     */
    public Page<RefundPageVO> page(Pageable pageable, RefundQueryDTO query) {

        Specification<Refund> specification = (Root<Refund> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            if (!StringUtils.isEmpty(query.getOrderOutNo())) {
                predicateList.add(criteriaBuilder.like(root.get("orderOutNo").as(String.class), "%" + query.getOrderOutNo() + "%"));
            }
            if ((!ObjectUtils.isEmpty(query.getStatus()))) {
                predicateList.add(criteriaBuilder.equal(root.get("status").as(RefundStatus.class), query.getStatus()));
            }

            Predicate[] predicates = new Predicate[predicateList.size()];
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(predicates)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("refundId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return refundDao.findAll(specification, pageable).map(this::toRefundPageVO);
    }

    /**
     * 转换
     *
     * @param refund
     * @return
     */
    private RefundPageVO toRefundPageVO(Refund refund) {
        RefundPageVO vo = new RefundPageVO();
        BeanUtils.copyProperties(refund, vo);
        return vo;
    }


    /**
     * 新增售后
     *
     * @param dto
     */
    public void save(RefundAddDTO dto) {
        Refund refund = new Refund();
        BeanUtils.copyProperties(dto, refund);
        refundDao.save(refund);
    }

    /**
     * 修改售后订单状态
     *
     * @param dto
     */
    public RefundPageVO update(RefundEditDTO dto) {
        Refund refund = refundDao.findFirstByRefundIdAndStoreId(dto.getRefundId(), dto.getStoreId());
        refund.setStatus(dto.getStatus());
        Refund entity = refundDao.save(refund);

        // 若果是同意退款调用对应的退款逻辑
        if (RefundStatus.AGREE.equals(dto.getStatus())) {
            // TODO
        }


        return this.toRefundPageVO(entity);
    }

    /**
     * 查找一个
     *
     * @param refundId
     * @return
     */
    public RefundPageVO one(Integer refundId) {
        return toRefundPageVO(this.findOne(refundId));
    }


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        Integer result = refundDao.delByIds(new ArrayList<>(ids));
    }


    /**
     * 根据售后id查找一个售后商品
     *
     * @param refundId
     * @return
     */
    public Refund findOne(Integer refundId) {
        Refund refund = refundDao.findById(refundId).orElse(null);
        Assert.notNull(refund, "售后不存在");
        return refund;
    }


    /**
     * 申请退款
     *
     * @param dto
     * @return
     */
    public RefundWebVO applyRefund(RefundAddDTO dto) {
        Refund refund = new Refund();
        Order order = orderService.findOne(dto.getOrderId());
        Refund refundEntity = refundDao.findFirstByOrderIdAndGoodsId(dto.getOrderId(), dto.getGoodsId());
        if (!ObjectUtils.isEmpty(refundEntity)) {
            throw new ValidateException(ExceptionMessage.AFTER_SALES_ORDER_ALREADY_EXISTS);
        }

        BeanUtils.copyProperties(dto, refund);

        OrderDetail orderDetail = orderDetailService.findByOrderIdAndGoodsId(dto.getOrderId(), dto.getGoodsId());
        if (dto.getGoodsCount() > orderDetail.getCount()) {
            throw new ValidateException(ExceptionMessage.REFUNDS_CANNOT_BE_GREATER_THAN_ORDERS);
        }

        if (order.getStatus().equals(OrderStatus.CANCEL) ||
                order.getStatus().equals(OrderStatus.CREATE) ||
                order.getStatus().equals(OrderStatus.REFUND)) {
            throw new ValidateException(ExceptionMessage.ORDER_STATUS_FAIL);
        }

        Store store = storeService.findOne(order.getStoreId());
        com.luwei.models.specification.Specification specification = specificationService.findOne(dto.getSpecificationId());
        refund.setSpecificationId(specification.getSpecificationId());
        refund.setGoodsCount(dto.getGoodsCount());
        refund.setGoodsName(orderDetail.getName());
        refund.setUserId(dto.getUserId());
        refund.setCreateTime(new Date());
        refund.setOrderOutNo(order.getOrderOutNo());
        refund.setConsignee(order.getUsername());
        refund.setPhone(order.getPhone());
        refund.setTotal(orderDetail.getPrice() * dto.getGoodsCount());
        refund.setStoreId(store.getStoreId());
        refund.setAddress(order.getAddress());
        refund.setStatus(RefundStatus.APPLY);
        refund.setCause(dto.getCause());
        refund.setOptionCause(dto.getOptionCause());
        Refund entity = refundDao.save(refund);
        return this.toRefundWebVO(orderDetail, entity, specification);
    }

    /**
     * @param refund
     * @return
     */
    public RefundWebVO toRefundWebVO(OrderDetail orderDetail, Refund refund, com.luwei.models.specification.Specification specification) {
        RefundWebVO vo = new RefundWebVO();
        BeanUtils.copyProperties(refund, vo);
        vo.setSpecificationId(specification.getSpecificationId());
        vo.setSpecificationName(specification.getName());
        vo.setGoodsPicture(specification.getPicture());
        vo.setGoodsName(orderDetail.getName());
        vo.setCreateTime(refund.getCreateTime());
        vo.setGoodsId(orderDetail.getGoodsId());
        return vo;
    }


}
