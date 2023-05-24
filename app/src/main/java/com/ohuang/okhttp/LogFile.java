package com.ohuang.okhttp;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {
    public static String toDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date = new Date(); // 获取当前时间
        String format = sdf.format(date);
        return format;
    }

    public static void logDate(String s){

        log(toDate()+" / "+s);
    }
    public static void log(String s) {
        LogFile.addLog(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/com.ohuang.okhttp" + "/logFile.log", s);
    }

    public static void addLog(String path,String data){

            //每次写入时，都换行写
            String strContent=data+"\n";
            try {
                File file = new File(path);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()){
                    parentFile.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                raf.seek(file.length());
                raf.write(strContent.getBytes());
                raf.close();
            } catch (Exception e) {
                Log.e("TestFile", "Error on write File."+e.toString());
            }

    }
}
