package org.example.pay.common;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author yxl
 * @date 2023/3/15 下午1:40
 */

@Component
public class SignUtil {

    private Object SysConfig;
    private final byte[] password = generateKey("10000-10086");

    /**
     * 注意：即便strKey相同 但是每次生成的byte[]却是不同的
     *
     * @param strKey
     * @return 16Byte的加密password
     */
    private byte[] generateKey(String strKey) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            int length = 128;
            generator.init(length, secureRandom);
            return generator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encrypt(String content) {
        return encrypt(content, password);
    }

    private String encrypt(String content, byte[] keyByte) {
        try {
            //初始化密钥 SecretKeySpec(byte[] key, String algorithm)
            SecretKeySpec key = new SecretKeySpec(keyByte, "AES");
            //创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            //初始化 key要求是16位 16个字节=16*8=128bit 128位
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            //获取加密后字节数组
            byte[] result = cipher.doFinal(byteContent);

            //获取加密后的字符串
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String content) {
        return decrypt(content, password);
    }

    private String decrypt(String content, byte[] keyByte) {
        try {
            SecretKeySpec key = new SecretKeySpec(keyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(parseHexStr2Byte(content));
            return new String(result); // 明文
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 字符串
     * @return 字节数组
     */
    private byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf 字节数组
     * @return 字符串
     */
    private String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

}
