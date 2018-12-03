package com.luwei.models.integralbill;

import com.luwei.common.enums.type.BillType;
import com.luwei.common.utils.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@Entity
public class IntegralBill extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer integralBillId;

    @Column(columnDefinition = "int(11) not null default 0 comment '用户ID'")
    private Integer userId;

    @Column(columnDefinition = "varchar(64) not null default '' comment '用户昵称'")
    private String nickname;

    @Column(columnDefinition = "varchar(64) not null default '' comment '用户手机'")
    private String phone;

    @Column(columnDefinition = "int(11) not null default 0 comment '积分'")
    private Integer integral;

    @Column(columnDefinition = "int(11) not null default 0 comment '当前积分'")
    private Integer nowIntegral;

    @Column(columnDefinition = "int(11) not null default 0 comment '收支类型'")
    private BillType billType;

    @Column(columnDefinition = "varchar(64) not null default '' comment '备注'")
    private String remark;

}
