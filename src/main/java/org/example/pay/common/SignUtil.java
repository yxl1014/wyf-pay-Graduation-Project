package org.example.pay.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.spec.IvParameterSpec;

/**
 * @author yxl
 * @date 2023/3/15 下午1:40
 */

@Component
public class SignUtil {

    /**
     * AES加密工具类
     *
     * @author ACGkaka
     * @since 2021-06-18 19:11:03
     */
    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 算法定义
     */
    private static final String AES_ALGORITHM = "AES";
    /**
     * 指定填充方式
     */
    private static final String CIPHER_PADDING = "AES/ECB/PKCS5Padding";
    private static final String CIPHER_CBC_PADDING = "AES/CBC/PKCS5Padding";
    /**
     * 偏移量(CBC中使用，增强加密算法强度)
     */
    private static final String IV_SEED = "1234567812345678";


    private static final String ascKey = "pay-10000-10086-";


    public String encrypt(String context) {
        return this.encrypt(context, ascKey);
    }

    /**
     * AES加密
     *
     * @param content 待加密内容
     * @param aesKey  密码
     * @return
     */
    private String encrypt(String content, String aesKey) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        //判断秘钥是否为16位
        if (StringUtils.isNotBlank(aesKey) && aesKey.length() == 16) {
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置加密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
                //选择加密
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
                //根据待加密内容生成字节数组
                byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
                //返回base64字符串
                return Base64Utils.encodeToString(encrypted);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            return null;
        }
    }

    public String decrypt(String context) {
        return this.decrypt(context, ascKey);
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param aesKey  密码
     * @return
     */
    private String decrypt(String content, String aesKey) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        //判断秘钥是否为16位
        if (StringUtils.isNotBlank(aesKey) && aesKey.length() == 16) {
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置解密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
                //选择解密
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);

                //先进行Base64解码
                byte[] decodeBase64 = Base64Utils.decodeFromString(content);

                //根据待解密内容进行解密
                byte[] decrypted = cipher.doFinal(decodeBase64);
                //将字节数组转成字符串
                return new String(decrypted, ENCODING);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            return null;
        }
    }

    /**
     * AES_CBC加密
     *
     * @param content 待加密内容
     * @param aesKey  密码
     * @return
     */
    public String encryptCBC(String content, String aesKey) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        //判断秘钥是否为16位
        if (StringUtils.isNotBlank(aesKey) && aesKey.length() == 16) {
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置加密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);
                //偏移
                IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
                //选择加密
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                //根据待加密内容生成字节数组
                byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
                //返回base64字符串
                return Base64Utils.encodeToString(encrypted);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            return null;
        }
    }

    /**
     * AES_CBC解密
     *
     * @param content 待解密内容
     * @param aesKey  密码
     * @return
     */
    public String decryptCBC(String content, String aesKey) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        //判断秘钥是否为16位
        if (StringUtils.isNotBlank(aesKey) && aesKey.length() == 16) {
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置解密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                //偏移
                IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);
                //选择解密
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

                //先进行Base64解码
                byte[] decodeBase64 = Base64Utils.decodeFromString(content);

                //根据待解密内容进行解密
                byte[] decrypted = cipher.doFinal(decodeBase64);
                //将字节数组转成字符串
                return new String(decrypted, ENCODING);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        // AES支持三种长度的密钥：128位、192位、256位。
        // 代码中这种就是128位的加密密钥，16字节 * 8位/字节 = 128位。
        SignUtil signUtil = new SignUtil();

        System.out.println("---------加密---------");
        String aesResult = signUtil.encrypt("1008652700");
        System.out.println("aes加密结果:" + aesResult);
        System.out.println();
        aesResult = signUtil.decrypt(aesResult);
        System.out.println("aes解密结果:" + aesResult);
        System.out.println();
    }
}

