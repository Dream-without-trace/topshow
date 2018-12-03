package com.luwei.controller;

import com.luwei.common.enums.type.SortType;
import com.luwei.services.store.StoreService;
import com.luwei.services.store.web.StoreWebDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Leone
 * @since 2018-08-28
 **/
@Slf4j
@RestController
@Api(tags = "商家模块")
@RequestMapping("/api/store")
public class StoreController {

    @Resource
    private StoreService storeService;


    @GetMapping
    @ApiOperation("/detail")
    public StoreWebDetailVO storeDetail(@RequestParam @ApiParam("商家id") Integer storeId,
                                        @RequestParam(required = false) @ApiParam("价格 升序|降序") SortType price,
                                        @RequestParam(required = false) @ApiParam("时间 升序|降序") SortType time,
                                        @PageableDefault Pageable pageable) {
        return storeService.storeDetail(storeId, price, time, pageable);
    }


}
