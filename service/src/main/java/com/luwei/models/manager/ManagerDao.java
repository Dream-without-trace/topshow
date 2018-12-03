package com.luwei.models.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-07-30
 **/
public interface ManagerDao extends JpaRepository<Manager, Integer>, JpaSpecificationExecutor<Manager> {

    Manager findByAccountAndPasswordAndDeletedIsFalseAndDisabledIsFalse(String account, String password);

    Manager findFirstByAccountAndDeletedIsFalseAndDisabledIsFalse(String account);

    Integer deleteManagersByManagerIdIn(List<Integer> ids);

    @Modifying
    @Query("update Manager set deleted = :deleted where managerId in :ids and deleted <> :deleted")
    Integer updateDeleted(@Param("ids") Set<Integer> ids, @Param("deleted") Boolean deleted);

    @Modifying
    @Query("update Manager set disabled = ?2 where managerId = ?1 and disabled <> ?2")
    Integer updateDisabled(Integer managerId, Boolean disabled);


    Manager findFirstByAccountAndDisabledIsFalseAndDeletedIsFalse(String account);

}
