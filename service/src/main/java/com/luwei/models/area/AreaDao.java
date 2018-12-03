package com.luwei.models.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface AreaDao extends JpaRepository<Area, Integer>, JpaSpecificationExecutor<Area> {

    List<Area> findAreasByParentId(Integer parentId);

    List<Area> findAreasByAreaIdIn(List<Integer> ids);

    Area findFirstByPinyin(String pinYin);

    Area findFirstByName(String name);
}
