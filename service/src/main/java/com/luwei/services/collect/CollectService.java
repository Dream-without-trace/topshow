package com.luwei.services.collect;

import com.luwei.common.enums.status.SalesStatus;
import com.luwei.common.enums.type.CollectType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.activity.Activity;
import com.luwei.models.collect.Collect;
import com.luwei.models.collect.CollectDao;
import com.luwei.models.goods.Goods;
import com.luwei.models.user.User;
import com.luwei.services.activity.ActivityService;
import com.luwei.services.collect.web.CollectWebListVO;
import com.luwei.services.goods.GoodsService;
import com.luwei.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class CollectService {

    @Resource
    private CollectDao collectDao;

    @Resource
    private UserService userService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ActivityService activityService;

    /**
     * 分页
     *
     * @param pageable
     * @param query
     * @return
     */
    public Page<Object> page(Pageable pageable, Object query) {
        return null;
    }

    /**
     * 添加收藏或取消收藏
     *
     * @param id
     * @param collectType
     * @param userId
     */
    public void save(Integer id, CollectType collectType, Integer userId) {
        Collect collect = collectDao.findFirstByUserIdAndIdAndType(userId, id, collectType);
        if (ObjectUtils.isEmpty(collect)) {
            collect = new Collect();
        } else {
            collectDao.delete(collect);
            return;
        }
        User user = userService.findOne(userId);
        if (CollectType.ACTIVITY.equals(collectType)) {
            Activity activity = activityService.findOne(id);
            collect.setId(activity.getActivityId());
            collect.setType(CollectType.ACTIVITY);
        } else if (CollectType.GOODS.equals(collectType)) {
            Goods goods = goodsService.findOne(id);
            collect.setId(goods.getGoodsId());
            collect.setType(CollectType.GOODS);
        } else {
            throw new ValidateException(ExceptionMessage.COLLECT_TYPE_FAIL);
        }
        collect.setUserId(user.getUserId());
        collectDao.save(collect);
    }

    /**
     * 批量删除收藏
     *
     * @param ids
     * @param collectType
     * @param userId
     */
    public void delete(Set<Integer> ids, CollectType collectType, Integer userId) {
        Integer result = collectDao.delByIds(new ArrayList<>(ids), collectType, userId);
    }

    /**
     * 查找单个收藏
     *
     * @param collectId
     * @return
     */
    public Collect findOne(Integer collectId) {
        Collect collect = collectDao.findById(collectId).orElse(null);
        Assert.notNull(collect, "收藏不存在");
        return collect;
    }

    /**
     * 获取用户收藏的商品或活动列表
     *
     * @param collectType
     * @param userId
     * @return
     */
    public Page<CollectWebListVO> collectList(CollectType collectType, Integer userId, Pageable pageable) {
        Page<Collect> collectList = collectDao.findCollectsByUserIdAndType(userId, collectType, pageable);
        return collectList.map(this::toCollectWebListVO);
    }

    /**
     * 转换成小程序端vo
     *
     * @param collect
     * @return
     */
    private CollectWebListVO toCollectWebListVO(Collect collect) {
        CollectWebListVO vo = new CollectWebListVO();
        BeanUtils.copyProperties(collect, vo);
        if (CollectType.ACTIVITY.equals(collect.getType())) {
            Activity activity = activityService.findOne(collect.getId());
            vo.setTitle(activity.getTitle());
            vo.setPrice(activity.getPrice());
            vo.setPicture(activity.getPicture());
            vo.setAddress(activity.getProvince() + " " + activity.getCity());
            vo.setStartTime(activity.getStartTime());
        } else if (CollectType.GOODS.equals(collect.getType())) {
            Goods goods = goodsService.findOne(collect.getId());
            vo.setTitle(goods.getName());
            vo.setPrice(goods.getPrice());
            vo.setPicture(goods.getPicture());
            vo.setAddress("");
            vo.setStartTime(goods.getCreateTime());
        }
        return vo;
    }

    /**
     * 根据用户id和 商品或活动id判断是否收藏
     *
     * @param userId
     * @param id
     * @return
     */
    public FlagType isCollect(Integer userId, Integer id, CollectType type) {
        Collect collect = collectDao.findFirstByUserIdAndIdAndType(userId, id, type);
        if (!ObjectUtils.isEmpty(collect)) {
            return FlagType.RIGHT;
        }
        return FlagType.DENY;
    }

    /**
     * 获取某个用户的收藏商品或活动的id和类别列表
     *
     * @param type
     * @param userId
     * @return
     */
    public List<Integer> findCollectIds(CollectType type, Integer userId) {
        return collectDao.findCollectsByUserIdAndType(userId, type);
    }


    /**
     * 删除下架商品收藏
     */
    public void deleteSoldOut() {
        List<Integer> goodsIds = goodsService.findSoldOutGoods(SalesStatus.OFF);
        List<Collect> collectList = collectDao.findCollectsByIdInAndType(goodsIds, CollectType.GOODS);
        collectDao.deleteAll(collectList);
    }


}