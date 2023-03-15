package org.example.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author yxl
 * @date 2023/3/15 上午11:29
 */

@Data
@AllArgsConstructor

@NoArgsConstructor
public class Child {
    private String business_num;//子商户账号
    private String business_name;//子商户名
    private String parent_num;//父商户账号
    private String business_password;//子商户密码
    private float amount;//当前子账户余额
    private float all_amount;//当前子账户总营业额
    private Timestamp create_time;//创建时间
    private byte[] pic;
}
