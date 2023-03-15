package org.example.pay.common;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author yxl
 * @date 2023/3/15 上午10:58
 */

@Component
public class RandomUtil {

    private final Random random = new Random();

    public String randomFirstChildNum() {
        StringBuilder head = new StringBuilder("10086");
        for (int i = 0; i < 5; i++) {
            head.append(random.nextInt(10));
        }
        return head.toString();
    }

    public String randomFirstParentNum() {
        StringBuilder head = new StringBuilder("10000");
        for (int i = 0; i < 5; i++) {
            head.append(random.nextInt(10));
        }
        return head.toString();
    }

    public String getRandomPassword() {
        int len = 8;
        String result = makeRandomPassword(len);
        if (result.matches(".*[a-z]+.*") && result.matches(".*[A-Z]+.*") &&
                result.matches(".*[0-9]+.*")) {
            return result;
        }
        result = makeRandomPassword(len);
        return result;
    }

    private String makeRandomPassword(int len) {
        char[] cs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < len; ++x) {
            sb.append(cs[random.nextInt(cs.length)]);
        }
        return sb.toString();
    }
}
