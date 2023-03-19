package org.example.pay.controller;

import org.example.pay.entity.MyResponse;
import org.example.pay.service.ManageServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yxl
 * @date 2023/3/16 上午9:59
 */

@RestController
@RequestMapping("/manage")
public class ManageController {
    private final ManageServiceImpl manageService;

    public ManageController(ManageServiceImpl manageService) {
        this.manageService = manageService;
    }

    @PostMapping("/Login")
    public MyResponse login(@RequestParam("manage_account") String account,
                            @RequestParam("manage_password") String password) {
        return manageService.login(account, password);
    }

    @PostMapping("/getParentBusinesses")
    public MyResponse getParentBusinesses(@RequestParam("manage_account") String account) {
        return manageService.getParentBusinesses(account);
    }

    @PostMapping("/getChildBusinesses")
    public MyResponse getChildBusinesses(@RequestParam("businesses_account") String account) {
        return manageService.getChildBusinesses(account);
    }


    @PostMapping("/getChildAuthority")
    public MyResponse getChildAuthority(@RequestParam("businesses_account") String account) {
        return manageService.getChildAuthority(account);
    }

    @PostMapping("/changeChildAuthority")
    public MyResponse changeChildAuthority(@RequestParam("businesses_account") String account,
                                           @RequestParam("authority") String authority,
                                           @RequestParam("way") String way) {
        return manageService.changeChildAuthority(account, Integer.parseInt(authority), Integer.parseInt(way));
    }

    @PostMapping("/childHistoryOrders")
    public MyResponse childHistoryOrders(@RequestParam("businesses_account") String account) {
        return manageService.childHistoryOrders(account);
    }

    @PostMapping("/checkUser")
    public MyResponse checkUser() {
        return manageService.checkUser();
    }

    @PostMapping("/searchUser")
    public MyResponse searchUser(@RequestParam("stu_account") String account) {
        return manageService.searchUser(account);
    }

    @PostMapping("/userMsg")
    public MyResponse userMsg(@RequestParam("stu_account") String account) {
        return manageService.userMsg(account);
    }

    @PostMapping("/changeBusinessesMsg")
    public MyResponse changeBusinessesMsg(@RequestParam("businesses_account") String account, @RequestParam("new_msg") String[] msg) {
        return manageService.changeBusinessesMsg(account, msg);
    }

    @PostMapping("/changeUserMsg")
    public MyResponse changeUserMsg(@RequestParam("stu_account") String account, @RequestParam("new_msg") String[] msg) {
        return manageService.changeUserMsg(account, msg);
    }

    @PostMapping("/deleteBusinesses")
    public MyResponse deleteBusinesses(@RequestParam("businesses_account") String account) {
        return manageService.deleteBusinesses(account);
    }

    @PostMapping("/deleteUser")
    public MyResponse deleteUser(@RequestParam("stu_account") String account) {
        return manageService.deleteUser(account);
    }

    @PostMapping("/takeParent")
    public MyResponse takeParent() {
        return manageService.takeParent();
    }

    @PostMapping("/auditParent")
    public MyResponse auditParent(@RequestParam("id") Integer id, @RequestParam("result") String result) {
        return manageService.auditParent(id, Integer.parseInt(result));
    }

    @PostMapping("/takeAuthority")
    public MyResponse takeAuthority() {
        return manageService.takeAuthority();
    }

    @PostMapping("/auditAuthority")
    public MyResponse auditAuthority(@RequestParam("businesses_account") String businesses_account,
                                     @RequestParam("request_authority") String request_authority,
                                     @RequestParam("result") String result) {
        return manageService.auditAuthority(businesses_account,
                Integer.parseInt(request_authority),
                Integer.parseInt(result));
    }

    @PostMapping("/allHistoryOrders")
    public MyResponse allHistoryOrders() {
        return manageService.allHistoryOrders();
    }

    @PostMapping("/allHistoryOrdersMsg")
    public MyResponse allHistoryOrdersMsg(@RequestParam("order_num") String order_num) {
        return manageService.allHistoryOrdersMsg(Integer.parseInt(order_num));
    }

}
