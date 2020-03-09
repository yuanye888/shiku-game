package com.shiku.robot.shikugame.untils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author lijun
 * @Description 游戏匹配规则类
 * @Date 2019-12-27 8:47 下午
 **/

@Log4j2
public class GameRuleUtil {
    static String[] cnArr = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    static String[] specialArr = new String[]{"-", "/", ":", ";", ",", ".", "!", "?", "_", "=", "+", "：", "，", "；", "！", "？", "。"};

    static String[] specialArrs = new String[]{"-", "/", ":", ";", ",", "[.]", "!", "\\?", "_", "=", "\\+", "：", "，", "；", "！", "？", "。"};

    public static JSONObject getBomb(String money, String greetings) {
        log.info("------------------------------------金额：" + money + ",祝福语：" + greetings + "------------------------------------");
        JSONObject resp = new JSONObject();
        money = money.split("[.]")[0];

        // 祝福语必须不为空
        if (StringUtil.isEmpty(greetings, true)) {
            resp.put("code", "-1");
            return resp;
        }

        // 将祝福语转为数字
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < greetings.length(); i++) {
            buffer.append(getArabicNum(String.valueOf(greetings.charAt(i))));
        }

        // 去除特殊符号
        String s1 = removeSpecial(buffer);

        // 判断去除特殊字符后判断是不是纯数字
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(s1);
        if (!m.matches()) {
            resp.put("code", "-7");
            return resp;
        }

        // 判断字符串是否有特殊字符
        String regEx = "[ -/:;,.!?_=+：，；！？。]";
        p = Pattern.compile(regEx);
        m = p.matcher(greetings);
        List<String> list = new ArrayList<>();
        if (!m.find()) {
            for (int i = 0; i < greetings.length(); i++) {
                String ss = String.valueOf(greetings.charAt(i));
                list.add(ss);
            }
            resp.put("code", "1");
            resp.put("data", list);
            return resp;
        } else {
            for (int i = 0; i < specialArr.length; i++) {
                //以特殊字符开头的
                if (greetings.startsWith(specialArr[i])) {
                    String str = greetings.substring(1, greetings.length());
                    m = p.matcher(str);
                    // 截取还有特殊字符不处理
                    if (m.find()) {
                        resp.put("code", "-2");
                        return resp;
                    } else {
                        for (int j = 0; j < str.length(); j++) {
                            String ss = String.valueOf(str.charAt(j));
                            list.add(ss);
                        }
                        resp.put("code", "1");
                        resp.put("data", list);
                        return resp;
                    }
                    // 以特殊字符结尾的
                } else if (greetings.endsWith(specialArr[i])) {
                    String str = greetings.substring(0, greetings.length() - 1);
                    m = p.matcher(str);
                    // 截取还有特殊字符不处理
                    if (m.find()) {
                        resp.put("code", "-3");
                        return resp;
                    } else {
                        for (int j = 0; j < str.length(); j++) {
                            String ss = String.valueOf(str.charAt(j));
                            list.add(ss);
                        }
                        resp.put("code", "1");
                        resp.put("data", list);
                        return resp;
                    }
                }
            }
            // 既没有开头也不是结尾的
            for (int j = 0; j < specialArr.length; j++) {
                boolean flga = greetings.contains(specialArr[j]);
                // 是否包含某个字符串
                if (flga) {
                    String[] str = greetings.split(specialArrs[j]);
                    // 超过1个特殊字符不处理
                    if (str.length > 2) {
                        resp.put("code", "-4");
                    } else {
                        for (int k = 0; k < str.length; k++) {
                            // 截取还有特殊字符不处理
                            if (m.find()) {
                                resp.put("code", "-5");
                                return resp;
                            } else {
                                p = Pattern.compile("[0-9]*");
                                // 开头是金额，结尾是雷
                                if (str[0].equals(money)) {
                                    for (int i = 0; i < str[1].length(); i++) {
                                        list.add(String.valueOf(str[1].charAt(i)));
                                    }
                                    resp.put("code", "1");
                                    resp.put("data", list);
                                    return resp;
                                    // 结尾是金额，开头是雷
                                } else if (str[1].equals(money)) {
                                    for (int i = 0; i < str[0].length(); i++) {
                                        list.add(String.valueOf(str[0].charAt(i)));
                                    }
                                    resp.put("code", "1");
                                    resp.put("data", list);
                                    return resp;
                                }
                            }
                        }
                    }
                }
            }
        }


        resp.put("code", "-6");
        return resp;
    }

    /**
     * 中文数字转阿拉伯数字
     *
     * @param chineseNum
     * @return
     */
    public static String getArabicNum(String chineseNum) {
        int result = 0;
        for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
            if (!chineseNum.equals(cnArr[j])) {
                result += 1;
            } else {
                return String.valueOf(result);
            }
        }
        return chineseNum;
    }

    /**
     * 去除特殊字符
     *
     * @param greetings
     * @return
     */
    public static String removeSpecial(StringBuffer greetings) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < greetings.length(); i++) {
            boolean flag = true;
            for (int j = 0; j < specialArr.length; j++) {
                if (specialArr[j].equals(String.valueOf(greetings.charAt(i)))) {
                    flag = false;
                }
            }
            if (flag) {
                buffer.append(greetings.charAt(i));
            }
        }
        return buffer.toString();
    }
}
