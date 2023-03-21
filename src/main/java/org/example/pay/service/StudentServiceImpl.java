package org.example.pay.service;

import org.example.pay.common.CheckUtil;
import org.example.pay.common.SignUtil;
import org.example.pay.entity.*;
import org.example.pay.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yxl
 * @date 2023/3/14 下午2:44
 */

@Service
public class StudentServiceImpl {

    private final StudentMapper studentMapper;

    private final CheckUtil checkUtil;

    private final CardMapper cardMapper;

    private final ChildMapper childMapper;

    private final OrderMapper orderMapper;

    private final BusinessMapper businessMapper;

    private final SignUtil signUtil;

    public StudentServiceImpl(StudentMapper studentMapper, CheckUtil checkUtil, CardMapper cardMapper, ChildMapper childMapper, OrderMapper orderMapper, BusinessMapper businessMapper, SignUtil signUtil) {
        this.studentMapper = studentMapper;
        this.checkUtil = checkUtil;
        this.cardMapper = cardMapper;
        this.childMapper = childMapper;
        this.orderMapper = orderMapper;
        this.businessMapper = businessMapper;
        this.signUtil = signUtil;
    }

    public MyResponse register(String account, String password, String card_num) {
        //正则表达式判断用户输入的学号是否正确
        if (!checkUtil.checkStuCardIsRight(card_num)) {
            //不正确则返回2
            return new MyResponse(2);
        }

        //判断账号是否存在
        int accountIsExist = studentMapper.findAllUserByAccount(account);
        if (accountIsExist > 0) {
            //如果账号已存在则返回5
            return new MyResponse(5);
        }

        //判断卡状态
        Boolean cardStatus = cardMapper.findCardStatusByCardNum(card_num);
        if (cardStatus != null && !cardStatus) {
            return new MyResponse(0);
        }

        //判断校园卡是否已经被绑定
        int cardNumIsExist = studentMapper.findAllUserByCardNum(card_num);
        if (cardNumIsExist > 0) {
            //如果账号已存在则返回5
            return new MyResponse(3);
        }

        int ok1 = studentMapper.insertStu(new Student(account, password, card_num, 0));
        int ok2 = cardMapper.insertCard(new Card(card_num, true));
        if (ok1 + ok2 == 2) {
            return new MyResponse(1, String.valueOf(0.0));
        } else {
            return new MyResponse(4);
        }
    }

    public MyResponse login(String account, String password) {
        Student stu = studentMapper.findUserByAccountAndPassword(account, password);
        if (stu == null) {
            return new MyResponse(0);
        }
        Boolean status = cardMapper.findCardStatusByCardNum(stu.getStu_card_num());
        if (!status) {
            return new MyResponse(3, String.valueOf(stu.getStu_amount()));
        }
        //TODO:这边如果登录成功 需要返回订单详情，需要查询订单表
        return new MyResponse(1, String.valueOf(stu.getStu_amount()), stu.getStu_card_num());
    }

    public MyResponse againBind(String account, String card_num) {
        //正则表达式判断用户输入的学号是否正确
        if (!checkUtil.checkStuCardIsRight(card_num)) {
            //不正确则返回2
            return new MyResponse(3);
        }

        //判断校园卡是否已经被绑定
        int cardNumIsExist = studentMapper.findAllUserByCardNum(card_num);
        if (cardNumIsExist > 0) {
            //如果账号已存在则返回5
            return new MyResponse(0);
        }

        //判断卡状态
        Boolean cardStatus = cardMapper.findCardStatusByCardNum(card_num);
        if (cardStatus != null && !cardStatus) {
            return new MyResponse(2);
        }

        Student user = studentMapper.findUserByAccount(account);
        String oldCardNum = user.getStu_card_num();

        //重绑定
        int ok1 = studentMapper.updateCardNumByAccount(card_num, account);
        //删除旧的
        int ok2 = cardMapper.deleteCardByCardNum(oldCardNum);
        //插入新的
        if (cardStatus == null) {
            cardMapper.insertCard(new Card(card_num, true));
        }

        return new MyResponse(ok1 + ok2 == 2 ? 1 : 4, String.valueOf(user.getStu_amount()));
    }

    public MyResponse searchProduct(String product) {
        //TODO 得有表 不然现在写不了
        return null;
    }


    public MyResponse detectStudentCard(String card_num) {
        Boolean cardStatus = cardMapper.findCardStatusByCardNum(card_num);
        if (cardStatus != null && !cardStatus) {
            return new MyResponse(0);
        }
        return new MyResponse(1);
    }

    public MyResponse pay(String businesses_account, String stu_account, Float pay_amount) {
        Child child = childMapper.findChildByBid(businesses_account);
        if (child == null) {
            return new MyResponse(3);
        }
        Student user = studentMapper.findUserByAccount(stu_account);
        if (user == null) {
            //用户不存在
            return new MyResponse(3);
        }
        if (user.getStu_amount() < pay_amount) {
            return new MyResponse(0);
        }

        int ok1 = studentMapper.updateAmountByAccount(user.getStu_amount() - pay_amount, stu_account);
        int ok2 = childMapper.updateAmountByAccount(child.getAmount() + pay_amount, businesses_account);
        int ok3 = childMapper.updateAllAmountByAccount(child.getAll_amount() + pay_amount, businesses_account);

        Business business = businessMapper.findBusinessById(child.getParent_num());
        int i = orderMapper.insertOrder(new Order(child.getBusiness_num(), business.getParent_num(),
                user.getStu_account(), pay_amount, new Timestamp(System.currentTimeMillis())));
        return new MyResponse(ok1 + ok2 + ok3 == 3 ? 1 : 2);
    }

    public MyResponse recharge(String account, Float recharge_amount) {
        Student user = studentMapper.findUserByAccount(account);
        if (user == null) {
            //用户不存在
            return new MyResponse(2);
        }
        int ok1 = studentMapper.updateAmountByAccount(user.getStu_amount() + recharge_amount, account);
        return new MyResponse(ok1 == 1 ? 1 : 0);
    }

    public MyResponse checkConsumptionRecords(String order_number) {
        Order order = orderMapper.findOrderByOrderNum(Integer.parseInt(order_number));
        if (order == null) {
            return new MyResponse(0);
        }
        Child child = childMapper.findChildByBid(order.getBusiness_num());
        List<String> res = new ArrayList<>();
        res.add(order.getBusiness_num());
        if (child == null) {
            res.add(null);
            res.add(order.getCreate_time().toString());
            res.add(String.valueOf(0));
        } else {
            res.add(child.getBusiness_name());
            res.add(order.getCreate_time().toString());
            res.add(String.valueOf(1));
        }
        return new MyResponse(1, res.toArray(new String[0]));
    }

    public MyResponse changeStudentPassword(String stu_account, String stu_password, String stu_new_password) {
        Student user = studentMapper.findUserByAccountAndPassword(stu_account, stu_password);
        if (user == null) {
            return new MyResponse(0);
        }
        int ok = studentMapper.updatePasswordByAccount(stu_new_password, stu_account);
        return new MyResponse(ok == 1 ? 1 : 2);
    }

    public MyResponse loginMsg(String account) {
        List<Order> orders = orderMapper.findAllOrderByStuAccount(account);
        List<String> oid = new ArrayList<>();
        List<Float> amount = new ArrayList<>();
        for (Order o : orders) {
            oid.add(String.valueOf(o.getOrder_num()));
            amount.add(o.getAmount());
        }
        return new MyResponse(oid.toArray(new String[0]), amount.toArray(new Float[0]));
    }

    public String takeMsg(String data) {
        return signUtil.decrypt(data);
    }
}
