package com.luwei.services.store;

import com.luwei.common.enums.status.StoreStatus;
import com.luwei.common.enums.type.SortType;
import com.luwei.common.utils.RandomUtil;
import com.luwei.models.store.Store;
import com.luwei.models.store.StoreDao;
import com.luwei.services.goods.GoodsService;
import com.luwei.services.store.cms.StoreAddDTO;
import com.luwei.services.store.cms.StoreEditDTO;
import com.luwei.services.store.cms.StorePageVO;
import com.luwei.services.store.web.StoreWebDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-12
 **/
@Slf4j
@Service
@Transactional
@CacheConfig(cacheNames = "store.page")
public class StoreService {

    @Resource
    private StoreDao storeDao;

    @Resource
    private GoodsService goodsService;

    /**
     * 分页
     *
     * @param pageable
     * @param name
     * @return
     */
//    @Cacheable
    public Page<StorePageVO> page(Pageable pageable, String name) {
        Specification<Store> specification = (Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (!StringUtils.isEmpty(name)) {
                list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("storeId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return storeDao.findAll(specification, pageable).map(this::toStorePageVO);
    }


    /**
     * 转换
     *
     * @param store
     * @return
     */
    private StorePageVO toStorePageVO(Store store) {
        StorePageVO vo = new StorePageVO();
        BeanUtils.copyProperties(store, vo);
        vo.setCreateTime(store.getCreateTime());
        return vo;
    }


    /**
     * 添加
     *
     * @param dto
     */
    @CacheEvict(allEntries = true)
    public void save(StoreAddDTO dto) {
        Store store = new Store();
        BeanUtils.copyProperties(dto, store);
        store.setStatus(StoreStatus.OFF);
        store.setCustomerId(1 + RandomUtil.getNum(7));
        storeDao.save(store);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @CachePut
    public StorePageVO update(StoreEditDTO dto) {
        Store store = this.findOne(dto.getStoreId());
        BeanUtils.copyProperties(dto, store);
        storeDao.save(store);
        return this.toStorePageVO(store);
    }

    /**
     * 获取单个
     *
     * @param storeId
     * @return
     */
    public StorePageVO one(Integer storeId) {
        return this.toStorePageVO(this.findOne(storeId));
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        Integer result = storeDao.delByIds(new ArrayList<>(ids));
    }

    /**
     * 查找单个
     *
     * @param storeId
     * @return
     */
    public Store findOne(Integer storeId) {
        Store store = storeDao.findById(storeId).orElse(null);
        Assert.notNull(store, "商家不存在");
        return store;
    }


    // -----------------------------cms-----------------------------------


    // -----------------------------web-----------------------------------

    /**
     * 查找商家商品
     *
     * @param storeId
     * @return
     */
    public StoreWebDetailVO storeDetail(Integer storeId, SortType price, SortType time, Pageable pageable) {
        Store store = this.findOne(storeId);
        StoreWebDetailVO vo = new StoreWebDetailVO();
        BeanUtils.copyProperties(store, vo);
        vo.setStoreGoodsList(goodsService.findStoreGoods(storeId, price, time, pageable));
        return vo;
    }

    /**
     * 根据客服id查找客服
     *
     * @param customerId
     * @return
     */
    public Store findByCustomerId(String customerId) {
        return storeDao.findFirstByCustomerId(customerId);
    }


}

