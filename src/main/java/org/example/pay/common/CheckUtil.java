package org.example.pay.common;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author yxl
 * @date 2023/3/14 下午2:51
 */

@Component
public class CheckUtil {
    public boolean checkStuCardIsRight(String card_num) {
        String pattern = "0?(01|02|03|04|05|06|07|08)[0-9]{8}";
        return Pattern.matches(pattern, card_num);
    }

    public boolean checkIdCardIsRight(String id_num){
        String pattern = "[1-9]\\d{5}(18|19|20|(3\\d))\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]";
        return Pattern.matches(pattern, id_num);
    }
}
