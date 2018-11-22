package com.elab.core.utils;

import java.io.*;

/**
 * 文件操作工具
 * @author liuhx on 2016/12/30 23:07
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class FileUtils {
    /**
     *
     * 读取源文件内容
     *
     * @param filename
     *            String 文件路径
     * @throws IOException
     * @return byte[] 文件内容
     */
    public static byte[] readFile(String filename) throws IOException {
        File file = new File(filename);
        if (filename == null || filename.equals("")) {
            throw new NullPointerException("无效的文件路径");
        }
        long len = file.length();
        byte[] bytes = new byte[(int) len];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(file));
        int r = bufferedInputStream.read(bytes);
        if (r != len)
            throw new IOException("读取文件不正确");
        bufferedInputStream.close();
        return bytes;
    }

    /**
     * 将数据写入文件
     *
     * @param data
     *            byte[]
     * @throws IOException
     */

    public static void writeFile(byte[] data, String filename)
            throws IOException {
        File file = new File(filename);
        file.getParentFile().mkdirs();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(file));
        bufferedOutputStream.write(data);
        bufferedOutputStream.close();
    }

    /**
     * 从jar文件里读取class
     *
     * @param filename
     *            String
     * @throws IOException
     * @return byte[]
     */

    public byte[] readFileJar(String filename) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                getClass().getResource(filename).openStream());
        int len = bufferedInputStream.available();
        byte[] bytes = new byte[len];
        int r = bufferedInputStream.read(bytes);
        if (len != r) {
            bytes = null;
            throw new IOException("读取文件不正确");
        }
        bufferedInputStream.close();
        return bytes;
    }

    /** */
    /**
     *
     * 读取网络流，为了防止中文的问题，在读取过程中没有进行编码转换，而且采取了动态的byte[]的方式获得所有的byte返回
     *
     * @param bufferedInputStream
     *            BufferedInputStream
     * @throws IOException
     * @return byte[]
     */

    public byte[] readUrlStream(BufferedInputStream bufferedInputStream)
            throws IOException {
        byte[] bytes = new byte[100];
        byte[] bytecount = null;
        int n = 0;
        int ilength = 0;
        while ((n = bufferedInputStream.read(bytes)) >= 0) {
            if (bytecount != null)
                ilength = bytecount.length;
            byte[] tempbyte = new byte[ilength + n];
            if (bytecount != null) {
                System.arraycopy(bytecount, 0, tempbyte, 0, ilength);
            }
            System.arraycopy(bytes, 0, tempbyte, ilength, n);
            bytecount = tempbyte;
            if (n < bytes.length)
                break;
        }

        return bytecount;
    }

    /**
     * 获取文件的真实后缀名。目前只支持JPG, GIF, PNG, BMP四种图片文件。
     *
     * @param bytes 文件字节流
     * @return JPG, GIF, PNG or null
     */
    public static String getFileSuffix(byte[] bytes)
    {
        if ((bytes == null) || (bytes.length < 10)) {
            return null;
        }
        if ((bytes[0] == 71) && (bytes[1] == 73) && (bytes[2] == 70)) {
            return "GIF";
        }
        if ((bytes[1] == 80) && (bytes[2] == 78) && (bytes[3] == 71)) {
            return "PNG";
        }
        if ((bytes[6] == 74) && (bytes[7] == 70) && (bytes[8] == 73) && (bytes[9] == 70)) {
            return "JPG";
        }
        if ((bytes[0] == 66) && (bytes[1] == 77)) {
            return "BMP";
        }
        return null;
    }

    /**
     * 获取文件的真实媒体类型。目前只支持JPG, GIF, PNG, BMP四种图片文件。
     *
     * @param bytes 文件字节流
     * @return 媒体类型(MEME-TYPE)
     */
    public static String getMimeType(byte[] bytes)
    {
        String suffix = getFileSuffix(bytes);
        String mimeType;
        if ("JPG".equals(suffix))
        {
            mimeType = "image/jpeg";
        }
        else
        {
            if ("GIF".equals(suffix))
            {
                mimeType = "image/gif";
            }
            else
            {
                if ("PNG".equals(suffix))
                {
                    mimeType = "image/png";
                }
                else
                {
                    if ("BMP".equals(suffix))
                    {
                        mimeType = "image/bmp";
                    }
                    else
                    {
                        mimeType = "application/octet-stream";
                    }
                }
            }
        }
        return mimeType;
    }

}
