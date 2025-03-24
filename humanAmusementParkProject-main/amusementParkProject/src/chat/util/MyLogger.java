package javaproject.chat.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class MyLogger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    public static void log(Object obj){
        String time = LocalTime.now().format(formatter);
        System.out.printf("%s [%9s] %s\n",time,Thread.currentThread().getName(),obj);
    }
    public static Object log1(){
        return LocalTime.now().format(formatter);
    }
}
