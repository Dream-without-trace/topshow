package com.luwei.models.integralbill;

import com.luwei.common.enums.type.BillType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-07-30
 **/
public interface IntegralBillDao extends JpaRepository<IntegralBill, Integer>, JpaSpecificationExecutor<IntegralBill> {

    @Query("update IntegralBill set deleted = 1 where integralBillId in ?1 and deleted <> :deleted")
    Integer delByIds(Set<Integer> ids);

    Page<IntegralBill> findIntegralBillsByBillTypeAndUserId(BillType billType, Integer userId, Pageable pageable);

    Page<IntegralBill> findIntegralBillsByBillTypeAndUserIdOrderByIntegralBillIdDesc(BillType billType, Integer userId, Pageable pageable);

}
