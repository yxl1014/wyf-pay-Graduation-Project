package org.example.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author yxl
 * @date 2023/3/15 下午4:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int order_num;
    private String business_num;
    private String parent_num;
    private String student_num;
    private float amount;
    private Timestamp create_time;

    public Order(String business_num, String parent_num, String student_num, float amount, Timestamp create_time) {
        this.business_num = business_num;
        this.parent_num = parent_num;
        this.student_num = student_num;
        this.amount = amount;
        this.create_time = create_time;
    }
}
