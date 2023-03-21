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
    private Float[] pay_amount;
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


    private String[] parent_num;
    private String[] create_time;

    public MyResponse(int status, String[] parent_num, String[] create_time) {
        this.status = status;
        this.parent_num = parent_num;
        this.create_time = create_time;
    }

    private String[] child_num;
    private String[] child_name;

    public MyResponse(int status, String[] child_num, String[] child_name, String[] create_time) {
        this.status = status;
        this.create_time = create_time;
        this.child_num = child_num;
        this.child_name = child_name;
    }

    private String[] stu_num;

    public MyResponse(int status, Integer[] order_id, String[] stu_num, String[] create_time, Float[] order_amount) {
        this.status = status;
        this.order_id = order_id;
        this.order_amount = order_amount;
        this.create_time = create_time;
        this.stu_num = stu_num;
    }

    private Student student;

    public MyResponse(int status, Student student) {
        this.status = status;
        this.student = student;
    }

    private Integer[] var7;

    public MyResponse(int status, String[] var1, String[] var2, String[] var3, String[] var4, Float[] var5, Integer[] var6, Integer[] var7) {
        this.status = status;
        this.businessesRegister = var1;
        this.login_cId = var2;
        this.login_cName = var3;
        this.login_cTime = var4;
        this.login_allAmount = var5;
        this.order_id = var6;
        this.var7 = var7;
    }

    public MyResponse(int status, String[] var1, String[] var2, String[] var3, Integer[] var4, String[] var5) {
        this.status = status;
        this.businessesRegister = var1;
        this.login_cId = var2;
        this.login_cName = var3;
        this.order_id = var4;
        this.orderMsg = var5;
    }

    public MyResponse(String[] order_number, Float[] pay_amount) {
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

    public MyResponse(int status, Integer[] having_authority) {
        this.status = status;
        this.having_authority = having_authority;
    }

    private Order order;

    public MyResponse(int status, Order order) {
        this.status = status;
        this.order = order;
    }
}
