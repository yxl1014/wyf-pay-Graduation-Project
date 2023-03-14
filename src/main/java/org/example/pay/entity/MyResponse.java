package org.example.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yxl
 * @date 2023/3/14 下午2:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyResponse {
    private int status;
    private String balance;
    private String[] order_number;
    private String[] pay_amount;

    private String stu_card_num;

    public MyResponse(int status) {
        this.status = status;
    }

    public MyResponse(String balance, String[] order_number, String[] pay_amount) {
        this.balance = balance;
        this.order_number = order_number;
        this.pay_amount = pay_amount;
    }

    public MyResponse(int status, String balance, String stu_card_num) {
        this.status = status;
        this.balance = balance;
        this.stu_card_num = stu_card_num;
    }

    public MyResponse(int status, String balance) {
        this.status = status;
        this.balance = balance;
    }
}
