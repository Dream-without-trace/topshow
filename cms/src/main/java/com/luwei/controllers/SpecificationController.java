package com.luwei.controllers;

import com.luwei.services.specification.SpecificationService;
import com.luwei.services.specification.cms.SpecificationAddDTO;
import com.luwei.services.specification.cms.SpecificationEditDTO;
import com.luwei.services.specification.cms.SpecificationPageVO;
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
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Slf4j
@RestController
@Api(tags = "商品规格模块")
@RequestMapping("api/specification")
public class SpecificationController {

    @Resource
    private SpecificationService specificationService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid SpecificationAddDTO dto) {
        specificationService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        specificationService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public SpecificationPageVO update(@RequestBody SpecificationEditDTO dto) {
        return specificationService.update(dto);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<SpecificationPageVO> page(@RequestParam(required = false) Integer goodsId, @PageableDefault Pageable pageable) {
        return specificationService.page(pageable, goodsId);
    }

}
