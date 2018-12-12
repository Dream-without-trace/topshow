package com.luwei.services.banner;

import com.luwei.common.enums.status.BannerStatus;
import com.luwei.common.enums.type.BannerType;
import com.luwei.common.enums.type.CollectType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.activity.Activity;
import com.luwei.models.banner.Banner;
import com.luwei.models.banner.BannerDao;
import com.luwei.models.goods.Goods;
import com.luwei.services.activity.ActivityService;
import com.luwei.services.banner.cms.*;
import com.luwei.services.banner.web.BannerWebVO;
import com.luwei.services.goods.GoodsService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Leone
 * @since 2018-08-02
 **/
@Slf4j
@Service
@Transactional
public class BannerService {

    @Resource
    private BannerDao bannerDao;

    @Resource
    private ActivityService activityService;

    @Resource
    private GoodsService goodsService;

    /**
     * 分页
     *
     * @param pageable
     * @param query
     * @return
     */
    public Page<BannerPageVO> page(Pageable pageable, BannerQueryDTO query) {
        Specification<Banner> specification = (Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (null != (query.getTitle())) {
                list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + query.getTitle() + "%"));
            }
            if (null != (query.getType())) {
                list.add(criteriaBuilder.equal(root.get("type").as(BannerType.class), query.getType()));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("bannerId")));
            return criteriaQuery.getRestriction();
        };
        return bannerDao.findAll(specification, pageable).map(this::toBannerPageVO);
    }

    /**
     * @param query
     * @return
     */
    public List<BannerPageVO> list(Object query) {
        Specification<Banner> specification = (Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted"), 0));
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("bannerId")));
            return criteriaQuery.getRestriction();
        };
        return bannerDao.findAll(specification).stream().map(this::toBannerPageVO).collect(Collectors.toList());
    }

    /**
     * 保存
     *
     * @param dto
     */
    public void save(BannerAddDTO dto) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(dto, banner);
        if (ObjectUtils.isEmpty(dto.getRemark())) {
            banner.setRemark("");
        }


        if (dto.getType().equals(BannerType.ACTIVITY)) {
            Activity activity = activityService.findOne(dto.getId());
            banner.setName(activity.getTitle());
            if (ObjectUtils.isEmpty(dto.getPicture())) {
                banner.setPicture(activity.getPicture());
            }
        }
        if (dto.getType().equals(BannerType.GOODS)) {
            Goods goods = goodsService.findOne(dto.getId());
            banner.setName(goods.getName());
            if (ObjectUtils.isEmpty(dto.getPicture())) {
                banner.setPicture(goods.getPicture());
            }
        }
        Banner entity = bannerDao.save(banner);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    public BannerPageVO update(BannerEditDTO dto) {
        Banner banner = findOne(dto.getBannerId());
        BeanUtils.copyProperties(dto, banner);

        if (dto.getType().equals(BannerType.ACTIVITY)) {
            Activity activity = activityService.findOne(dto.getId());
            banner.setName(activity.getTitle());
        }

        if (dto.getType().equals(BannerType.GOODS)) {
            Goods goods = goodsService.findOne(dto.getId());
            banner.setName(goods.getName());
        }

        Banner entity = bannerDao.save(banner);
        return toBannerPageVO(entity);
    }

    /**
     * @param bannerId
     * @return
     */
    public BannerPageVO one(Integer bannerId) {
        return toBannerPageVO(this.findOne(bannerId));
    }

    /**
     * 查找一个
     *
     * @param bannerId
     * @return
     */
    public Banner findOne(Integer bannerId) {
        Banner banner = bannerDao.findById(bannerId).orElse(null);
        Assert.notNull(banner, "banner不存在");
        return banner;
    }

    /**
     * 删除
     *
     * @param bannerIds
     */
    public void delete(Set<Integer> bannerIds) {
        List<Integer> listIds = new ArrayList<>(bannerIds);
        List<Banner> bannerList = bannerDao.findAllById(listIds);
        if (listIds.size() != bannerIds.size()) {
            throw new ValidateException(ExceptionMessage.DELETE_IDS_FAIL);
        }
        bannerList.forEach(e -> e.setDeleted(true));
        bannerDao.saveAll(bannerList);
    }

    /**
     * @param banner
     * @return
     */
    public BannerPageVO toBannerPageVO(Banner banner) {
        BannerPageVO vo = new BannerPageVO();
        BeanUtils.copyProperties(banner, vo);
        return vo;
    }

    /**
     * 查询商品或活动列表
     *
     * @param name
     * @param type
     * @return
     */
    public List<GoodsActivityVO> searchGoodsOrActivity(String name, CollectType type) {
        if (StringUtils.isEmpty(name)) {
            name = "%%";
        } else {
            name = "%" + name + "%";
        }
        if (type.equals(CollectType.GOODS)) {
            return goodsService.findAllSimpleGoods(name);
        } else if (type.equals(CollectType.ACTIVITY)) {
            return activityService.findAllSimpleActivity(name);
        }
        return null;
    }

    /**
     * web端分页
     *
     * @param pageable
     * @return
     */
    public Page<BannerWebVO> homePage(BannerType type, Integer areaId, Pageable pageable) {
        final List<Integer> areaIds = new ArrayList<>();
        Assert.notNull(type,"类型为空！");
        if (!type.equals(BannerType.ACTIVITY)) {
            if (!ObjectUtils.isEmpty(areaId)) {
                areaIds.addAll(activityService.findByAreaId(areaId));
                areaIds.add(-1);
            }
        }
        Specification<Banner> specification = (Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("status").as(BannerStatus.class), BannerStatus.UP));
            list.add(criteriaBuilder.equal(root.get("type").as(BannerType.class), type));
            if (!ObjectUtils.isEmpty(areaIds)) {
                Expression<Integer> exp = root.get("id");
                list.add(exp.in(areaIds));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("weight")));
            return criteriaQuery.getRestriction();
        };
        return bannerDao.findAll(specification, pageable).map(this::toBannerWebVO);
    }

    /**
     * @param banner
     * @return
     */
    private BannerWebVO toBannerWebVO(Banner banner) {
        BannerWebVO vo = new BannerWebVO();
        BeanUtils.copyProperties(banner, vo);
        return vo;
    }

    /**
     * 显示隐藏
     *
     * @param bannerId
     * @return
     */
    public BannerPageVO showHidden(Integer bannerId) {
        Banner banner = this.findOne(bannerId);
        if (banner.getStatus().equals(BannerStatus.DOWN)) {
            banner.setStatus(BannerStatus.UP);
        } else if (banner.getStatus().equals(BannerStatus.UP)) {
            banner.setStatus(BannerStatus.DOWN);
        }
        Banner entity = bannerDao.save(banner);
        return toBannerPageVO(entity);
    }
}

