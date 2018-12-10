package com.luwei.models.evaluate;

import com.luwei.common.enums.type.EvaluateType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.services.evaluate.web.EvaluateWebVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface EvaluateDao extends JpaRepository<Evaluate, Integer>, JpaSpecificationExecutor<Evaluate> {

    List<Evaluate> findEvaluatesByTypeAndTripartiteIdAndDeletedIsFalse(EvaluateType evaluateType, Integer tripartiteId);


    @Query("select new com.luwei.services.evaluate.web.EvaluateWebVO(e.evaluateId,e.createTime,e.picture,u.avatarUrl,u.nickname,e.content) from Evaluate e, User u where e.userId = u.userId and e.tripartiteId = ?1 and e.type = ?2 and e.flagType = ?3")
    List<EvaluateWebVO> findEvaluateByIdAndType(Integer goodsId, EvaluateType type, FlagType flagType);
}
