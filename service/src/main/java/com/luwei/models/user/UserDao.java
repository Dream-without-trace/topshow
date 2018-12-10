package com.luwei.models.user;

import com.luwei.common.enums.type.FlagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-07-30
 **/
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findFirstByOpenid(String openid);

    @Modifying
    @Query("update User set deleted = 1 where userId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);

    List<User> findAllByPhoneAndDisable(String phone, FlagType deny);
}
