package com.luwei.services.integral.bill;

import com.luwei.common.enums.type.BillType;
import com.luwei.models.integralbill.IntegralBill;
import com.luwei.models.integralbill.IntegralBillDao;
import com.luwei.services.integral.bill.cms.IntegralBillPageVO;
import com.luwei.services.integral.bill.web.IntegralBillDTO;
import com.luwei.services.integral.bill.web.IntegralBillWebPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Slf4j
@Service
@Transactional
public class IntegralBillService {

    @Resource
    private IntegralBillDao integralBillDao;

    /**
     * 分页
     *
     * @param pageable
     * @param phone
     * @param start
     * @param end
     * @return
     */
//    @Cacheable("integral.bill.page")
    public Page<IntegralBillPageVO> page(Pageable pageable, String phone, Date start, Date end) {
        Specification<IntegralBill> specification = (Root<IntegralBill> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            if (!StringUtils.isEmpty(phone)) {
                list.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }

            if (!Objects.isNull(start) && !Objects.isNull(end)) {
                list.add(criteriaBuilder.between(root.get("createTime"), start, end));
            }

            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("integralBillId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return integralBillDao.findAll(specification, pageable).map(this::toStorePageVO);
    }

    /**
     * 转换
     *
     * @param integralBill
     * @return
     */
    private IntegralBillPageVO toStorePageVO(IntegralBill integralBill) {
        IntegralBillPageVO vo = new IntegralBillPageVO();
        BeanUtils.copyProperties(integralBill, vo);
        vo.setTime(integralBill.getCreateTime());
        return vo;
    }


    /**
     * web端分页
     *
     * @param pageable
     * @param userId
     * @param billType
     * @return
     */
    public Page<IntegralBillWebPageVO> page(Pageable pageable, Integer userId, BillType billType) {
        return integralBillDao.findIntegralBillsByBillTypeAndUserIdOrderByIntegralBillIdDesc(billType, userId, pageable).map(this::toIntegralBillPageVO);
    }

    /**
     * 转换
     *
     * @param integralBill
     * @return
     */
    public IntegralBillWebPageVO toIntegralBillPageVO(IntegralBill integralBill) {
        IntegralBillWebPageVO vo = new IntegralBillWebPageVO();
        BeanUtils.copyProperties(integralBill, vo);
        return vo;
    }

    /**
     * 保存
     *
     * @param bill
     * @return
     */
    public IntegralBill save(IntegralBill bill) {
        return integralBillDao.save(bill);
    }

    /**
     * 保存
     *
     * @param dto
     */
    public void save(IntegralBillDTO dto) {
        IntegralBill bill = new IntegralBill();
        BeanUtils.copyProperties(dto, bill);
        integralBillDao.save(bill);
    }

}
