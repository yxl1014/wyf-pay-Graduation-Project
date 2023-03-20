package org.example.pay.service;

import org.example.pay.common.CheckUtil;
import org.example.pay.common.RandomUtil;
import org.example.pay.common.SignUtil;
import org.example.pay.common.TwoHouseUtil;
import org.example.pay.entity.*;
import org.example.pay.mapper.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author yxl
 * @date 2023/3/15 上午10:50
 */

@Service
public class BusinessServiceImpl {

    private final BusinessMapper businessMapper;

    private final ChildMapper childMapper;

    private final JurisdictionMapper jurisdictionMapper;

    private final CheckUtil checkUtil;

    private final RandomUtil randomUtil;

    private final SignUtil signUtil;

    private final TwoHouseUtil twoHouseUtil;

    private final ApplyMapper applyMapper;

    private final OrderMapper orderMapper;

    public BusinessServiceImpl(BusinessMapper businessMapper, ChildMapper childMapper, CheckUtil checkUtil, RandomUtil randomUtil, SignUtil signUtil, TwoHouseUtil twoHouseUtil, JurisdictionMapper jurisdictionMapper, ApplyMapper applyMapper, OrderMapper orderMapper) {
        this.businessMapper = businessMapper;
        this.childMapper = childMapper;
        this.checkUtil = checkUtil;
        this.randomUtil = randomUtil;
        this.signUtil = signUtil;
        this.twoHouseUtil = twoHouseUtil;
        this.jurisdictionMapper = jurisdictionMapper;
        this.applyMapper = applyMapper;
        this.orderMapper = orderMapper;
    }

    public MyResponse businessesRegister(String people, String id_num, String phone_num, String shop_name, Float amount) throws IOException {
        if (!checkUtil.checkIdCardIsRight(id_num)) {
            //身份证格式不正确
            return new MyResponse(2);
        }
        //获取当前时间
        long now = System.currentTimeMillis();
        Business business = businessMapper.findBusinessByIdAndPeople(id_num, people);
        //判断是不是第一次注册
        if (business == null) {
            //如果是第一次注册则加两个表
            String pn = randomUtil.randomFirstParentNum();
            String pp = randomUtil.getRandomPassword();
            int ok1 = businessMapper.insertBusiness(new Business(id_num, people, phone_num, shop_name, amount, pn, pp,
                    new Timestamp(now)));

            String cn = randomUtil.randomFirstChildNum();
            String cp = randomUtil.getRandomPassword();

            int ok2 = childMapper.insertChild(new Child(cn, shop_name, id_num, cp, 0, 0,
                    new Timestamp(now), twoHouseUtil.get2WHouse(signUtil.encrypt(cn, "pay-10000-10086-"))));

            int ok3 = jurisdictionMapper.insertJurisdiction(cn);

            int ok4 = applyMapper.insertApply(cn, 3, new Timestamp(now), 2, null);
            if (ok1 + ok2 + ok3 + ok4 == 4) {
                return new MyResponse(1, new String[]{pn, pp, cn, cp});
            } else {
                return new MyResponse(0);
            }
        } else {
            //如果不是第一次注册则加一个表
            String cn = randomUtil.randomFirstChildNum();
            String cp = randomUtil.getRandomPassword();

            int ok1 = childMapper.insertChild(new Child(cn, shop_name, id_num, cp, 0, 0,
                    new Timestamp(now), twoHouseUtil.get2WHouse(signUtil.encrypt(cn, "pay-10000-10086-"))));
            int ok2 = jurisdictionMapper.insertJurisdiction(cn);
            int ok3 = applyMapper.insertApply(cn, 3, new Timestamp(now), 2, null);
            if (ok1 + ok2 + ok3 == 3) {
                return new MyResponse(1, new String[]{cn, cp});
            } else {
                return new MyResponse(0);
            }
        }
    }

    public MyResponse businessesLogin(String businesses_account, String businesses_password) {
        if (businesses_account.startsWith("10000")) {
            int ok = businessMapper.findAllBusinessByNP(businesses_account, businesses_password);
            return new MyResponse(ok == 1 ? 1 : 0);
        } else {
            int ok = childMapper.findAllChildByNP(businesses_account, businesses_password);
            return new MyResponse(ok == 1 ? 2 : 0);
        }
    }

