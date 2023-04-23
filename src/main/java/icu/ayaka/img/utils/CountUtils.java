package icu.ayaka.img.utils;

public class CountUtils {

    /**
     * 生成 表示数组
     * @param max
     * @param num
     * @return
     */
    public static char[] byArray(String max, String num, int line) {

        if (!num.matches("^[1-9]\\d*$") || !max.matches("^[1-9]\\d*$")) {
            return null;
        }

        // 最大位数
        int m = Integer.parseInt(max);
        // 数字长度
        int n = num.length();
        // 判断是否越界
        if (m > line || n > line){
            return null;
        }

        // 字符数组
        char[] charArray = num.toCharArray();

        // 最大位数 >= 数字长度 --> 进行补零操作
        if (m >= n) {
            int zone = m - n;
            char [] arr = new char[m];
            for (int i = 0; i < arr.length; i++) {
                if (i < zone) {
                    arr[i] = '0';
                } else {
                    arr[i] = charArray[i - zone];
                }
            }
            return arr;
        } else {
            // 不需要补零，直接返回
            return charArray;
        }
    }

}
