package org.example.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author yxl
 * @date 2023/3/15 上午11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    private String id_num;//注册人身份证
    private String people;//注册人姓名
    private String phone_num;//注册人电话
    private String shop_name;//父商户名称
    private float amount;//注册资金
    private String parent_num;//父商户账号
    private String parent_password;//父商户密码
    private Timestamp create_time;//创建时间
}
