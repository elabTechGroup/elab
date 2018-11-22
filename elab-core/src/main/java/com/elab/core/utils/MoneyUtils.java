package com.elab.core.utils;

import com.elab.core.exception.CoreException;

/**
 * @author liuhx on 2017/1/2 14:31
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class MoneyUtils {
    private static final String[] ChineseNum = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

    /**
     * 阿拉伯数字转对应中文金额
     * @param moneyValue 阿拉伯数字金额
     * @return 中文金额
     * @throws CoreException
     */
    public static String getChineseMoney(float moneyValue) throws CoreException {
        String result = "";

        if(Float.compare(moneyValue, new Float(0.0f)) == 0){
            return ChineseNum[0];
        }

        if(moneyValue <0){
            moneyValue *= -1;
            result = "负";
        }

        int intMoney = Integer.parseInt(Float.toString(moneyValue*100));

        String strMoney = Integer.toString(intMoney);

        int moneyLength = strMoney.length();

        StringBuilder strBuf= new StringBuilder(100);

        if (moneyLength > 14) {
            throw new CoreException("Money Value Is Too Large");
        }

        //处理亿部分
        if (moneyLength > 10 && moneyLength <= 14){
            strBuf.append(getSmallMoney(strMoney.substring(0, strMoney.length() - 10)));
            strMoney = strMoney.substring(strMoney.length() - 10, 10);
            strBuf.append("亿");
        }

        //处理万部分
        if (moneyLength > 6)
        {
            strBuf.append(getSmallMoney(strMoney.substring(0, strMoney.length() - 6)));
            strMoney = strMoney.substring(strMoney.length() - 6, 6);
            strBuf.append("万");
        }

        //处理元部分
        if (moneyLength > 2)
        {
            strBuf.append(getSmallMoney(strMoney.substring(0, strMoney.length() - 2)));
            strMoney = strMoney.substring(strMoney.length() - 2, 2);
            strBuf.append("元");
        }

        //处理角、分处理分
        if (Integer.parseInt(strMoney) == 0)
        {
            strBuf.append("整");
        }
        else
        {
            if (moneyLength > 1)
            {
                int intJiao = Integer.parseInt(strMoney.substring(0, 1));
                strBuf.append(ChineseNum[intJiao]);
                if (intJiao != 0)
                {
                    strBuf.append("角");
                }
                strMoney = strMoney.substring(1, 1);
            }

            int intFen = Integer.parseInt(strMoney.substring(0, 1));
            if (intFen != 0)
            {
                strBuf.append(ChineseNum[intFen]);
                strBuf.append("分");
            }
        }
        String temp = strBuf.toString();
        while (temp.indexOf("零零") != -1)
        {
            temp.replaceAll("零零", "零");
        }

        temp.replaceAll("零亿", "亿");
        temp.replaceAll("零万", "万");
        temp.replaceAll("亿万", "亿");

        return result + temp;
    }

    private static String getSmallMoney(String moneyValue)
    {
        int intMoney = Integer.parseInt(moneyValue);
        if (intMoney == 0){
            return "";
        }

        String strMoney = Integer.toString(intMoney);
        int temp;
        StringBuilder strBuf = new StringBuilder(10);
        if (strMoney.length() == 4)
        {
            temp = Integer.parseInt(strMoney.substring(0, 1));
            strMoney = strMoney.substring(1, strMoney.length() - 1);
            strBuf.append(ChineseNum[temp]);
            if (temp != 0)
                strBuf.append("仟");
        }
        if (strMoney.length() == 3)
        {
            temp = Integer.parseInt(strMoney.substring(0, 1));
            strMoney = strMoney.substring(1, strMoney.length() - 1);
            strBuf.append(ChineseNum[temp]);
            if (temp != 0)
                strBuf.append("佰");
        }
        if (strMoney.length() == 2)
        {
            temp = Integer.parseInt(strMoney.substring(0, 1));
            strMoney = strMoney.substring(1, strMoney.length() - 1);
            strBuf.append(ChineseNum[temp]);
            if (temp != 0)
                strBuf.append("拾");
        }
        if (strMoney.length() == 1)
        {
            temp = Integer.parseInt(strMoney);
            strBuf.append(ChineseNum[temp]);
        }
        return strBuf.toString();
    }
}
