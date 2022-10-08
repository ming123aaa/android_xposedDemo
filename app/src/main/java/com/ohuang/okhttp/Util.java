package com.ohuang.okhttp;

public class Util {

    public static void tryCatch(Block block) {
        try {
            block.invoke();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
