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
}
