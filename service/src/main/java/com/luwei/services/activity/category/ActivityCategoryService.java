package com.luwei.services.activity.category;

import com.luwei.models.activity.category.ActivityCategory;
import com.luwei.models.activity.category.ActivityCategoryDao;
import com.luwei.services.activity.category.cms.ActivityCategoryAddDTO;
import com.luwei.services.activity.category.cms.ActivityCategoryEditDTO;
import com.luwei.services.activity.category.cms.ActivityCategoryPageVO;
import com.luwei.services.activity.category.web.ActivityCategoryListVO;
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
import java.util.stream.Collectors;

/**
 * @author Leone
 * @since 2018-08-09
 **/
@Slf4j
@Service
@Transactional
public class ActivityCategoryService {

    @Resource
    private ActivityCategoryDao activityCategoryDao;

    /**
     * 分页
     *
     * @param pageable
     * @param title
     * @return
     */
    public Page<ActivityCategoryPageVO> page(Pageable pageable, String title) {
        Specification<ActivityCategory> specification = (Root<ActivityCategory> root,
                                                         CriteriaQuery<?> criteriaQuery,
                                                         CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (!StringUtils.isEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + title + "%"));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("activityCategoryId")));
            return criteriaQuery.getRestriction();
        };
        return activityCategoryDao.findAll(specification, pageable).map(this::toActivityCategoryPageVO);
    }

    /**
     * @param activityCategory
     * @return
     */
    private ActivityCategoryPageVO toActivityCategoryPageVO(ActivityCategory activityCategory) {
        ActivityCategoryPageVO vo = new ActivityCategoryPageVO();
        BeanUtils.copyProperties(activityCategory, vo);
        return vo;
    }

    /**
     * 保存
     *
     * @param dto
     */
    public void save(ActivityCategoryAddDTO dto) {
        ActivityCategory activityCategory = new ActivityCategory();
        BeanUtils.copyProperties(dto, activityCategory);
        activityCategory.setSeries(0);
        activityCategoryDao.save(activityCategory);
    }

    /**
     * @param dto
     * @return
     */
    public ActivityCategoryPageVO update(ActivityCategoryEditDTO dto) {
        ActivityCategory activityCategory = this.findOne(dto.getActivityCategoryId());
        BeanUtils.copyProperties(dto, activityCategory);
        ActivityCategory entity = activityCategoryDao.save(activityCategory);
        return toActivityCategoryPageVO(entity);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        activityCategoryDao.delByIds(new ArrayList<>(ids));
    }

    /**
     * 查找单个
     *
     * @param activityCategoryId
     * @return
     */
    public ActivityCategoryPageVO one(Integer activityCategoryId) {
        return this.toActivityCategoryPageVO(this.findOne(activityCategoryId));
    }

    /**
     * 获取单个
     *
     * @param activityCategoryId
     * @return
     */
    public ActivityCategory findOne(Integer activityCategoryId) {
        ActivityCategory activityCategory = activityCategoryDao.findById(activityCategoryId).orElse(null);
        Assert.notNull(activityCategory, "活动分类不存在");
        return activityCategory;
    }

    /**
     * 获取活动分类列表
     *
     * @return
     */
    public List<ActivityCategoryListVO> list() {
        return activityCategoryDao.findActivityCategoriesByDeletedIsFalse().stream().map(
                this::toActivityCategoryListVO).collect(Collectors.toList());
    }

    /**
     * 转换
     *
     * @param activityCategory
     * @return
     */
    public ActivityCategoryListVO toActivityCategoryListVO(ActivityCategory activityCategory) {
        ActivityCategoryListVO vo = new ActivityCategoryListVO();
        BeanUtils.copyProperties(activityCategory, vo);
        return vo;
    }


}
