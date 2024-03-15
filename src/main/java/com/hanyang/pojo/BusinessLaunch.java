package com.hanyang.pojo;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "business_launch")
public class BusinessLaunch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 业务投放详情
    @Column(nullable = false, name = "business_detail")
    private String businessDetail;

    // 业务投放目的城市
    @Column(nullable = false, name = "target_city")
    private String targetCity;

    // 业务投放性别群体
    @Column(nullable = false, name = "target_sex")
    private String targetSex;

    // 业务投放相关产品
    @Column(nullable = false, name = "target_product")
    private String targetProduct;
}
