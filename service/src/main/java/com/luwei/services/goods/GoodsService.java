package com.luwei.services.goods;

import com.luwei.common.enums.status.SalesStatus;
import com.luwei.common.enums.type.CollectType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.enums.type.GoodsType;
import com.luwei.common.enums.type.SortType;
import com.luwei.models.goods.Goods;
import com.luwei.models.goods.GoodsDao;
import com.luwei.models.goods.category.GoodsCategory;
import com.luwei.models.store.Store;
import com.luwei.services.banner.cms.GoodsActivityVO;
import com.luwei.services.category.CategoryService;
import com.luwei.services.collect.CollectService;
import com.luwei.services.evaluate.EvaluateService;
import com.luwei.services.goods.cms.GoodsAddDTO;
import com.luwei.services.goods.cms.GoodsEditDTO;
import com.luwei.services.goods.cms.GoodsPageVO;
import com.luwei.services.goods.cms.GoodsQueryDTO;
import com.luwei.services.goods.web.GoodsDetailVO;
import com.luwei.services.goods.web.StoreGoodsListVO;
import com.luwei.services.specification.SpecificationService;
import com.luwei.services.specification.cms.SpecificationDetailVO;
import com.luwei.services.specification.web.SpecificationWebVO;
import com.luwei.services.store.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@Service
@Transactional
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private CategoryService categoryService;

    @Resource
    private EvaluateService evaluateService;

    @Resource
    private StoreService storeService;

    @Resource
    private CollectService collectService;

    @Resource
    private SpecificationService specificationService;

    /**
     * 分页
     *
     * @param pageable
     * @param query
     * @return
     */
    public Page<GoodsPageVO> page(Pageable pageable, GoodsQueryDTO query) {
        Specification<Goods> specification = (Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("storeId").as(Integer.class), query.getStoreId()));
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (!StringUtils.isEmpty(query.getName())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getName() + "%"));
            }

            if (!StringUtils.isEmpty(query.getCategoryId())) {
                list.add(criteriaBuilder.equal(root.get("categoryId").as(Integer.class), query.getCategoryId()));
            }

            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("goodsId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return goodsDao.findAll(specification, pageable).map(this::toGoodsPageVO);
    }

    /**
     * @param goods
     * @return
     */
    private GoodsPageVO toGoodsPageVO(Goods goods) {
        GoodsPageVO vo = new GoodsPageVO();
        BeanUtils.copyProperties(goods, vo);
        List<com.luwei.models.specification.Specification> specificationList = specificationService.findByGoodsId(goods.getGoodsId());
        List<SpecificationDetailVO> specificationPageVOList = new ArrayList<>();
        specificationList.forEach(e -> {
            SpecificationDetailVO o = new SpecificationDetailVO();
            BeanUtils.copyProperties(e, o);
            specificationPageVOList.add(o);
        });
        vo.setSpecificationList(specificationPageVOList);
        GoodsCategory category = categoryService.findOne(goods.getCategoryId());
        vo.setCategoryName(category.getName());
        return vo;
    }

    /**
     * 保存商品
     *
     * @param dto
     */
    public void save(GoodsAddDTO dto) {
        Store store = storeService.findOne(dto.getStoreId());
        Goods goods = new Goods();
        BeanUtils.copyProperties(dto, goods);
        goods.setPrice(0);
        goods.setSales(0);
        goods.setStatus(SalesStatus.OFF);
        goodsDao.save(goods);
    }

    /**
     * 修改商品上下架状态
     *
     * @param goodsId
     * @return
     */
    public GoodsPageVO update(Integer goodsId) {
        Goods goods = findOne(goodsId);
        if (goods.getStatus().equals(SalesStatus.OFF)) {
            goodsDao.updateGoodsUpDownStatus(goodsId, SalesStatus.ON, false);
        } else {
            goodsDao.updateGoodsUpDownStatus(goodsId, SalesStatus.OFF, false);
        }
        goodsDao.save(goods);
        return null;
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        Integer result = goodsDao.delByIds(new ArrayList<>(ids));
    }

    /**
     * 查找单个商品
     *
     * @param goodsId
     * @return
     */
    public Goods findOne(Integer goodsId) {
        Goods goods = goodsDao.findById(goodsId).orElse(null);
        Assert.notNull(goods, "商品不存在");
        return goods;
    }

    /**
     * 修改商品
     *
     * @param dto
     * @return
     */
    public GoodsPageVO update(GoodsEditDTO dto) {
        Goods goods = findOne(dto.getGoodsId());
        BeanUtils.copyProperties(dto, goods);
        Goods entity = goodsDao.save(goods);
        return this.toGoodsPageVO(goods);
    }

    /**
     * 推荐商品
     *
     * @param goodsId
     * @return
     */
    public GoodsPageVO recommend(Integer goodsId) {
        Goods goods = this.findOne(goodsId);
        if (GoodsType.RECOMMEND.equals(goods.getGoodsType())) {
            goodsDao.updateGoodsType(GoodsType.PRODUCT, goodsId);
        } else if (GoodsType.PRODUCT.equals(goods.getGoodsType())) {
            goodsDao.updateGoodsType(GoodsType.RECOMMEND, goodsId);
        }
        Goods entity = this.findOne(goodsId);
        return this.toGoodsPageVO(entity);
    }

    /**
     * 商品详情
     *
     * @param goodsId
     * @return
     */
    public Map detail(Integer goodsId) {
        Goods goods = this.findOne(goodsId);
        Map<String, String> map = new HashMap<>();
        map.put("detail", goods.getDetail());
        return map;
    }

    /**
     * 小程序端商品详情
     *
     * @param goodsId
     * @return
     */
    public GoodsDetailVO webGoodsDetail(Integer goodsId, Integer userId) {
        Goods goods = this.findOne(goodsId);
        Store store = storeService.findOne(goods.getStoreId());
        GoodsDetailVO vo = new GoodsDetailVO();
        BeanUtils.copyProperties(goods, vo);
        vo.setGoodsName(goods.getName());
        vo.setStoreName(store.getName());
        vo.setPhone(store.getPhone());
        vo.setCustomerId(store.getCustomerId());
        vo.setLogo(store.getLogo());
        // 规格列表
        List<SpecificationWebVO> specificationWebVOList = specificationService
                .findByGoodsId(goodsId)
                .stream()
                .map(this::toSpecificationWebVO)
                .collect(Collectors.toList());
        vo.setSpecificationList(specificationWebVOList);

        if (!ObjectUtils.isEmpty(userId)) {
            vo.setCollect(collectService.isCollect(userId, goodsId, CollectType.GOODS));
        } else {
            vo.setCollect(FlagType.DENY);
        }
        return vo;
    }

    /**
     * 转换
     *
     * @param specification
     * @return
     */
    private SpecificationWebVO toSpecificationWebVO(com.luwei.models.specification.Specification specification) {
        SpecificationWebVO vo = new SpecificationWebVO();
        BeanUtils.copyProperties(specification, vo);
        vo.setSpecificationName(specification.getName());
        return vo;
    }

    /**
     * 根据商家id查询
     *
     * @param storeId
     * @return
     */
    public List<StoreGoodsListVO> findStoreGoods(Integer storeId, SortType price, SortType time, Pageable pageable) {
        Specification<Goods> specification = (Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("storeId").as(Integer.class), storeId));

            if (!StringUtils.isEmpty(price)) {
                if (SortType.ASC.equals(price)) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price").as(Integer.class)));
                } else if (SortType.DESC.equals(price)) {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price").as(Integer.class)));
                }
            }
            if (!StringUtils.isEmpty(time)) {
                if (SortType.ASC.equals(time)) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createTime").as(Date.class)));
                } else if (SortType.DESC.equals(time)) {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime").as(Date.class)));
                }
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            return criteriaQuery.getRestriction();
        };
        return goodsDao.findAll(specification, pageable).map(this::toStorePageVO).getContent();
    }

    /**
     * 转换
     *
     * @param goods
     * @return
     */
    private StoreGoodsListVO toStorePageVO(Goods goods) {
        StoreGoodsListVO vo = new StoreGoodsListVO();
        BeanUtils.copyProperties(goods, vo);
        return vo;
    }


    /**
     * 猜你喜欢商品
     *
     * @param userId
     * @param pageable
     * @return
     */
    public Page<StoreGoodsListVO> guessLike(Integer userId, Pageable pageable) {

        if (ObjectUtils.isEmpty(userId)) {
            userId = -1;
        }
        List<Integer> goodsIdList = collectService.findCollectIds(CollectType.GOODS, userId);
        goodsIdList.add(-1);
        List<Integer> categoryIdList = goodsDao.findCategoryIdByGoodsId(goodsIdList);
        if (categoryIdList.size() == 0 || categoryIdList.size() < 7) {
            List<Integer> allCategoryId = categoryService.findAllCategoryId();
            categoryIdList.addAll(allCategoryId);
        }

        Specification<Goods> specification = (Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("status").as(SalesStatus.class), SalesStatus.ON));
            Expression<Integer> exp = root.get("categoryId");
            list.add(exp.in(categoryIdList));


            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            return criteriaQuery.getRestriction();
        };
        return goodsDao.findAll(specification, pageable).map(this::toStoreGoodsListVO);
    }

    /**
     * 推荐商品
     *
     * @param pageable
     * @return
     */
    public Page<StoreGoodsListVO> recommendGoods(Pageable pageable) {
        Specification<Goods> specification = (Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("status").as(SalesStatus.class), SalesStatus.ON));
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("goodsId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return goodsDao.findAll(specification, pageable).map(this::toStoreGoodsListVO);
    }


    /**
     * 转换
     *
     * @param goods
     * @return
     */
    public StoreGoodsListVO toStoreGoodsListVO(Goods goods) {
        StoreGoodsListVO vo = new StoreGoodsListVO();
        BeanUtils.copyProperties(goods, vo);
        return vo;
    }


    /**
     * 查询所有商品
     *
     * @return
     */
    public List<GoodsActivityVO> findAllSimpleGoods(String name) {
        return goodsDao.findAllGoods(name);
    }

    /**
     * 商品分类列表
     *
     * @param categoryId
     * @param pageable
     * @return
     */
    public Page<StoreGoodsListVO> categoryPage(Integer categoryId, Pageable pageable) {
        Specification<Goods> specification = (Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("categoryId").as(Integer.class), categoryId));
            list.add(criteriaBuilder.equal(root.get("status").as(SalesStatus.class), SalesStatus.ON));
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("goodsId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return goodsDao.findAll(specification, pageable).map(this::toStoreGoodsListVO);
    }

    /**
     * 商品分类列表
     *
     * @return
     */
    public List<Integer> findSoldOutGoods(SalesStatus salesStatus) {
        return goodsDao.findSoldOutGoods(salesStatus);
    }


}
