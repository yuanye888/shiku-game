package com.shiku.robot.shikugame.untils;

import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author lijun
 * @Description 字符串工具类
 * @Date 2019-12-22 4:48 下午
 **/
public final class StringUtil {
    public static final char  BLANK = ' ';

    public static final char  ZERO  = '0';

    private static StringUtil stringUtil;

    private StringUtil() {

    }

    public static synchronized StringUtil getInstance() {
        if (stringUtil == null) {
            stringUtil = new StringUtil();
        }
        return stringUtil;
    }

    /**
     * 右补位，左对齐
     * @param oriStr
     *            原字符串
     * @param len
     *            目标字符串长度
     * @param alexin
     *            补位字符
     * @return 目标字符串
     */
    public String padRight(String oriStr, int len, char alexin) {
        oriStr = StringUtils.isEmpty(oriStr) == true ? "" : oriStr;
        StringBuilder str = new StringBuilder();
        int strlen = oriStr.length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str.append(alexin);
            }
        }
        str.insert(0, oriStr);
        return str.toString();
    }

    /**
     * 左补位，右对齐
     * @param oriStr
     *            原字符串
     * @param len
     *            目标字符串长度
     * @param alexin
     *            补位字符
     * @return 目标字符串
     */
    public String padLeft(String oriStr, int len, char alexin) {
        oriStr = StringUtils.isEmpty(oriStr) == true ? "" : oriStr;
        StringBuilder str = new StringBuilder();
        int strlen = oriStr.length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str.append(alexin);
            }
        }
        str.append(oriStr);
        return str.toString();
    }

    /**
     * 金额转为12位字符串，单位分
     * @param value
     * @return
     */
    public String amount2String(BigDecimal value) {
        String v = new DecimalFormat(value.signum() >= 0 ? "0000000000.00" : "000000000.00").format(value);
        String str = String.format("%s%s", v.substring(0, 10), v.substring(11));
        return str;
    }

    /**
     * 字符串截取 半个中文字符用空格代替
     * @param value
     * @param length
     * @return
     */
    public String subStringValue(String value, int length) {
        StringBuilder sb = new StringBuilder();
        int strLength = value.length();
        int charLength = 0;
        for (int i = 0; i < strLength; i++) {
            int assicCode = value.codePointAt(i);
            if (assicCode > 0 && assicCode <= 255) {
                charLength += 1;
            } else {
                charLength += 2;
            }

            if (charLength < length) {
                sb.append(value.charAt(i));
            } else if (charLength == length) {
                sb.append(value.charAt(i));
                break;
            } else {
                sb.append(" ");
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @param isTrim
     * @return
     */
    public static boolean isEmpty(Object str, boolean isTrim) {
        if (isTrim) {
            return (str == null || "".equals(str.toString().trim()));
        } else {
            return (str == null || "".equals(str));
        }
    }

    /**
     * 布尔类型转换成字符"0"或"1"
     * @param value
     * @return
     */
    public static String convertBooleanToStringNum(boolean value) {
        return value ? "1" : "0";
    }

    /**
     * 判断字符串是否为空并分割成list
     * @param str
     * @return
     */
    public static List<Long> isEmptyAndSplit(String str) {
        List<Long> list = null;
        String[] split = null;
        if (str != null && !"".equals(str)) {
            split = str.split(",");
            list = new ArrayList<Long>();
            for (int i = 0; i < split.length; i++) {
                list.add(Long.valueOf(split[i]));
            }
        } else {
            list = null;
        }
        return list;
    }

    /**
     * 把null转换成""(适用于不是实体类的值 如:String,Integer,List<String>等)
     * @param param
     * @return
     */
    public static Object convertNullToEmpty(Object param) throws Exception {
        if (param == null) {
            return "";
        }
        return param;
    }

    /**
     * 把实体类中null转换成""<br>
     * 处理List<Object> 类型的数据（如：List<TestVO>）
     * @param param
     * @param listMap
     * @return
     */
    public static void convertNullToEmptyForEntity(Object param, List<Map<String, Object>> listMap) throws Exception {

        if (param == null) {
            return;
        }
        List<?> list1 = (List<?>) param;
        for (int j = 0; j < list1.size(); j++) {
            Object jec = list1.get(j);
            Map<String, Object> childMap = new HashMap<>();
            convertNullToEmptyForEntity(jec, childMap);

            if (!childMap.isEmpty()) {
                listMap.add(childMap);
            }
        }
    }

    /**
     * 把实体类中null转换成""
     * @param param
     * @param retMap
     * @return
     */
    public static void convertNullToEmptyForEntity(Object param, Map<String, Object> retMap) throws Exception {

        if (param == null) {
            return;
        }
        if (!(param instanceof Integer || param instanceof String || param instanceof Double || param instanceof Float || param instanceof Long || param instanceof Boolean || param instanceof Date)) {

            Class<?> class1 = param.getClass();
            Field[] fields = class1.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];

                // 获取当前属性名称
                String name = field.getName();

                // 获取当前属性值
                PropertyDescriptor pd = new PropertyDescriptor(name, class1);
                // 获得get方法
                Method getMethod = pd.getReadMethod();
                // 执行get方法返回一个Object
                Object value = getMethod.invoke(param);

                if (value instanceof Collection) {

                    Collection<?> collection = (Collection<?>) value;

                    // 子集合返回Map类型
                    List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
                    for (Object item : collection) {
                        Map<String, Object> childMap = new HashMap<>();
                        convertNullToEmptyForEntity(item, childMap);
                        if (!childMap.isEmpty()) {
                            listMap.add(childMap);
                        }
                    }

                    if (listMap != null && listMap.size() > 0) {
                        retMap.put(name, listMap);
                    } else {
                        retMap.put(name, value);
                    }

                } else {

                    // 如果值为空，返回一个""
                    if (value == null) {
                        retMap.put(name, "");
                    } else {
                        retMap.put(name, value);
                    }
                }
            }

        }
    }

    /**
     * 获取用户ID
     * @param userToken
     * @return
     */
    public static String getUserId(String userToken) {

        String[] tokenInfo = userToken.split("_");
        if (tokenInfo.length != 2) {
            return "";
        }
        return tokenInfo[0];
    }
}
