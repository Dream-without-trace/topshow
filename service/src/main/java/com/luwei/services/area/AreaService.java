package com.luwei.services.area;

import com.luwei.models.area.Area;
import com.luwei.models.area.AreaDao;
import com.luwei.services.area.cms.CityVO;
import com.luwei.services.area.cms.DistrictVO;
import com.luwei.services.area.cms.ProvinceVO;
import com.luwei.services.area.web.CityListVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
    @Cacheable("area.all")
    public List<ProvinceVO> allArea() {
        List<ProvinceVO> area = new ArrayList<>();
        List<Area> provinceList = areaDao.findAreasByParentId(0);
        return provinceList.stream().map(e -> {
            List<CityVO> cityVOList = areaDao.findAreasByParentId(e.getAreaId()).stream().map(a -> {

                List<Area> districtList = areaDao.findAreasByParentId(a.getAreaId());
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
        return areaDao.findAreasByAreaIdIn(ids);
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
        return areaDao.findFirstByPinyin(pinYin.toLowerCase());
    }

    /**
     * 根据地区名称查询
     *
     * @param name
     * @return
     */
    public Area findByCityName(String name) {
        Area area = areaDao.findFirstByName(name);
        Assert.notNull(area, "地区不存在");
        return area;
    }

}
