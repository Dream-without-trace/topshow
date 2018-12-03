package com.luwei.models.collect;

import com.luwei.common.enums.type.CollectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface CollectDao extends JpaRepository<Collect, Integer>, JpaSpecificationExecutor<Collect> {


    @Modifying
    @Query("delete from Collect where userId = ?3 and type = ?2 and id in ?1")
    Integer delByIds(List<Integer> ids, CollectType type, Integer userId);

    Page<Collect> findCollectsByUserIdAndType(Integer userId, CollectType collectType, Pageable pageable);

    Collect findFirstByUserIdAndId(Integer userId, Integer id);

    Collect findFirstByUserIdAndIdAndType(Integer userId, Integer id, CollectType type);

    @Query("select id from Collect where userId =?1 and type =?2")
    List<Integer> findCollectsByUserIdAndType(Integer userId, CollectType type);

    List<Collect> findCollectsByIdInAndType(List<Integer> collectId, CollectType collectType);

}
