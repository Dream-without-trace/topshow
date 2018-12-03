package com.luwei.services.activity;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.luwei.common.enums.status.ActivityStatus;
import com.luwei.common.enums.type.*;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.activity.Activity;
import com.luwei.models.activity.ActivityDao;
import com.luwei.models.activity.category.ActivityCategory;
import com.luwei.models.activity.order.ActivityOrder;
import com.luwei.models.activity.series.ActivitySeries;
import com.luwei.models.area.Area;
import com.luwei.models.evaluate.Evaluate;
import com.luwei.models.user.User;
import com.luwei.services.activity.category.ActivityCategoryService;
import com.luwei.services.activity.cms.ActivityAddDTO;
import com.luwei.services.activity.cms.ActivityEditDTO;
import com.luwei.services.activity.cms.ActivityPageVO;
import com.luwei.services.activity.order.ActivityOrderService;
import com.luwei.services.activity.series.ActivitySeriesService;
import com.luwei.services.activity.series.web.ActivitySeriesListVO;
import com.luwei.services.activity.series.web.SeriesListVO;
import com.luwei.services.activity.web.*;
import com.luwei.services.area.AreaService;
import com.luwei.services.area.web.CityListVO;
import com.luwei.services.banner.cms.GoodsActivityVO;
import com.luwei.services.collect.CollectService;
import com.luwei.services.coupon.web.CouponOrderListVO;
import com.luwei.services.evaluate.EvaluateService;
import com.luwei.services.evaluate.cms.EvaluateCmsVO;
import com.luwei.services.evaluate.web.EvaluateWebListVO;
import com.luwei.services.user.UserService;
import com.luwei.services.user.coupon.UserCouponService;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leone
 * @since 2018-08-09
 **/
@Slf4j
@Service
@Transactional
public class ActivityService {

    @Resource
    private ActivityDao activityDao;

    @Resource
    private AreaService areaService;

    @Resource
    private UserService userService;

    @Resource
    private CollectService collectService;

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private ActivityOrderService activityOrderService;

    @Resource
    private EvaluateService evaluateService;

    @Resource
    private ActivityCategoryService activityCategoryService;

    @Resource
    private ActivitySeriesService activitySeriesService;