    public MyResponse businessesLoginMsg(String businesses_account) {
        if (businesses_account.startsWith("10000")) {
            Business business = businessMapper.findBusinessByPn(businesses_account);
            List<String> login_cId = new ArrayList<>();
            List<String> login_cName = new ArrayList<>();
            List<String> login_cTime = new ArrayList<>();
            List<Float> login_allAmount = new ArrayList<>();
            List<String> login_nok_name = new ArrayList<>();
            List<Float> login_nok_amount = new ArrayList<>();
            List<Child> childList = childMapper.findAllChildByPid(business.getId_num());
            for (Child c : childList) {
                Jurisdiction jurisdiction = jurisdictionMapper.findJurisdictionByChildNum(c.getBusiness_num());
                if (jurisdiction.isUsing_j()) {
                    login_cId.add(c.getBusiness_num());
                    login_cName.add(c.getBusiness_name());
                    login_cTime.add(c.getCreate_time().toString());
                    login_allAmount.add(c.getAll_amount());
                } else {
                    login_nok_name.add(c.getBusiness_name());
                    login_nok_amount.add(c.getAmount());
                }
            }
            return new MyResponse(1, login_cId.toArray(new String[0]), login_cName.toArray(new String[0]),
                    login_cTime.toArray(new String[0]), login_allAmount.toArray(new Float[0]),
                    login_nok_name.toArray(new String[0]), login_nok_amount.toArray(new Float[0]),
                    business.getCreate_time().toString());
        } else {
            Child child = childMapper.findChildByBid(businesses_account);
            List<String> login_child = new ArrayList<>();
            List<Integer> having_authority = new ArrayList<>();
            Business business = businessMapper.findBusinessById(child.getParent_num());
            login_child.add(business.getParent_num());
            login_child.add(child.getBusiness_name());
            login_child.add(child.getCreate_time().toString());
            Jurisdiction jurisdiction = jurisdictionMapper.findJurisdictionByChildNum(businesses_account);
            if (jurisdiction.isChangeName_j()) {
                having_authority.add(1);
            } else {
                Apply apply = applyMapper.findApplyByAccountAndType(businesses_account, 1);
                if (apply == null) {
                    having_authority.add(0);
                } else if (apply.getStatus() == 2) {
                    having_authority.add(2);
                }
            }
            if (jurisdiction.isChangePassword_j()) {
                having_authority.add(1);
            } else {
                Apply apply = applyMapper.findApplyByAccountAndType(businesses_account, 2);
                if (apply == null) {
                    having_authority.add(0);
                } else if (apply.getStatus() == 2) {
                    having_authority.add(2);
                }
            }

            return new MyResponse(1, login_child.toArray(new String[0]), having_authority.toArray(new Integer[0]));
        }
    }

    public MyResponse deleteBusinesses(String businesses_account, String delete_reason) {
        Child c = childMapper.findChildByBid(businesses_account);
        if (c == null) {
            return new MyResponse(0);
        }
        int ok = applyMapper.insertApply(businesses_account, 4,
                new Timestamp(System.currentTimeMillis()), 2, delete_reason);
        return new MyResponse(ok);
    }

    public MyResponse historyOrders(String businesses_account) {
        List<Integer> num = new ArrayList<>();
        List<Float> amount = new ArrayList<>();
        if (businesses_account.startsWith("10000")) {
            List<Order> allOrderByParentNum = orderMapper.findAllOrderByParentNum(businesses_account);
            allOrderByParentNum.sort((o1, o2) -> Long.compare(o2.getCreate_time().getTime(), o1.getCreate_time().getTime()));
            for (Order o : allOrderByParentNum) {
                num.add(o.getOrder_num());
                amount.add(o.getAmount());
            }
        } else {
            List<Order> allOrderByParentNum = orderMapper.findAllOrderByBusinessNum(businesses_account);
            allOrderByParentNum.sort((o1, o2) -> Long.compare(o2.getCreate_time().getTime(), o1.getCreate_time().getTime()));
            for (Order o : allOrderByParentNum) {
                num.add(o.getOrder_num());
                amount.add(o.getAmount());
            }
        }
        return new MyResponse(1, num.toArray(new Integer[0]), amount.toArray(new Float[0]));
    }

    public MyResponse historyOrdersMsg(int order_num) {
        Order order = orderMapper.findOrderByOrderNum(order_num);
        return new MyResponse(1, new String[]{String.valueOf(order.getOrder_num()), order.getBusiness_num(),
                String.valueOf(order.getAmount()), order.getCreate_time().toString()});
    }

    public MyResponse changeBusinessesPassword(String businesses_account, String businesses_password, String businesses_new_password) {
        if (businesses_account.startsWith("10000")) {
            int exist = businessMapper.findAllBusinessByNP(businesses_account, businesses_password);
            if (exist == 0) {
                return new MyResponse(0);
            }
            int ok = businessMapper.updatePwdByNum(businesses_new_password, businesses_account);
            return new MyResponse(ok == 1 ? 1 : 2);
        } else {
            int exist = childMapper.findAllChildByNP(businesses_account, businesses_password);
            if (exist == 0) {
                return new MyResponse(0);
            }
            int ok = childMapper.updatePwdByNum(businesses_new_password, businesses_account);
            return new MyResponse(ok == 1 ? 1 : 2);
        }
    }

    public byte[] getQrcode(String businesses_account) {
        Child child = childMapper.findChildByBid(businesses_account);
        return child.getPic();
    }

    public MyResponse todayOrdersMsg(String businesses_account) {
        List<Integer> num = new ArrayList<>();
        List<Float> amount = new ArrayList<>();
        List<Order> allOrderByParentNum = orderMapper.findAllOrderByBusinessNum(businesses_account);
        allOrderByParentNum.sort((o1, o2) -> Long.compare(o2.getCreate_time().getTime(), o1.getCreate_time().getTime()));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        for (Order o : allOrderByParentNum) {
            if (o.getCreate_time().getTime() < c.getTimeInMillis()) {
                break;
            }
            num.add(o.getOrder_num());
            amount.add(o.getAmount());
        }
        return new MyResponse(1, num.toArray(new Integer[0]), amount.toArray(new Float[0]));
    }

    public MyResponse requestAuthority(String businesses_account, String request_authority, String request_reason) {
        int type = Integer.parseInt(request_authority);
        Jurisdiction jurisdiction = jurisdictionMapper.findJurisdictionByChildNum(businesses_account);
        if (type == 1 && jurisdiction.isChangeName_j()) {
            return new MyResponse(0);
        }
        if (type == 2 && jurisdiction.isChangePassword_j()) {
            return new MyResponse(0);
        }
        Apply apply = applyMapper.findApplyByAccountAndType(businesses_account, type);
        if (apply != null) {
            return new MyResponse(0);
        }
        return new MyResponse(applyMapper.insertApply(businesses_account, type,
                new Timestamp(System.currentTimeMillis()), 2, request_reason));
    }


}
