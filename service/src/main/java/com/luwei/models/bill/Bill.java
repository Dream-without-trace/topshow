package com.luwei.models.bill;

import com.luwei.common.enums.type.BillType;
import com.luwei.common.enums.type.TransactionType;
import com.luwei.common.utils.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Leone
 * @since 2018-08-02
 **/
@Data
@Entity
public class Bill extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户id'")
    private Integer userId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '外部订单号'")
    private String outTradeNo;

    @Column(columnDefinition = "varchar(64) not null default '' comment '交易号'")
    private String transactionId;

    @Column(columnDefinition = "int(11) not null default 0 comment '账单金额(单位为分)'")
    private Integer amount;

    @Column(columnDefinition = "tinyint(2) not null default 0 comment '账单流水类型'")
    private TransactionType transactionType;

    @Column(columnDefinition = "varchar(64) not null default '' comment '交易备注'")
    private String remark;

    @Column(columnDefinition = "int(11) not null default 0 comment '商家id(平台为0)'")
    private Integer storeId;

}
