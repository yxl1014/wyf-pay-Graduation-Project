package org.example.pay.service;

import org.example.pay.entity.*;
import org.example.pay.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yxl
 * @date 2023/3/16 上午9:59
 */

@Service
public class ManageServiceImpl {

    @Autowired
    private ManageMapper manageMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private ChildMapper childMapper;

    @Autowired
    private JurisdictionMapper jurisdictionMapper;

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CardMapper cardMapper;


    public MyResponse login(String account, String password) {
        int ok = manageMapper.findManageByAccountAndPassword(account, password);
        return new MyResponse(ok);
    }

    public MyResponse getParentBusinesses(String account) {
        int ok = manageMapper.findManageByAccount(account);
        if (ok != 1) {
            return new MyResponse(0);
        }
        List<Business> businesses = businessMapper.findAllBusiness();
        List<String> pid = new ArrayList<>();
        List<String> ctime = new ArrayList<>();
        for (Business b : businesses) {
            pid.add(b.getParent_num());
            ctime.add(b.getCreate_time().toString());
        }
        return new MyResponse(1, pid.toArray(new String[0]), ctime.toArray(new String[0]));
    }

    public MyResponse getChildBusinesses(String account) {
        Business business = businessMapper.findBusinessByPn(account);
        if (business == null) {
            return new MyResponse(0);
        }
        List<Child> childList = childMapper.findAllChildByPid(business.getId_num());
        List<String> cid = new ArrayList<>();
        List<String> cname = new ArrayList<>();
        List<String> ctime = new ArrayList<>();
        for (Child c : childList) {
            cid.add(c.getBusiness_num());
            cname.add(c.getBusiness_name());
            ctime.add(c.getCreate_time().toString());
        }
        return new MyResponse(1, cid.toArray(new String[0]),
                cname.toArray(new String[0]), ctime.toArray(new String[0]));
    }

    public MyResponse getChildAuthority(String account) {
        List<Integer> having_authority = new ArrayList<>();
        Jurisdiction jurisdiction = jurisdictionMapper.findJurisdictionByChildNum(account);
        if (jurisdiction.isChangeName_j()) {
            having_authority.add(1);
        } else {
            Apply apply = applyMapper.findApplyByAccountAndType(account, 1);
            if (apply.getStatus() == 2) {
                having_authority.add(2);
            }
        }
        if (jurisdiction.isChangePassword_j()) {
            having_authority.add(1);
        } else {
            Apply apply = applyMapper.findApplyByAccountAndType(account, 2);
            if (apply.getStatus() == 2) {
                having_authority.add(2);
            }
        }

        return new MyResponse(1, having_authority.toArray(new Integer[0]));
    }

    public MyResponse changeChildAuthority(String account, int authority, int way) {
        Jurisdiction jurisdiction = jurisdictionMapper.findJurisdictionByChildNum(account);
        int ok = 0;
        Apply apply = applyMapper.findApplyByAccountAndType(account, authority);
        switch (way) {
            case 1: {
                if (authority == 1 && jurisdiction.isChangeName_j()) {
                    ok += jurisdictionMapper.updateChangeNameByChildNum(0, account);
                }
                if (authority == 2 && jurisdiction.isChangePassword_j()) {
                    ok += jurisdictionMapper.updateChangePasswordByChildNum(0, account);
                }
                break;
            }
            case 2: {
                if (authority == 1 && !jurisdiction.isChangeName_j()) {
                    ok += jurisdictionMapper.updateChangeNameByChildNum(1, account);
                }
                if (authority == 2 && !jurisdiction.isChangePassword_j()) {
                    ok += jurisdictionMapper.updateChangePasswordByChildNum(1, account);
                }
                break;
            }
            case 3: {
                if (apply == null) {
                    return new MyResponse(0);
                }
                ok += applyMapper.removeApplyByAid(apply.getAid());
                return new MyResponse(ok);
            }
            default:
                return new MyResponse(0);
        }
        if (apply != null) {
            ok += applyMapper.removeApplyByAid(apply.getAid());
        }
        return new MyResponse(ok >= 1 ? 1 : 0);
    }

