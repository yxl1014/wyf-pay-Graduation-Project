package org.example.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yxl
 * @date 2023/3/14 下午2:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String stu_account;
    private String stu_password;
    private String stu_card_num;
    private float stu_amount;
}
