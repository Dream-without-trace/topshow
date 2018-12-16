package com.luwei.services.area;

import com.luwei.common.Response;
import com.luwei.common.enums.type.EvaluateType;
import com.luwei.models.activity.Activity;
import com.luwei.models.activity.category.ActivityCategory;
import com.luwei.models.activity.series.ActivitySeries;
import com.luwei.models.area.Area;
import com.luwei.models.area.AreaDao;
import com.luwei.services.activity.cms.ActivityPageVO;
import com.luwei.services.area.cms.*;
import com.luwei.services.area.web.CityListVO;
import com.luwei.services.evaluate.cms.EvaluateCmsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-17
 **/
@Slf4j
@Service
@Transactional
public class AreaService {

    @Resource
    private AreaDao areaDao;

    /**
     * 获取全国行政区数据
     *
     * @return
     */
    //@Cacheable("area.all")
    public List<ProvinceVO> allArea() {
        List<ProvinceVO> area = new ArrayList<>();
        List<Area> provinceList = areaDao.findAreasByParentIdAndDeletedIsFalse(0);
        return provinceList.stream().map(e -> {
            List<CityVO> cityVOList = areaDao.findAreasByParentIdAndDeletedIsFalse(e.getAreaId()).stream().map(a -> {

                List<Area> districtList = areaDao.findAreasByParentIdAndDeletedIsFalse(a.getAreaId());
                List<DistrictVO> districtVOList = districtList.stream().map(c -> {
                    DistrictVO districtVO = new DistrictVO();
                    BeanUtils.copyProperties(c, districtVO);
                    return districtVO;
                }).collect(Collectors.toList());

                CityVO cityVO = new CityVO();
                BeanUtils.copyProperties(a, cityVO);
                cityVO.setDistrictVOList(districtVOList);
                return cityVO;
            }).collect(Collectors.toList());

            ProvinceVO provinceVO = new ProvinceVO();
            BeanUtils.copyProperties(e, provinceVO);
            provinceVO.setCityList(cityVOList);
            return provinceVO;
        }).collect(Collectors.toList());
    }

    /**
     * 根据地区id获取
     *
     * @param areaId
     * @return
     */
    public DistrictVO one(Integer areaId) {
        Area area = this.findOne(areaId);
        DistrictVO vo = new DistrictVO();
        BeanUtils.copyProperties(area, vo);
        return vo;
    }

    /**
     * 获取单个
     *
     * @param areaId
     * @return
     */
    public Area findOne(Integer areaId) {
        Assert.isTrue(areaId != null && areaId != 0,"地区ID为空！");
        Area area = areaDao.findById(areaId).orElse(null);
        Assert.notNull(area, "地区不存在");
        return area;
    }

    /**
     * 根据地区id列表查询
     *
     * @param ids
     * @return
     */
    public List<Area> findByIds(List<Integer> ids) {
        return areaDao.findAreasByAreaIdInAndDeletedIsFalse(ids);
    }

    /**
     * 转换
     *
     * @param area
     * @return
     */
    public CityListVO toCityListVO(Area area) {
        CityListVO vo = new CityListVO();
        vo.setAreaId(area.getAreaId());
        vo.setName(area.getName());
        vo.setSpell(area.getPinyin());
        vo.setPicture(area.getPicture());
        return vo;
    }

    /**
     * 根据地区拼音查询地区
     *
     * @param pinYin
     * @return
     */
    public Area findByPinYin(String pinYin) {
        if (StringUtils.isEmpty(pinYin)) {
            pinYin = "";
        }
        return areaDao.findFirstByPinyinAndDeletedIsFalse(pinYin.toLowerCase());
    }

    /**
     * 根据地区名称查询
     *
     * @param name
     * @return
     */
    public Area findByCityName(String name) {
        Area area = areaDao.findFirstByNameAndDeletedIsFalse(name);
        Assert.notNull(area, "地区不存在");
        return area;
    }

    public Page<AreaPageVo> page(Pageable pageable, String title,Integer parentId) {
        Specification<Area> specification = (Root<Area> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (!StringUtils.isEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            if (parentId != null && parentId != 0) {
                list.add(criteriaBuilder.equal(root.get("parentId"), parentId));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("areaId")));
            return criteriaQuery.getRestriction();
        };
        return areaDao.findAll(specification, pageable).map(this::toAreaPageVo);
    }


    /**
     * 转换
     *
     * @param area
     * @return
     */
    private AreaPageVo toAreaPageVo(Area area) {
        AreaPageVo vo = new AreaPageVo();
        BeanUtils.copyProperties(area, vo);
        Integer parentId = area.getParentId();
        if (parentId != null && parentId != 0) {
            Optional<Area> parea = areaDao.findById(parentId);
            if (parea.isPresent()) {
                vo.setParentId(parentId);
                vo.setParentName(parea.get().getName());
            }
        }
        return vo;
    }


    public Response save(@Valid AreaDTO dto) {
        Area area = new Area();
        List<Area> areas = areaDao.findAllByNameAndParentIdAndDeletedIsFalse(dto.getName(),dto.getParentId());
        Assert.isTrue((areas == null || areas.size()< 1), dto.getName()+"该城市已存在！");
        BeanUtils.copyProperties(dto, area);
        Integer parentId = dto.getParentId();
        if (parentId == null || parentId == 0) {
            area.setSuffix("省份");
        }else{
            area.setSuffix("市区");
        }
        areaDao.save(area);
        return Response.build(2000,"成功！",area);
    }


    public void delete(Set<Integer> ids) {
        areaDao.delByIds(new ArrayList<>(ids));
    }

    public Response update(AreaDTO dto) {
        Integer areaId = dto.getAreaId();
        Area area = this.findOne(areaId);
        List<Area> areas = areaDao.findAllByNameAndParentIdAndDeletedIsFalse(dto.getName(),dto.getParentId());
        Assert.isTrue((areas == null || areas.size()< 2), dto.getName()+"该城市已存在！");
        BeanUtils.copyProperties(dto, area);
        Integer parentId = dto.getParentId();
        if (parentId == null || parentId == 0) {
            area.setSuffix("省份");
        }else{
            area.setSuffix("市区");
        }
        areaDao.save(area);
        return Response.build(2000,"成功！",dto);
    }
}