    public MyResponse childHistoryOrders(String account) {
        List<Order> allOrderByParentNum = orderMapper.findAllOrderByBusinessNum(account);
        allOrderByParentNum.sort((o1, o2) -> Long.compare(o2.getCreate_time().getTime(), o1.getCreate_time().getTime()));
        List<Integer> num = new ArrayList<>();
        List<String> stu_num = new ArrayList<>();
        List<String> ctime = new ArrayList<>();
        List<Float> amount = new ArrayList<>();
        for (Order o : allOrderByParentNum) {
            num.add(o.getOrder_num());
            stu_num.add(o.getStudent_num());
            ctime.add(o.getCreate_time().toString());
            amount.add(o.getAmount());
        }
        return new MyResponse(1, num.toArray(new Integer[0]), stu_num.toArray(new String[0]),
                ctime.toArray(new String[0]), amount.toArray(new Float[0]));
    }

    public MyResponse checkUser() {
        List<Student> allUser = studentMapper.findAllUser();
        List<String> stu_account = new ArrayList<>();
        for (Student s : allUser) {
            stu_account.add(s.getStu_account());
        }
        return new MyResponse(1, stu_account.toArray(new String[0]));
    }

    public MyResponse searchUser(String account) {
        Student student = studentMapper.findUserByAccount(account);
        if (student == null) {
            return new MyResponse(0);
        }
        return new MyResponse(1, student.getStu_account());
    }

    public MyResponse userMsg(String account) {
        Student student = studentMapper.findUserByAccount(account);
        if (student == null) {
            return new MyResponse(0);
        }
        Boolean cardStatus = cardMapper.findCardStatusByCardNum(student.getStu_card_num());
        List<String> msg = new ArrayList<>();
        msg.add(student.getStu_password());
        if (cardStatus) {
            msg.add(student.getStu_card_num());
            msg.add(String.valueOf(student.getStu_amount()));
        } else {
            msg.add("0");
            msg.add("0");
        }
        return new MyResponse(1, msg.toArray(new String[0]));
    }

    public MyResponse changeBusinessesMsg(String account, String[] msg) {
        Child child = childMapper.findChildByBid(account);
        if (child == null) {
            return new MyResponse(0);
        }
        if (msg[0] != null) {
            Business bus = businessMapper.findBusinessByPn(msg[0]);
            if (bus == null) {
                return new MyResponse(0);
            }
            childMapper.updateParentByAccount(bus.getId_num(), account);
        }
        if (msg[1] != null) {
            childMapper.updateBidByAccount(msg[1], account);
        }
        if (msg[2] != null) {
            childMapper.updateNameByAccount(msg[2], account);
        }
        return new MyResponse(1);
    }

    public MyResponse changeUserMsg(String account, String[] msg) {
        int ok = studentMapper.findAllUserByAccount(account);
        if (ok == 0) {
            return new MyResponse(0);
        }
        if (msg[0] != null) {
            studentMapper.updatePasswordByAccount(msg[0], account);
        }
        if (msg[1] != null) {
            studentMapper.updateCardNumByAccount(msg[1], account);
        }
        return new MyResponse(1);
    }

    public MyResponse deleteBusinesses(String account) {
        if (account.startsWith("10000")) {
            Business business = businessMapper.findBusinessByPn(account);
            if (business == null) {
                return new MyResponse(0);
            }
            int i = businessMapper.deleteBusinessByPn(account);
            List<Child> allChildByPid = childMapper.findAllChildByPid(business.getId_num());
            int i1 = childMapper.deleteChildByPn(business.getId_num());
            for (Child c : allChildByPid) {
                jurisdictionMapper.deleteJByAccount(c.getBusiness_num());
            }
            return new MyResponse(i + i1 >= 2 ? 1 : 0);
        }
        int i = childMapper.deleteChildByBid(account);
        return new MyResponse(i == 1 ? 1 : 0);
    }

    public MyResponse deleteUser(String account) {
        int i = studentMapper.deleteStuByStu_account(account);
        return new MyResponse(i == 1 ? 1 : 0);
    }

