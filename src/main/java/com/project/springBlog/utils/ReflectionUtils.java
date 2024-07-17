package com.project.springBlog.utils;

public class ReflectionUtils {

    public static boolean hasField(Class<?> tClass, String fieldName){
        if(fieldName == null){
            return true;
        }
        try {
            tClass.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

}
