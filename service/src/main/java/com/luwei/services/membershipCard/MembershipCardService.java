package com.luwei.services.membershipCard;

import com.luwei.common.Response;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.enums.type.EvaluateType;
import com.luwei.common.utils.DateTimeUtis;
import com.luwei.models.activity.Activity;
import com.luwei.models.activity.category.ActivityCategory;
import com.luwei.models.activity.series.ActivitySeries;
import com.luwei.models.area.Area;
import com.luwei.models.membershipcard.MembershipCard;
import com.luwei.models.membershipcard.MembershipCardDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.membershipcard.order.MembershipCardOrderDao;
import com.luwei.models.shop.Shop;
import com.luwei.services.activity.cms.ActivityEditDTO;
import com.luwei.services.activity.cms.ActivityPageVO;
import com.luwei.services.area.AreaService;
import com.luwei.services.evaluate.cms.EvaluateCmsVO;
import com.luwei.services.membershipCard.cms.MembershipCardDTO;
import com.luwei.services.membershipCard.order.MembershipCardOrderService;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @program: topshow
 * @description: 会员卡Service
 * @author: ZhangHongJie
 * @create: 2018-12-03 16:24
 **/
@Slf4j
@Service
@Transactional
public class MembershipCardService {

    @Resource
    private MembershipCardDao membershipCardDao;
    @Resource
    private AreaService areaService;
    @Resource
    private MembershipCardOrderDao membershipCardOrderDao;

    /**
     * 保存
     * @param dto
     */
    public Response save(@Valid MembershipCardDTO dto) {
        MembershipCard membershipCard = new MembershipCard();
        BeanUtils.copyProperties(dto, membershipCard);
        membershipCardDao.save(membershipCard);
        return Response.build(2000,"成功！",membershipCard);
    }

    public Response delete(Set<Integer> ids) {
        membershipCardDao.delByIds(new ArrayList<>(ids));
        return Response.success("成功！");
    }

    public MembershipCard findOne(Integer membershipCardDaoId) {
        Assert.isTrue(membershipCardDaoId != null && membershipCardDaoId != 0,"会员卡ID参数为空！");
        MembershipCard membershipCard = membershipCardDao.findById(membershipCardDaoId).orElse(null);
        Assert.notNull(membershipCard, "会员卡不存在");
        return membershipCard;
    }

    public Page<MembershipCardDTO> page(Pageable pageable, String title) {
        Specification<MembershipCard> specification = (Root<MembershipCard> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            if (!StringUtils.isEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("membershipCardId")));
            return criteriaQuery.getRestriction();
        };
        return membershipCardDao.findAll(specification, pageable).map(this::toMembershipCardDTO);
    }

    private MembershipCardDTO toMembershipCardDTO(MembershipCard membershipCard) {
        MembershipCardDTO vo = new MembershipCardDTO();
        BeanUtils.copyProperties(membershipCard, vo);
        Integer areaId = membershipCard.getAreaId();
        Area area = areaService.findOne(areaId);
        vo.setAreaName(area.getName());
        Date zeroDate = DateTimeUtis.getZeroDate();
        int totalSales = 0;
        int todaySales = 0;
        List<MembershipCardOrder> membershipCardOrderList = membershipCardOrderDao.
                findAllByMembershipCardIdAndStatusAndDeletedIsFalseOrderByPayTimeDesc(membershipCard.getMembershipCardId(), MembershipCardOrderStatus.PAY);
        if (membershipCardOrderList != null && membershipCardOrderList.size()>0) {
            totalSales = membershipCardOrderList.size();
            for (MembershipCardOrder membershipCardOrder:membershipCardOrderList) {
                if (membershipCardOrder != null) {
                    Date payTime = membershipCardOrder.getPayTime();
                    if(payTime != null && payTime.getTime() > zeroDate.getTime() && payTime.getTime() < (zeroDate.getTime()+86400000)){
                        todaySales = todaySales + 1;
                    }
                }
            }
        }
        vo.setTodaySales(todaySales);
        vo.setTotalSales(totalSales);
        return vo;
    }

}
