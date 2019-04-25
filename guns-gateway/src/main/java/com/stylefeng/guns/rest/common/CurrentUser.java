package com.stylefeng.guns.rest.common;

/**
 * @author Wang Xueyang
 * @create 2019-04-25
 */
public class CurrentUser {
    // 线程绑定的存储空间
    private static final InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    public static void saveUserId(String userId){
        threadLocal.set(userId);
    }
    public static String getCurrentUser(){
        return threadLocal.get();
    }
}
