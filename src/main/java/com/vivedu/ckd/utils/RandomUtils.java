package com.vivedu.sls.utils;

import java.util.Random;

/***
 * 用于生成激活码
 */
public class RandomUtils {
    //  定义所有的字符组成的串
    public static final String allChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 产生长度为length的随机字符串（包括字母和数字）
     * @param length
     * @return
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }


}
