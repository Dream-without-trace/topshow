package com.luwei.controller;

import com.luwei.services.area.AreaService;
import com.luwei.services.area.cms.DistrictVO;
import com.luwei.services.area.cms.ProvinceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-17
 **/
@Slf4j
@RestController
@Api(tags = "地区模块")
@RequestMapping("/api/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    @GetMapping
    @ApiOperation("地区数据")
    public List<ProvinceVO> allArea() {
        return areaService.allArea();
    }

    @GetMapping("/one")
    @ApiOperation("根据id获取地区名称")
    public DistrictVO findOne(@RequestParam Integer areaId) {
        return areaService.one(areaId);
    }


}