    /**
     * cms分页
     *
     * @param pageable
     * @param title
     * @return
     */
    public Page<ActivityPageVO> page(Pageable pageable, String title, Integer activityCategoryId) {
        Specification<Activity> specification = (Root<Activity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            if (!StringUtils.isEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }

            if (!StringUtils.isEmpty(activityCategoryId)) {
                list.add(criteriaBuilder.equal(root.get("activityCategoryId").as(Integer.class), activityCategoryId));
            }

            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("activityId")));
            return criteriaQuery.getRestriction();
        };
        return activityDao.findAll(specification, pageable).map(this::toActivityPageVO);
    }

    /**
     * 转换
     *
     * @param activity
     * @return
     */
    private ActivityPageVO toActivityPageVO(Activity activity) {
        ActivityPageVO vo = new ActivityPageVO();
        BeanUtils.copyProperties(activity, vo);
        String previousUrls = activity.getPrevious();
        if (!StringUtils.isEmpty(previousUrls)) {
            List<String> list = new ArrayList<>(Arrays.asList(previousUrls.split(",")));
            Area city = areaService.findOne(activity.getAreaId());
            Area province = areaService.findOne(city.getParentId());
            vo.setAreaId(Arrays.asList(city.getAreaId(), province.getAreaId()));
            vo.setPrevious(list);
        }

        ActivityCategory activityCategory = activityCategoryService.findOne(activity.getActivityCategoryId());
        ActivitySeries activitySeries = activitySeriesService.findOne(activity.getActivitySeriesId());
        vo.setCategory(activityCategory.getTitle());
        vo.setSeries(activitySeries.getTitle());
        // 评价
        List<EvaluateCmsVO> evaluateList = evaluateService.cmsVOList(activity.getActivityId(), EvaluateType.ACTIVITY);
        vo.setEvaluate(evaluateList);
        return vo;
    }

    /**
     * 添加活动
     *
     * @param dto
     */
    public void save(ActivityAddDTO dto) {
        Activity activity = new Activity();

        Calendar calendar = Calendar.getInstance();
        if (dto.getStartTime().getTime() > dto.getEndTime().getTime()) {
            throw new ValidateException(ExceptionMessage.THE_START_TIME_CANNOT_BE_GREATER_THAN_THE_END_TIME);
        }

        if (dto.getStartTime().getTime() < calendar.getTime().getTime()) {
            throw new ValidateException(ExceptionMessage.THE_START_TIME_CANNOT_BE_LESS_THAN_THE_CURRENT_TIME);
        }

        BeanUtils.copyProperties(dto, activity);
        Area city = areaService.findOne(dto.getAreaId());
        Area province = areaService.findOne(city.getParentId());
        activity.setCity(city.getName());
        activity.setProvince(province.getName());
        activity.setAddress(dto.getAddress());
        activity.setDeleted(false);
        long time = new Date().getTime();
        if (dto.getStartTime().getTime() > time) {
            activity.setStatus(ActivityStatus.NOT_START);
        } else if (dto.getStartTime().getTime() < time && dto.getEndTime().getTime() > time) {
            activity.setStatus(ActivityStatus.UNDERWAY);
        } else {
            activity.setStatus(ActivityStatus.OVER);
        }

        String join = Joiner.on(",").join(dto.getPrevious());
        activity.setPrevious(join);

        if (dto.getPrice() < 1) {
            activity.setTicketType(TicketType.FREE);
        } else {
            activity.setTicketType(TicketType.VIP);
        }
        activityDao.save(activity);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    public ActivityPageVO update(ActivityEditDTO dto) {
        Activity activity = this.findOne(dto.getActivityId());
        BeanUtils.copyProperties(dto, activity);
        long time = new Date().getTime();
        if (dto.getStartTime().getTime() > time) {
            activity.setStatus(ActivityStatus.NOT_START);
        } else if (dto.getStartTime().getTime() < time && dto.getEndTime().getTime() > time) {
            activity.setStatus(ActivityStatus.UNDERWAY);
        } else {
            activity.setStatus(ActivityStatus.OVER);
        }

        activityDao.save(activity);

        Area city = areaService.findOne(dto.getAreaId());
        Area province = areaService.findOne(city.getParentId());
        activity.setCity(city.getName());
        activity.setProvince(province.getName());
        activity.setAddress(dto.getAddress());

        String join = Joiner.on(",").join(dto.getPrevious());
        activity.setPrevious(join);

        if (dto.getPrice() < 1) {
            activity.setTicketType(TicketType.FREE);
        } else {
            activity.setTicketType(TicketType.VIP);
        }

        return this.toActivityPageVO(activity);
    }

    /**
     * 查找一个
     *
     * @param activityId
     * @return
     */
    public ActivityPageVO one(Integer activityId) {
        return this.toActivityPageVO(this.findOne(activityId));
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        activityDao.delByIds(new ArrayList<>(ids));
    }

    /**
     * 查找一个
     *
     * @param activityId
     * @return
     */
    public Activity findOne(Integer activityId) {
        Activity activity = activityDao.findById(activityId).orElse(null);
        Assert.notNull(activity, "活动不存在");
        return activity;
    }

    // ----------------------------web--------------------------------

    /**
     * 精品活动页面
     *
     * @param areaId
     * @return
     */
    public HandpickVO handpick(Integer areaId, Integer activityCategoryId) {
        HandpickVO vo = new HandpickVO();
        Area city = areaService.findOne(areaId);

        vo.setCity(city.getName());

        // 活动介绍
        vo.setIntroductionList(this.introductionList(city.getAreaId(), activityCategoryId));

        // 亮点活动
        vo.setLightSpotList(this.lightSpotList(city.getAreaId(), activityCategoryId));

        // 本周推送
        vo.setPushList(this.pushList(city.getAreaId(), activityCategoryId));

        return vo;
    }

    /**
     * 强力推荐
     *
     * @param areaId
     * @return
     */
    private List<ActivityWebListVO> introductionList(Integer areaId, Integer activityCategoryId) {
        List<Activity> activityList = activityDao.findActivitiesByAreaIdAndRecommendAndStatusAndActivityCategoryIdAndDeletedIsFalse(areaId, FlagType.RIGHT, ActivityStatus.NOT_START, activityCategoryId);
        List<ActivityWebListVO> result = activityList.stream().map(this::toActivityWebListVO).collect(Collectors.toList());
        if (result.size() > 20) {
            return result.subList(0, 20);
        }
        return result;
    }

    /**
     * 城中热事
     *
     * @param areaId
     * @return
     */
    private List<ActivityWebListVO> lightSpotList(Integer areaId, Integer activityCategoryId) {
        List<Activity> activityList = activityDao.findActivitiesByAreaIdAndStatusAndActivityTypeAndActivityCategoryIdAndDeletedIsFalse(areaId, ActivityStatus.NOT_START, ActivityType.HOT, activityCategoryId);
        List<ActivityWebListVO> result = activityList.stream().map(this::toActivityWebListVO).collect(Collectors.toList());
        if (result.size() > 20) {
            return result.subList(0, 20);
        }
        return result;
    }

    /**
     * 猜你喜欢
     *
     * @param areaId
     * @return
     */
    private List<ActivityWebListVO> pushList(Integer areaId, Integer activityCategoryId) {
        List<Activity> activityList = activityDao.findActivitiesByAreaIdAndStatusAndActivityTypeAndActivityCategoryIdAndDeletedIsFalse(areaId, ActivityStatus.NOT_START, ActivityType.LIKE, activityCategoryId);
        List<ActivityWebListVO> result = activityList.stream().map(this::toActivityWebListVO).collect(Collectors.toList());
        if (result.size() > 20) {
            return result.subList(0, 20);
        }
        return result;
    }

    /**
     * 首页列表
     *
     * @param areaId             地区id
     * @param activityCategoryId 活动分类id
     * @return
     */
    public Page<ActivityWebListVO> indexActivityList(Integer areaId, Integer activityCategoryId, Pageable pageable) {
        Specification<Activity> specification = (Root<Activity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("areaId").as(Integer.class), areaId));
            list.add(criteriaBuilder.equal(root.get("activityCategoryId").as(Integer.class), activityCategoryId));
            list.add(criteriaBuilder.equal(root.get("status").as(ActivityStatus.class), ActivityStatus.NOT_START));

            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("activityId")));
            return criteriaQuery.getRestriction();
        };
        return activityDao.findAll(specification, pageable).map(this::toActivityWebListVO);
    }

    /**
     * 搜索列表
     *
     * @param areaId
     * @param title
     * @param pageable
     * @return
     */
    public Page<ActivityWebListVO> searchActivityList(Integer areaId, String title, Pageable pageable) {
        Specification<Activity> specification = (Root<Activity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("areaId").as(Integer.class), areaId));
            if (!StringUtils.isEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("activityId")));
            return criteriaQuery.getRestriction();
        };
        return activityDao.findAll(specification, pageable).map(this::toActivityWebListVO);
    }

    /**
     * 转换
     *
     * @param activity
     * @return
     */
    private ActivityWebListVO toActivityWebListVO(Activity activity) {
        ActivityWebListVO vo = new ActivityWebListVO();
        BeanUtils.copyProperties(activity, vo);
        return vo;
    }

    /**
     * 活动详情
     *
     * @param activityId
     * @return
     */
    public ActivityDetailVO activityWebDetail(Integer activityId, Integer userId) {
        Activity activity = this.findOne(activityId);
        ActivityDetailVO vo = new ActivityDetailVO();
        BeanUtils.copyProperties(activity, vo);
        vo.setPrevious(Arrays.asList(activity.getPrevious().split(",")));
        if (!ObjectUtils.isEmpty(userId)) {
            vo.setCollect(collectService.isCollect(userId, activityId, CollectType.ACTIVITY));
        } else {
            vo.setCollect(FlagType.DENY);
        }
        return vo;
    }

    /**
     * @param evaluateList
     * @return
     */
    public List<EvaluateWebListVO> toEvaluateWebListVO(List<Evaluate> evaluateList) {
        return evaluateList.stream().map(e -> {
            EvaluateWebListVO evaluateWebListVo = new EvaluateWebListVO();
            BeanUtils.copyProperties(e, evaluateWebListVo);
            User user = userService.findOne(e.getUserId());
            evaluateWebListVo.setAvatarUrl(user.getAvatarUrl());
            evaluateWebListVo.setNickname(user.getNickname());
            if (!StringUtils.isEmpty(e.getPicture())) {
                evaluateWebListVo.setPictureList(Arrays.asList(e.getPicture().split(",")));
            } else {
                evaluateWebListVo.setPictureList(Lists.newArrayList());
            }
            return evaluateWebListVo;
        }).collect(Collectors.toList());
    }


    /**
     * 系列活动列表
     *
     * @param activityId 活动id
     * @param areaId     地区id
     * @return
     */
    public List<ActivitySeriesListVO> activitySeriesList(Integer activityId, Integer areaId) {
        Activity activity = this.findOne(activityId);
        return activityDao.findActivitiesByActivitySeriesIdAndAreaIdAndStatus(activity.getActivitySeriesId(), areaId, ActivityStatus.NOT_START).stream().map(e -> {
            ActivitySeriesListVO vo = new ActivitySeriesListVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 报名活动
     *
     * @param activityId
     * @return
     */
    public ActivityApplyVO applyActivity(Integer activityId) {
        Activity activity = findOne(activityId);

        if (activity.getStatus().equals(ActivityStatus.OVER)) {
            throw new ValidateException(ExceptionMessage.ACTIVITY_CLOSED);
        } else if (activity.getStatus().equals(ActivityStatus.UNDERWAY)) {
            throw new ValidateException(ExceptionMessage.THE_CAMPAIGN_HAS_ALREADY_STARTED);
        }

        ActivityApplyVO activityApply = new ActivityApplyVO();
        BeanUtils.copyProperties(activity, activityApply);
        return activityApply;
    }

    /**
     * 根据活动获取城市列表
     *
     * @return
     */
    public List<CityListVO> activityCityList() {
        List<Integer> areaIds = activityDao.findAreaIds();
        areaIds.add(-1);
        List<Area> areaList = areaService.findByIds(areaIds);
        return areaList.stream().map(areaService::toCityListVO).collect(Collectors.toList());
    }


    /**
     * 推荐活动
     *
     * @param activityId
     * @return
     */
    public ActivityPageVO recommend(Integer activityId) {
        Activity activity = this.findOne(activityId);
        if (activity.getRecommend().equals(FlagType.RIGHT)) {
            activity.setRecommend(FlagType.DENY);
        } else {
            activity.setRecommend(FlagType.RIGHT);
        }
        Activity entity = activityDao.save(activity);
        return this.toActivityPageVO(entity);
    }


    /**
     * 确认活动
     *
     * @param dto
     * @return
     */
    public ActivityAffirmVO affirmActivity(ActivityApplyDTO dto) {
        Activity activity = findOne(dto.getActivityId());
        User user = userService.findOne(dto.getUserId());
        ActivityAffirmVO vo = new ActivityAffirmVO();
        BeanUtils.copyProperties(activity, vo);
        vo.setUsername(user.getUsername());
        vo.setUserId(user.getUserId());
        vo.setPhone(user.getPhone());
        List<CouponOrderListVO> couponOrderVOList = userCouponService.findUserCanUsable(user.getUserId());
        vo.setCouponList(couponOrderVOList);
        // 保存活动订单
        ActivityOrder activityOrder = activityOrderService.saveActivityOrder(activity, user, dto);
        vo.setActivityOrderId(activityOrder.getActivityOrderId());
        vo.setCount(activityOrder.getCount());
        return vo;
    }


    /**
     * 推荐活动列表
     *
     * @param areaId
     * @return
     */
    public Page<ActivityWebListVO> recommendActivityList(Integer areaId, Pageable pageable) {
        Page<Activity> activityList = activityDao.findActivitiesByAreaIdAndRecommendAndStatus(areaId, FlagType.RIGHT, ActivityStatus.NOT_START, pageable);
        return activityList.map(this::toActivityWebListVO);
    }

    /**
     * 系列列表
     *
     * @return
     */
    public List<SeriesListVO> seriesActivityList() {
        return activitySeriesService.findAll().stream().map(e -> {
            SeriesListVO vo = new SeriesListVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }


    /**
     * 查询所有活动
     *
     * @return
     */
    public List<GoodsActivityVO> findAllSimpleActivity(String name) {
        return activityDao.findAllActivity(name);
    }


    /**
     * 活动分类分页
     *
     * @param areaId
     * @param categoryId
     * @param pageable
     * @return
     */
    public Page<ActivityWebListVO> activityCategoryPage(Integer areaId, Integer categoryId, Pageable pageable) {
        Page<Activity> activityList = activityDao.findActivitiesByAreaIdAndActivityCategoryIdAndStatus(areaId, categoryId, ActivityStatus.NOT_START, pageable);
        return activityList.map(this::toActivityWebListVO);
    }


    /**
     * 猜你喜欢分页
     *
     * @param areaId
     * @param pageable
     * @return
     */
    public Page<ActivityWebListVO> guessLike(Integer areaId, Pageable pageable) {
        return activityDao.findActivitiesByAreaIdAndDeletedIsFalseAndStatusAndActivityType(areaId, ActivityStatus.NOT_START, ActivityType.LIKE, pageable).map(this::toActivityWebListVO);
    }


    /**
     * 统计活动系列数量
     *
     * @param activitySeriesId
     * @return
     */
    public Integer countSeries(Integer activitySeriesId) {
        return activityDao.countByActivitySeriesId(activitySeriesId);
    }


    /**
     * 根据时间修改结束的活动的状态
     */
    public void updateOverActivityStatus() {
        log.info("修改完成的活动状态");
        List<Activity> overActivityList = activityDao.findActivityByStartTimeLessThan(new Date());
        overActivityList.forEach(e -> e.setStatus(ActivityStatus.OVER));
        activityDao.saveAll(overActivityList);
    }

    /**
     * 根据时间修改进心中的活动的状态
     */
    public void updateUnderwayActivityStatus() {
        log.info("修改进行中活动状态");
        Date date = new Date();
        List<Activity> activityList = activityDao.findActivityByStartTimeLessThanAndEndTimeGreaterThan(date, date);
        activityList.forEach(e -> e.setStatus(ActivityStatus.UNDERWAY));
        activityDao.saveAll(activityList);
    }

    /**
     * 根据地区id查找活动
     *
     * @param areaId
     * @return
     */
    public List<Integer> findByAreaId(Integer areaId) {
        return activityDao.findByAreaId(areaId);
    }


}
