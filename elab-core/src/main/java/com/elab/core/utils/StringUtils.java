package com.elab.core.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuhx on 2016/12/11 22:29
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class StringUtils {
    private static String localIp;
    private static DecimalFormat df = new DecimalFormat("##,###.00");
    private static NumberFormat nf = NumberFormat.getInstance();

    public StringUtils() {
    }

    public static boolean isEmpty(String value) {
        int strLen;
        if(value != null && (strLen = value.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(value.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isNumeric(Object obj) {
        if(obj == null) {
            return false;
        } else {
            char[] chars = obj.toString().toCharArray();
            int length = chars.length;
            if(length < 1) {
                return false;
            } else {
                boolean i = false;
                if(length > 1 && chars[0] == 45) {
                    ;
                }

                for(int var4 = 1; var4 < length; ++var4) {
                    if(!Character.isDigit(chars[var4])) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static final int toNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException var2) {
            return 0;
        }
    }

    public static final int toNumber(String str, int defaultValue) {
        if(isEmpty(str)) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }

    public static final long toLongNumber(String str, long defaultValue) {
        if(isEmpty(str)) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException var4) {
                return defaultValue;
            }
        }
    }

    public static final Boolean toBoolean(String str, Boolean defaultValue) {
        return isNotEmpty(str)?Boolean.valueOf(Boolean.parseBoolean(str)):defaultValue;
    }

    public final boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    public final boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public final boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static String formatDouble(double d) {
        return df.format(d);
    }

    public static String formatInt(int d) {
        return nf.format((long)d);
    }

    public static String formatInt(double d) {
        return nf.format(d);
    }

    public static final String[] toLowerCaseWordArray(String s) {
        if(s != null && s.length() != 0) {
            StringTokenizer stringtokenizer = new StringTokenizer(s, " ,\r\n.:/\\+");
            String[] as = new String[stringtokenizer.countTokens()];

            for(int i = 0; i < as.length; ++i) {
                as[i] = stringtokenizer.nextToken().toLowerCase();
            }

            return as;
        } else {
            return new String[0];
        }
    }

    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if(values != null && values.length != 0) {
            String[] var2 = values;
            int var3 = values.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String value = var2[var4];
                result &= !isEmpty(value);
            }
        } else {
            result = false;
        }

        return result;
    }

    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if(!isEmpty(unicode)) {
            for(int i = 0; i < unicode.length(); ++i) {
                out.append(unicode.charAt(i));
            }
        }

        return out.toString();
    }

    public static String stripNonValidXMLCharacters(String input) {
        if(input != null && !"".equals(input)) {
            StringBuilder out = new StringBuilder();

            for(int i = 0; i < input.length(); ++i) {
                char current = input.charAt(i);
                if(current == 9 || current == 10 || current == 13 || current >= 32 && current <= '\ud7ff' || current >= '\ue000' && current <= '�' || current >= 65536 && current <= 1114111) {
                    out.append(current);
                }
            }

            return out.toString();
        } else {
            return "";
        }
    }

    static Pattern MOBILE_PATTERN = Pattern.compile("^((1[3-9][0-9]))[0-9]{8}$");

    public static boolean isMobile(String mobile) {
        if(isEmpty(mobile)) {
            return false;
        } else {
            Matcher match = MOBILE_PATTERN.matcher(mobile);
            return match.matches();
        }
    }

    public static boolean isDate(String str) {
        boolean bValid = true;
        boolean bl = false;
        if(str != null) {
            try {
                if(str.length() != 10) {
                    return false;
                }

                for(int e = 0; e < 10; ++e) {
                    String sTem = Integer.toString(e);
                    if(str.endsWith(sTem)) {
                        bl = true;
                    }
                }

                if(!bl) {
                    return false;
                }

                DateFormat var6 = DateFormat.getDateInstance(3, Locale.getDefault());
                var6.setLenient(false);
                var6.parse(str);
            } catch (ParseException var5) {
                bValid = false;
            }
        } else {
            bValid = false;
        }

        return bValid;
    }

    static Pattern EMAIL_PATTERN = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");

    public static boolean isEmail(String email) {
        if(isEmpty(email)) {
            return false;
        } else {
            Matcher match = EMAIL_PATTERN.matcher(email);
            return match.matches();
        }
    }

    public static String getLocalNetWorkIp() {
        if(localIp != null) {
            return localIp;
        } else {
            try {
                Enumeration e = NetworkInterface.getNetworkInterfaces();
                InetAddress ip = null;

                label47:
                do {
                    NetworkInterface ni;
                    do {
                        do {
                            if(!e.hasMoreElements()) {
                                break label47;
                            }

                            ni = (NetworkInterface)e.nextElement();
                        } while(ni.isLoopback());
                    } while(ni.isVirtual());

                    Enumeration addresss = ni.getInetAddresses();

                    while(addresss.hasMoreElements()) {
                        InetAddress address = (InetAddress)addresss.nextElement();
                        if(address instanceof Inet4Address) {
                            ip = address;
                            break;
                        }
                    }
                } while(ip == null);

                if(ip != null) {
                    localIp = ip.getHostAddress();
                } else {
                    localIp = "127.0.0.1";
                }
            } catch (Exception var5) {
                localIp = "127.0.0.1";
            }

            return localIp;
        }
    }

    public static List<String[]> splitArray(String[] strs, int pageSize) {
        ArrayList list = new ArrayList();

        for(int sList = 0; sList < strs.length; ++sList) {
            list.add(strs[sList]);
        }

        ArrayList var10 = new ArrayList();
        int count = list.size();
        int size;
        if(count % pageSize == 0) {
            size = count / pageSize;
        } else {
            size = count / pageSize + 1;
        }

        for(int i = 0; i < size; ++i) {
            List tempList;
            if(i == size - 1) {
                tempList = list.subList(i * pageSize, list.size());
            } else {
                tempList = list.subList(i * pageSize, (i + 1) * pageSize);
            }

            String[] mobiles = new String[tempList.size()];

            for(int j = 0; j < mobiles.length; ++j) {
                mobiles[j] = (String)tempList.get(j);
            }

            var10.add(mobiles);
        }

        return var10;
    }

    public static final String replace(String s, String s1, String s2) {
        if(s == null) {
            return null;
        } else {
            byte i = 0;
            int i1;
            if((i1 = s.indexOf(s1, i)) < 0) {
                return s;
            } else {
                char[] ac = s.toCharArray();
                char[] ac1 = s2.toCharArray();
                int j = s1.length();
                StringBuffer stringbuffer = new StringBuffer(ac.length);
                stringbuffer.append(ac, 0, i1).append(ac1);
                i1 += j;

                int k;
                for(k = i1; (i1 = s.indexOf(s1, i1)) > 0; k = i1) {
                    stringbuffer.append(ac, k, i1 - k).append(ac1);
                    i1 += j;
                }

                stringbuffer.append(ac, k, ac.length - k);
                return stringbuffer.toString();
            }
        }
    }

    public static final String replaceIgnoreCase(String s, String s1, String s2) {
        if(s == null) {
            return null;
        } else {
            String s3 = s.toLowerCase();
            String s4 = s1.toLowerCase();
            byte i = 0;
            int i1;
            if((i1 = s3.indexOf(s4, i)) < 0) {
                return s;
            } else {
                char[] ac = s.toCharArray();
                char[] ac1 = s2.toCharArray();
                int j = s1.length();
                StringBuffer stringbuffer = new StringBuffer(ac.length);
                stringbuffer.append(ac, 0, i1).append(ac1);
                i1 += j;

                int k;
                for(k = i1; (i1 = s3.indexOf(s4, i1)) > 0; k = i1) {
                    stringbuffer.append(ac, k, i1 - k).append(ac1);
                    i1 += j;
                }

                stringbuffer.append(ac, k, ac.length - k);
                return stringbuffer.toString();
            }
        }
    }

    public static final String escapeHTMLTags(String s) {
        if(s != null && s.length() != 0) {
            StringBuffer stringbuffer = new StringBuffer(s.length());

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if(c == 60) {
                    stringbuffer.append("&lt;");
                } else if(c == 62) {
                    stringbuffer.append("&gt;");
                } else {
                    stringbuffer.append(c);
                }
            }

            return stringbuffer.toString();
        } else {
            return s;
        }
    }

    public static String gbToUtf8(String src) {
        byte[] b = src.getBytes();
        char[] c = new char[b.length];

        for(int i = 0; i < b.length; ++i) {
            c[i] = (char)(b[i] & 255);
        }

        return new String(c);
    }

    public static final String ConvertGBK(String s) {
        String s1 = "";
        if(s != null && s.trim().length() != 0) {
            try {
                s1 = new String(s.getBytes("ISO-8859-1"), "GBK");
            } catch (Exception var4) {
                System.out.println("ConvertGBK():ex=" + var4.toString());
            }

            return s1;
        } else {
            return "";
        }
    }

    public static final String NULLToSpace(String s) {
        return s == null?"":s.trim();
    }

    public static final String toHex(byte[] abyte0) {
        StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);

        for(int i = 0; i < abyte0.length; ++i) {
            if((abyte0[i] & 255) < 16) {
                stringbuffer.append("0");
            }

            stringbuffer.append(Long.toString((long)(abyte0[i] & 255), 16));
        }

        return stringbuffer.toString();
    }

    public static boolean hasCn(String str) {
        return str != null && str.trim().length() != 0?str.getBytes().length > str.length():false;
    }

    public static String numToChinese(int number) {
        String[] chinese = new String[]{"零", "壹", "贰", "弎", "肆", "伍", "陆", "柒", "捌", "玖"};
        return chinese[number];
    }

    public static String getSubStr(String str, int startIndex, int byteNum) throws Exception {
        try {
            if(str == null) {
                return "";
            } else {
                byte[] e = str.getBytes("GBK");
                if(startIndex >= e.length) {
                    return "";
                } else {
                    int index = 0;

                    for(int n = 0; n < byteNum && index < e.length; ++index) {
                        if(e[index] != 0) {
                            ++n;
                        }
                    }

                    return new String(e, startIndex, index, "GBK");
                }
            }
        } catch (Exception var6) {
            throw new Exception(var6);
        }
    }

    public static int getBodySplitLength(String msg, String split, int count) {
        int length = 0;

        for(int i = 0; i < count; ++i) {
            length += msg.indexOf(split) + 1;
            msg = msg.substring(msg.indexOf(split) + 1);
        }

        return length;
    }

    public static List<String> transferStringToArray(String msg, String split) {
        ArrayList result = new ArrayList();
        if(isNotEmpty(msg)) {
            String[] strs = msg.split(split);

            for(int i = 0; i < strs.length; ++i) {
                result.add(strs[i]);
            }
        }

        return result;
    }

    public static String getDateTimeRandomStr() {
        StringBuffer str = new StringBuffer();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        str.append(calendar.get(1));
        str.append(String.valueOf(calendar.get(2)).length() < 2?"0" + String.valueOf(calendar.get(2)):String.valueOf(calendar.get(2)));
        str.append(String.valueOf(calendar.get(5)).length() < 2?"0" + String.valueOf(calendar.get(5)):String.valueOf(calendar.get(5)));
        str.append(String.valueOf(calendar.get(10)).length() < 2?"0" + String.valueOf(calendar.get(10)):String.valueOf(calendar.get(10)));
        str.append(String.valueOf(calendar.get(12)).length() < 2?"0" + String.valueOf(calendar.get(12)):String.valueOf(calendar.get(12)));
        str.append(String.valueOf(calendar.get(13)).length() < 2?"0" + String.valueOf(calendar.get(13)):String.valueOf(calendar.get(13)));
        str.append((new Random()).nextFloat());
        return str.toString();
    }

    public static String trim(String stream, String trimstr) {
        if(stream != null && stream.length() != 0 && trimstr != null && trimstr.length() != 0) {
            boolean epos = false;
            String regpattern = "[" + trimstr + "]*+";
            Pattern pattern = Pattern.compile(regpattern, 2);
            StringBuffer buffer = (new StringBuffer(stream)).reverse();
            Matcher matcher = pattern.matcher(buffer);
            int epos1;
            if(matcher.lookingAt()) {
                epos1 = matcher.end();
                stream = (new StringBuffer(buffer.substring(epos1))).reverse().toString();
            }

            matcher = pattern.matcher(stream);
            if(matcher.lookingAt()) {
                epos1 = matcher.end();
                stream = stream.substring(epos1);
            }

            return stream;
        } else {
            return stream;
        }
    }

    public static String ltrim(String stream, String trimstr) {
        if(stream != null && stream.length() != 0 && trimstr != null && trimstr.length() != 0) {
            boolean epos = false;
            String regpattern = "[" + trimstr + "]*+";
            Pattern pattern = Pattern.compile(regpattern, 2);
            StringBuffer buffer = (new StringBuffer(stream)).reverse();
            Matcher matcher = pattern.matcher(buffer);
            if(matcher.lookingAt()) {
                int epos1 = matcher.end();
                stream = (new StringBuffer(buffer.substring(epos1))).reverse().toString();
            }

            return stream;
        } else {
            return stream;
        }
    }

    public static String rtrim(String stream, String trimstr) {
        if(stream != null && stream.length() != 0 && trimstr != null && trimstr.length() != 0) {
            boolean epos = false;
            String regpattern = "[" + trimstr + "]*+";
            Pattern pattern = Pattern.compile(regpattern, 2);
            StringBuffer buffer = (new StringBuffer(stream)).reverse();
            Matcher matcher = pattern.matcher(buffer);
            if(matcher.lookingAt()) {
                int epos1 = matcher.end();
                stream = (new StringBuffer(buffer.substring(epos1))).reverse().toString();
            }

            return stream;
        } else {
            return stream;
        }
    }

    public static void main(String[] args) {
        boolean ismobile = StringUtils.isMobile("14723232323");
        System.out.println(ismobile);
        String str = getDateTimeRandomStr();
        System.out.println(str);
    }
}
