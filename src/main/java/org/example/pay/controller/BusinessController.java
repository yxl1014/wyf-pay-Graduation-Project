package org.example.pay.controller;

import org.example.pay.common.TwoHouseUtil;
import org.example.pay.entity.MyResponse;
import org.example.pay.service.BusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author yxl
 * @date 2023/3/15 上午10:45
 */

@RestController
@RequestMapping("/businesses")
public class BusinessController {

    private final BusinessServiceImpl businessService;

    private final TwoHouseUtil twoHouseUtil;

    public BusinessController(BusinessServiceImpl businessService, TwoHouseUtil twoHouseUtil) {
        this.businessService = businessService;
        this.twoHouseUtil = twoHouseUtil;
    }


    @GetMapping("test2WH")
    public byte[] test2WH(@RequestParam("data") String d) throws IOException {
        return twoHouseUtil.get2WHouse(d);
    }


    @PostMapping("/businessesRegister")
    public MyResponse businessesRegister(@RequestParam("people") String people,
                                         @RequestParam("id_num") String id_num,
                                         @RequestParam("phone_num") String phone_num,
                                         @RequestParam("shop_name") String shop_name,
                                         @RequestParam("registration_amount") String registration_amount) throws IOException {
        return businessService.businessesRegister(people, id_num, phone_num, shop_name, Float.valueOf(registration_amount));
    }

    @PostMapping("/businessesLogin")
    public MyResponse businessesLogin(@RequestParam("businesses_account") String businesses_account,
                                      @RequestParam("businesses_password") String businesses_password) {
        return businessService.businessesLogin(businesses_account, businesses_password);
    }

    @PostMapping("/businessesLoginMsg")
    public MyResponse businessesLoginMsg(@RequestParam("businesses_account") String businesses_account) {
        return businessService.businessesLoginMsg(businesses_account);
    }

    @PostMapping("/deleteBusinesses")
    public MyResponse deleteBusinesses(@RequestParam("businesses_account") String businesses_account,
                                       @RequestParam("delete_reason") String delete_reason) {
        return businessService.deleteBusinesses(businesses_account, delete_reason);
    }

    @PostMapping("/historyOrders")
    public MyResponse historyOrders(@RequestParam("businesses_account") String businesses_account) {
        return businessService.historyOrders(businesses_account);
    }

    @PostMapping("/historyOrdersMsg")
    public MyResponse historyOrdersMsg(@RequestParam("order_num") String order_num) {
        return businessService.historyOrdersMsg(Integer.parseInt(order_num));
    }

    @PostMapping("/changeBusinessesPassword")
    public MyResponse changeBusinessesPassword(@RequestParam("businesses_account") String businesses_account,
                                               @RequestParam("businesses_password") String businesses_password,
                                               @RequestParam("businesses_new_password") String businesses_new_password) {
        return businessService.changeBusinessesPassword(businesses_account, businesses_password, businesses_new_password);
    }

    @PostMapping(value = "getQrcode", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getQrcode(@RequestParam("businesses_account") String businesses_account) {
        return businessService.getQrcode(businesses_account);
    }

    @PostMapping("/todayOrdersMsg")
    public MyResponse todayOrdersMsg(@RequestParam("businesses_account") String businesses_account) {
        return businessService.todayOrdersMsg(businesses_account);
    }

    @PostMapping("/requestAuthority")
    public MyResponse requestAuthority(@RequestParam("businesses_account") String businesses_account,
                                       @RequestParam("request_authority") String request_authority,
                                       @RequestParam("request_reason") String request_reason) {
        return businessService.requestAuthority(businesses_account, request_authority, request_reason);
    }
}
