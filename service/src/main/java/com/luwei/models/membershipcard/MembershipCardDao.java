package com.luwei.models.membershipcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: topshow
 * @description: 会员卡dao接口
 * @author: ZhangHongJie
 * @create: 2018-12-03 16:44
 **/
public interface MembershipCardDao extends JpaRepository<MembershipCard, Integer>, JpaSpecificationExecutor<MembershipCard> {

}