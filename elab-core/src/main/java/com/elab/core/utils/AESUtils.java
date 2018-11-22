package com.elab.core.utils;

import org.apache.axis.encoding.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author liuhx on 2017/2/7 19:05
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class AESUtils {
    private static int length=128;
    public static final String KEY = "ACE4F440D45CE1CCF7CB702F";
    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] encrypt(String content, String password)
            throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(password.getBytes());
        kgen.init(length, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密

    }

    /**
     * 解密
     *
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    private static byte[] decrypt(byte[] content, String password)
            throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(password.getBytes());
        kgen.init(length, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密



    }

    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    private static byte[] encrypt2(String content, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按照固定KEY加密字符串
     * @param content 字符串
     * @return AES加密后的字符串
     * @throws Exception
     */
    public static String encrypt2Str(String content) throws Exception {
        return encrypt2Str(content, KEY);
    }

    /**
     * 按照指定KEY加密字符串
     * @param content 字符串
     * @param password 指定key
     * @return AES加密后的字符串
     * @throws Exception
     */
    public static String encrypt2Str(String content, String password) throws Exception {
        byte[] encryptResult = encrypt(content, password);
        return Base64.encode(encryptResult);
    }

    /**
     * 按照固定KEY解密加密字符串
     * @param content 加密字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt2Str(String content) throws Exception {
        return decrypt2Str(content, KEY);
    }

    /**
     * 按照指定KEY解密加密字符串
     * @param content 加密字符串
     * @param password 指定KEY
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt2Str(String content, String password) throws Exception {

        byte[] decryptResult = decrypt(Base64.decode(content), password);
        return new String(decryptResult,"UTF-8");
    }

    public static void main(String[] args) throws Exception {
        String content = "t太阳est地";
        String password = "12345678";
        // 加密
        System.out.println("加密前：" + content);

        String tt4 = encrypt2Str(content, password);
        System.out.println(new String(tt4));

        // 解密
        String d = decrypt2Str(tt4, password);
        System.out.println("解密后：" + d);

//		加密前：t太阳est地
//		Bpf0jyJDj/pVHaRf66+OMA==
//		解密后：t太阳est地
    }
}