    public MyResponse takeParent() {
        List<Apply> apply1 = applyMapper.findAllApplyByType(3);
        List<Apply> apply2 = applyMapper.findAllApplyByType(4);
        List<String> name = new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<String> tel = new ArrayList<>();
        List<String> cname = new ArrayList<>();
        List<Float> amount = new ArrayList<>();
        List<Integer> idd = new ArrayList<>();
        List<Integer> type = new ArrayList<>();
        for (Apply apply : apply1) {
            Child child = childMapper.findChildByBid(apply.getAccount());
            Business business = businessMapper.findBusinessById(child.getParent_num());
            name.add(business.getPeople());
            id.add(business.getId_num());
            tel.add(business.getPhone_num());
            cname.add(child.getBusiness_name());
            amount.add(child.getAmount());
            idd.add(apply.getAid());
            type.add(apply.getType());
        }
        for (Apply apply : apply2) {
            Child child = childMapper.findChildByBid(apply.getAccount());
            Business business = businessMapper.findBusinessById(child.getParent_num());
            name.add(business.getPeople());
            id.add(business.getId_num());
            tel.add(business.getPhone_num());
            cname.add(child.getBusiness_name());
            amount.add(child.getAmount());
            idd.add(apply.getAid());
            type.add(apply.getType());
        }
        return new MyResponse(1, name.toArray(new String[0]), id.toArray(new String[0]), tel.toArray(new String[0]),
                cname.toArray(new String[0]), amount.toArray(new Float[0]), idd.toArray(new Integer[0]),type.toArray(new Integer[0]));
    }


    public MyResponse auditParent(Integer id, int result) {
        Apply apply = applyMapper.findApplyById(id);
        if (apply == null) {
            return new MyResponse(0);
        }
        int ok1;
        if (result == 1) {
            ok1 = jurisdictionMapper.updateUsingByChildNum(1, apply.getAccount());
        } else {
            ok1 = childMapper.deleteChildByBid(apply.getAccount());
        }

        int ok2 = applyMapper.removeApplyByAid(id);
        return new MyResponse(ok1 + ok2 == 2 ? 1 : 0);
    }

    public MyResponse takeAuthority() {
        List<Apply> apply1 = applyMapper.findAllApplyByType(1);
        List<Apply> apply2 = applyMapper.findAllApplyByType(2);
        List<String> var1 = new ArrayList<>();
        List<String> var2 = new ArrayList<>();
        List<String> var3 = new ArrayList<>();
        List<Integer> var4 = new ArrayList<>();
        List<String> var5 = new ArrayList<>();
        for (Apply apply : apply1) {
            Child child = childMapper.findChildByBid(apply.getAccount());
            Business business = businessMapper.findBusinessById(child.getParent_num());
            var1.add(business.getParent_num());
            var2.add(child.getBusiness_num());
            var3.add(child.getBusiness_name());
            var4.add(apply.getType());
            var5.add(apply.getMsg());
        }
        for (Apply apply : apply2) {
            Child child = childMapper.findChildByBid(apply.getAccount());
            Business business = businessMapper.findBusinessById(child.getParent_num());
            var1.add(business.getParent_num());
            var2.add(child.getBusiness_num());
            var3.add(child.getBusiness_name());
            var4.add(apply.getType());
            var5.add(apply.getMsg());
        }
        return new MyResponse(1, var1.toArray(new String[0]), var2.toArray(new String[0]), var3.toArray(new String[0]),
                var4.toArray(new Integer[0]), var5.toArray(new String[0]));
    }

    public MyResponse auditAuthority(String businesses_account, int request, int result) {
        Apply apply = applyMapper.findApplyByAccountAndType(businesses_account, request);
        if (apply == null) {
            return new MyResponse(0);
        }
        if (result == 1) {
            if (request == 1) {
                jurisdictionMapper.updateChangeNameByChildNum(1, businesses_account);
            } else {
                jurisdictionMapper.updateChangePasswordByChildNum(1, businesses_account);
            }
        }
        int ok = applyMapper.removeApplyByAid(apply.getAid());
        return new MyResponse(ok);
    }

    public MyResponse allHistoryOrders() {
        List<Order> allOrder = orderMapper.findAllOrder();
        List<Integer> num = new ArrayList<>();
        List<Float> amount = new ArrayList<>();
        for (Order o : allOrder) {
            num.add(o.getOrder_num());
            amount.add(o.getAmount());
        }
        return new MyResponse(1, num.toArray(new Integer[0]), amount.toArray(new Float[0]));
    }

    public MyResponse allHistoryOrdersMsg(int order_num) {
        Order order = orderMapper.findOrderByOrderNum(order_num);
        return new MyResponse(order == null ? 1 : 0, order);
    }
}
