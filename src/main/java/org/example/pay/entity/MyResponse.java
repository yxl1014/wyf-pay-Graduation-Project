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

    private String[] businessesRegister;


    private String[] login_cId;
    private String[] login_cName;
    private String[] login_cTime;
    private Float[] login_allAmount;
    private String[] login_nok_name;
    private Float[] login_nok_amount;
    private String login_createTime;

    public MyResponse(int status, String[] login_cId, String[] login_cName, String[] login_cTime,
                      Float[] login_allAmount, String[] login_nok_name, Float[] login_nok_amount, String login_createTime) {
        this.status = status;
        this.login_cId = login_cId;
        this.login_cName = login_cName;
        this.login_cTime = login_cTime;
        this.login_allAmount = login_allAmount;
        this.login_nok_name = login_nok_name;
        this.login_nok_amount = login_nok_amount;
        this.login_createTime = login_createTime;
    }


    public MyResponse(int status) {
        this.status = status;
    }


    private String[] login_child;
    private Integer[] having_authority;

    public MyResponse(int status, String[] login_child, Integer[] having_authority) {
        this.status = status;
        this.login_child = login_child;
        this.having_authority = having_authority;
    }

    private Integer[] order_id;
    private Float[] order_amount;

    public MyResponse(int status, Integer[] order_id, Float[] order_amount) {
        this.status = status;
        this.order_id = order_id;
        this.order_amount = order_amount;
    }

    private String[] orderMsg;


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

    public MyResponse(int status, String[] businessesRegister) {
        this.status = status;
        this.businessesRegister = businessesRegister;
    }
}
