package com.elab.core.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * @author liuhx on 2017/1/2 14:38
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class AESForNodejs {
    public static final String DEFAULT_CODING = "utf-8";
    public static final String KEY = "ACE4F440D45CE1CCF7CB702F";

    /**
     * 解密
     * @param encrypted
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String encrypted, String key) throws Exception {
        byte[] keyb = key.getBytes(DEFAULT_CODING);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(keyb);
        SecretKeySpec skey = new SecretKeySpec(thedigest, "AES");
        Cipher dcipher = Cipher.getInstance("AES");
        dcipher.init(Cipher.DECRYPT_MODE, skey);

        byte[] clearbyte = dcipher.doFinal(toByte(encrypted));
        return new String(clearbyte);
    }

    /**
     * 解密
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static String decrypt(String encrypted) throws Exception {
        return decrypt(encrypted, KEY);
    }

    /**
     * 加密
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }

    /**
     * 加密
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key) throws Exception {
        byte[] input = content.getBytes(DEFAULT_CODING);

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(key.getBytes(DEFAULT_CODING));
        SecretKeySpec skc = new SecretKeySpec(thedigest, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skc);

        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
        ctLength += cipher.doFinal(cipherText, ctLength);

        return parseByte2HexStr(cipherText);
    }

    /**
     * 字符串转字节数组
     * @param hexString
     * @return
     */
    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    /**
     * 字节转16进制数组
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String key = "ACE4F440D45CE1CCF7CB702F";
        String content = "YSD-YX-001";
        String jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-001";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-002";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-003";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-004";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-005";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-006";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-007";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-008";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-009";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-010";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-011";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-012";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-013";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-014";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        content = "YSD-YX-015";
        jiami = AESForNodejs.encrypt(content, key);
        System.out.println(jiami);
        String jiemi = AESForNodejs.decrypt(jiami, key);
        System.out.println(jiemi);
    }
}
