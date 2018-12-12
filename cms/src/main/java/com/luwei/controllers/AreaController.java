package com.luwei.controllers;

import com.luwei.common.Response;
import com.luwei.services.area.AreaService;
import com.luwei.services.area.cms.AreaDTO;
import com.luwei.services.area.cms.AreaPageVo;
import com.luwei.services.area.cms.DistrictVO;
import com.luwei.services.area.cms.ProvinceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<AreaPageVo> page(@RequestParam(required = false) String title,Integer parentId,
                                 @PageableDefault Pageable pageable) {
        return areaService.page(pageable, title,parentId);
    }


    @PostMapping
    @ApiOperation("添加")
    public Response save(@RequestBody @Valid AreaDTO dto) {
        return areaService.save(dto);
    }


    @PutMapping
    @ApiOperation("修改")
    public Response update(@RequestBody AreaDTO dto) {
        return areaService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        areaService.delete(ids);
    }


}
