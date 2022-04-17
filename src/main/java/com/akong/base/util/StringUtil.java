package com.akong.base.util;

import java.util.Optional;
import java.util.Stack;

/**
 * 字符串帮助类
 *
 * @author Akong
 * @create 2021/11/25 19:45
 */
public class StringUtil {

    private static final char[] array = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();

    /**
     * 十进制转N进制
     *
     * @param number 需要转换的十进制数
     * @param N      转为N进制
     * @return 返回转换后的数值
     */
    public static String _10_to_N(long number, int N) {
        long rest = number;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(array[new Long((rest % N)).intValue()]);
            rest = rest / N;
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.length() == 0 ? "0" : result.toString();

    }

    /**
     * N进制转十进制
     *
     * @param number 要转为十进制的数据
     * @param N      N进制
     * @return 返回十进制的数据
     */
    public static long N_to_10(String number, int N) {
        char[] ch = number.toCharArray();
        int len = ch.length;
        long result = 0;
        if (N == 10) {
            return Long.parseLong(number);
        }
        long base = 1;
        for (int i = len - 1; i >= 0; i--) {
            String numStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int index = numStr.indexOf(ch[i]);
            result += index * base;
            base *= N;
        }

        return result;
    }

    /**
     * 判断不为空
     *
     * @param obj 判断的值
     * @return 不为空则返回true
     */
    public static boolean isNotBlank(Object obj) {
        return !Optional.ofNullable(obj).orElse("").equals("");
    }
}
