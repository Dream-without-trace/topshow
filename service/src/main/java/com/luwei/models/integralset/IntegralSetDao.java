package com.luwei.models.integralset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program: topshow
 * @description: 签到积分设置dao
 * @author: ZhangHongJie
 * @create: 2018-12-08 00:13
 **/
public interface IntegralSetDao extends JpaRepository<IntegralSet, Integer>, JpaSpecificationExecutor<IntegralSet> {
}
