package com.luwei.services.activity.series;

import com.luwei.models.activity.series.ActivitySeries;
import com.luwei.models.activity.series.ActivitySeriesDao;
import com.luwei.services.activity.series.cms.ActivitySeriesAddDTO;
import com.luwei.services.activity.series.cms.ActivitySeriesEditDTO;
import com.luwei.services.activity.series.cms.ActivitySeriesPageVO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-09
 **/
@Slf4j
@Service
@Transactional
public class ActivitySeriesService {

    @Resource
    private ActivitySeriesDao activitySeriesDao;

    /**
     * 分页
     *
     * @param pageable
     * @param title
     * @return
     */
    public Page<ActivitySeriesPageVO> page(Pageable pageable, String title) {
        Specification<ActivitySeries> specification = (Root<ActivitySeries> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (!StringUtils.isEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + title + "%"));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("activitySeriesId")));
            return criteriaQuery.getRestriction();
        };
        return activitySeriesDao.findAll(specification, pageable).map(this::toActivitySeriesPageVO);
    }

    /**
     * @param activitySeries
     * @return
     */
    private ActivitySeriesPageVO toActivitySeriesPageVO(ActivitySeries activitySeries) {
        ActivitySeriesPageVO vo = new ActivitySeriesPageVO();
        BeanUtils.copyProperties(activitySeries, vo);
        return vo;
    }

    /**
     * 保存
     *
     * @param dto
     */
    public void save(ActivitySeriesAddDTO dto) {
        ActivitySeries activitySeries = new ActivitySeries();
        BeanUtils.copyProperties(dto, activitySeries);
        activitySeries.setCount(0);
        activitySeriesDao.save(activitySeries);
    }

    public ActivitySeriesPageVO update(ActivitySeriesEditDTO dto) {
        ActivitySeries activitySeries = this.findOne(dto.getActivitySeriesId());
        BeanUtils.copyProperties(dto, activitySeries);
        ActivitySeries entity = activitySeriesDao.save(activitySeries);
        return toActivitySeriesPageVO(entity);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        activitySeriesDao.delByIds(new ArrayList<>(ids));
    }

    /**
     * 查找单个
     *
     * @param activityCategoryId
     * @return
     */
    public ActivitySeriesPageVO one(Integer activityCategoryId) {
        return this.toActivitySeriesPageVO(this.findOne(activityCategoryId));
    }

    /**
     * 获取单个
     *
     * @param activitySeriesId
     * @return
     */
    public ActivitySeries findOne(Integer activitySeriesId) {
        ActivitySeries activityCategory = activitySeriesDao.findById(activitySeriesId).orElse(null);
        Assert.notNull(activityCategory, "活动系列不存在");
        return activityCategory;
    }

    /**
     * 查找全部
     *
     * @return
     */
    public List<ActivitySeries> findAll() {
        return activitySeriesDao.findAll();
    }

    /**
     * 保存活动
     *
     * @param activitySeries
     */
    public void save(ActivitySeries activitySeries) {
        activitySeriesDao.save(activitySeries);
    }

    /**
     * 批量保存活动
     *
     * @param activitySeries
     */
    public void saveAll(List<ActivitySeries> activitySeries) {
        activitySeriesDao.saveAll(activitySeries);
    }

}
