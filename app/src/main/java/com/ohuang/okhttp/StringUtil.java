package com.ohuang.okhttp;

public class StringUtil {

    /**
     * 超过多少字符 添加换行符
     * @param input
     * @param maxLineLength
     * @return
     */
    public static String addNewlines(String input, int maxLineLength) {
        StringBuilder output = new StringBuilder();
        int start = 0;
        int end = maxLineLength;
        while (end <= input.length()) {
            output.append(input.substring(start, end));
            output.append('\n');
            start += maxLineLength;
            end += maxLineLength;
        }
        if (start < input.length()) {
            output.append(input.substring(start));
        }
        return output.toString();
    }
}
