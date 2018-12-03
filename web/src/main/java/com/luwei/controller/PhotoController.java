package com.luwei.controller;

import com.luwei.services.photo.PhotoService;
import com.luwei.services.photo.web.PhotoUploadDTO;
import com.luwei.services.photo.web.PhotoWebPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@RestController
@Api(tags = "图片模块")
@RequestMapping("/api/photo")
public class PhotoController {

    @Resource
    private PhotoService photoService;

    @PostMapping
    @ApiOperation("上传")
    public void save(@RequestBody @Valid PhotoUploadDTO dto) {
        photoService.upload(dto);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Page<PhotoWebPageVO> page(@PageableDefault Pageable pageable, @RequestParam Integer userId) {
        return photoService.page(pageable, userId);
    }


}
