package org.example.pay.controller;

import org.example.pay.entity.MyResponse;
import org.example.pay.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yxl
 * @date 2023/3/14 下午2:43
 */

@RestController

@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public MyResponse register(@RequestParam("stu_account") String account,
                               @RequestParam("stu_password") String password,
                               @RequestParam("stu_card_num") String card_num) {
        return studentService.register(account, password, card_num);
    }

    @PostMapping("/login")
    public MyResponse login(@RequestParam("stu_account") String account,
                            @RequestParam("stu_password") String password) {
        return studentService.login(account, password);
    }

    @PostMapping("/againBind")
    public MyResponse againBind(@RequestParam("stu_account") String account,
                                @RequestParam("stu_card_num") String card_num) {
        return studentService.againBind(account, card_num);
    }

    @PostMapping("/searchProduct")
    public MyResponse searchProduct(@RequestParam("Product") String product) {
        return studentService.searchProduct(product);
    }

    @PostMapping("/detectStudentCard")
    public MyResponse detectStudentCard(@RequestParam("stu_card_num") String card_num) {
        return studentService.detectStudentCard(card_num);
    }

    @PostMapping("/pay")
    public MyResponse pay(@RequestParam("businesses_account") String businesses_account,
                          @RequestParam("stu_account") String stu_account,
                          @RequestParam("pay_amount") String pay_amount) {
        return studentService.pay(businesses_account, stu_account, Float.valueOf(pay_amount));
    }

    @PostMapping("/recharge")
    public MyResponse recharge(@RequestParam("stu_account") String account,
                               @RequestParam("recharge_amount") String recharge_amount) {
        return studentService.recharge(account, Float.valueOf(recharge_amount));
    }

    @PostMapping("/checkConsumptionRecords")
    public MyResponse checkConsumptionRecords(@RequestParam("order_number") String order_number) {
        return studentService.checkConsumptionRecords(order_number);
    }

    @PostMapping("/changeStudentPassword")
    public MyResponse changeStudentPassword(@RequestParam("stu_account") String stu_account,
                                            @RequestParam("stu_password") String stu_password,
                                            @RequestParam("stu_new_password") String stu_new_password) {
        return studentService.changeStudentPassword(stu_account, stu_password, stu_new_password);
    }

    @PostMapping("/loginMsg")
    public MyResponse loginMsg(@RequestParam("stu_account") String account) {
        return studentService.loginMsg(account);
    }
}
