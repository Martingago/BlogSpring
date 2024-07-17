package com.project.springBlog.utils;

public class ReflectionUtils {

    public static boolean hasField(Class<?> tClass, String fieldName){
        try {
            tClass.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

}
