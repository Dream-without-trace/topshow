package com.luwei.models.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface AreaDao extends JpaRepository<Area, Integer>, JpaSpecificationExecutor<Area> {

    List<Area> findAreasByParentIdAndDeletedIsFalse(Integer parentId);

    List<Area> findAreasByAreaIdInAndDeletedIsFalse(List<Integer> ids);

    Area findFirstByPinyinAndDeletedIsFalse(String pinYin);

    Area findFirstByNameAndDeletedIsFalse(String name);

    List<Area> findAllByNameAndParentIdAndDeletedIsFalse(String name,Integer parentId);

    @Modifying
    @Query("update Area set deleted = 1 where areaId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);
}
