package com.luwei.services.collect.web;

import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-01
 **/
@Data
public class CollectSimpleBO {

    private Integer goodsId;

    private Integer categoryId;

    public CollectSimpleBO() {
    }

    public CollectSimpleBO(Integer goodsId, Integer categoryId) {
        this.goodsId = goodsId;
        this.categoryId = categoryId;
    }
}
