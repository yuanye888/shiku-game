package com.shiku.robot.shikugame.untils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author lijun
 * @Description 红包接口授权类
 * @Date 2019-12-27 12:21 下午
 **/

public class AuthServiceUtils {

    /**
     * 检验授权 红包相关接口
     *
     * @param apiKey
     * @param payPassword
     * @param userId
     * @param token
     * @param time
     * @return
     */
    public static String getRedPacketSecret(String apiKey, String payPassword, String userId, String token, long time) {

        /**
         * 密钥
         md5( md5(apikey+time) +userid+token)
         */

        /**
         * apikey+time
         */
        String apiKey_time = new StringBuffer()
                .append(apiKey)
                .append(time).toString();

        /**
         * userid+token
         */
        String userid_token = new StringBuffer()
                .append(userId)
                .append(token).toString();
        /**
         * payPassword
         */
        String md5payPassword = payPassword;
        /**
         * md5(apikey+time)
         */
        String md5ApiKey_time = DigestUtils.md5Hex(apiKey_time);

        /**
         *  md5(apikey+time) +userid+token+payPassword
         */
        String key = new StringBuffer()
                .append(md5ApiKey_time)
                .append(userid_token)
                .append(md5payPassword).toString();

        return DigestUtils.md5Hex(key);
    }

    public static boolean authRedPacket(String apiKey, String userId, String token, long time, String secret) {
        if (!authRequestTime(time)) {
            return false;
        }
        if (StringUtil.isEmpty(secret, true)) {
            return false;
        }

        String secretKey = getRedPacketSecret(apiKey, userId, token, time);

        if (!secretKey.equals(secret)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 检验接口请求时间
     *
     * @param time
     * @return
     */
    public static boolean authRequestTime(long time) {
        long currTime = System.currentTimeMillis() / 1000;
        //允许 5分钟时差
        if (((currTime - time) < 600 && (currTime - time) > -600)) {
            return true;
        } else {
            System.out.println(String.format("====> authRequestTime error server > %s client %s", currTime, time));
            return false;
        }
    }

    public static String getRedPacketSecret(String apiKey, String userId, String token, long time) {

        /**
         * 密钥
         md5( md5(apikey+time) +userid+token)
         */

        /**
         * apikey+time
         */
        String apiKey_time = new StringBuffer()
                .append(apiKey)
                .append(time).toString();

        /**
         * userid+token
         */
        String userid_token = new StringBuffer()
                .append(userId)
                .append(token).toString();

        /**
         * md5(apikey+time)
         */
        String md5ApiKey_time = DigestUtils.md5Hex(apiKey_time);

        /**
         *  md5(apikey+time) +userid+token+payPassword
         */
        String key = new StringBuffer()
                .append(md5ApiKey_time)
                .append(userid_token).toString();

        return DigestUtils.md5Hex(key);

    }
}
