package com.luwei.services.specification;

import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.goods.Goods;
import com.luwei.models.specification.Specification;
import com.luwei.models.specification.SpecificationDao;
import com.luwei.services.goods.GoodsService;
import com.luwei.services.property.PropertyService;
import com.luwei.services.specification.cms.SpecificationAddDTO;
import com.luwei.services.specification.cms.SpecificationEditDTO;
import com.luwei.services.specification.cms.SpecificationPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-07
 **/
@Slf4j
@Service
@Transactional
public class SpecificationService {

    @Resource
    private SpecificationDao specificationDao;

    @Resource
    private GoodsService goodsService;

    @Resource
    private PropertyService propertyService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 分页
     *
     * @param pageable
     * @param goodsId
     * @return
     */
    public Page<SpecificationPageVO> page(Pageable pageable, Integer goodsId) {
        org.springframework.data.jpa.domain.Specification<Specification> specification =
                (Root<Specification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
                    if (!ObjectUtils.isEmpty(goodsId)) {
                        list.add(criteriaBuilder.equal(root.get("goodsId").as(Integer.class), goodsId));
                    }
                    Predicate[] predicates = new Predicate[list.size()];
                    criteriaQuery.where(criteriaBuilder.and(list.toArray(predicates)));
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("specificationId")));
                    return criteriaQuery.getRestriction();
                };
        return specificationDao.findAll(specification, pageable).map(this::toSpecificationPageVO);
    }

    /**
     * 转换
     *
     * @param specification
     * @return
     */
    private SpecificationPageVO toSpecificationPageVO(Specification specification) {
        SpecificationPageVO vo = new SpecificationPageVO();
        BeanUtils.copyProperties(specification, vo);
        return vo;
    }

    /**
     * 添加
     *
     * @param dto
     */
    public void save(SpecificationAddDTO dto) {
        Goods goods = goodsService.findOne(dto.getGoodsId());
        Specification specification = new Specification();
        if (goods.getPrice().equals(0) || goods.getPrice() > dto.getPrice()) {
            goods.setPrice(dto.getPrice());
        }
        BeanUtils.copyProperties(dto, specification);
        Specification entity = specificationDao.save(specification);
    }


    /**
     * 批量删除
     *
     * @param bannerIds
     */
    public void delete(Set<Integer> bannerIds) {
        List<Integer> listIds = new ArrayList<>(bannerIds);
        List<Specification> bannerList = specificationDao.findAllById(listIds);
        if (listIds.size() != bannerIds.size()) {
            throw new ValidateException(ExceptionMessage.DELETE_IDS_FAIL);
        }
        bannerList.forEach(e -> e.setDeleted(true));
        specificationDao.saveAll(bannerList);
    }

    /**
     * 查找一个
     *
     * @param specificationId
     * @return
     */
    public Specification findOne(Integer specificationId) {
        Specification specification = specificationDao.findById(specificationId).orElse(null);
        Assert.notNull(specification, "规格不能为空");
        return specification;
    }


    /**
     * 列表
     *
     * @param goodsId
     * @return
     */
    public List<Specification> findByGoodsId(Integer goodsId) {
        return specificationDao.findByGoodsId(goodsId);
    }


    /**
     * 修改
     *
     * @param dto
     * @return
     */
    public SpecificationPageVO update(SpecificationEditDTO dto) {
        Specification specification = this.findOne(dto.getSpecificationId());
        BeanUtils.copyProperties(dto, specification);
        Specification entity = specificationDao.save(specification);
        return this.toSpecificationPageVO(entity);
    }

    /**
     * 减库存
     *
     * @param specificationId
     * @param count
     */
    @Transactional
    public void inventoryReduction(Integer specificationId, Integer count) {
        Specification specification = this.findOne(specificationId);
        Integer nowCount = specification.getInventory();
        if (nowCount < count) {
            throw new ValidateException(ExceptionMessage.INVENTORY_SHORTAGE);
        }
        specificationDao.updateInventory(nowCount - count, specificationId);
    }

}
