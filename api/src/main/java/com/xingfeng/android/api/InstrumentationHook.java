package com.xingfeng.android.api;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: 王立
 * @Date: 2019/4/9 20:37
 * @Desc: Hook startActivity
 */
public class InstrumentationHook extends Instrumentation {

    private Instrumentation base;

    public InstrumentationHook(Instrumentation base) {
        this.base = base;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        //如果有URL，说明之前拦截过，交给路由继续执行
        if (!TextUtils.isEmpty(EasyRouter.getInstance().getCurrentUrl())) {
            EasyRouter.getInstance().goToPages(who, EasyRouter.getInstance().getCurrentUrl());
            return null;
        }

        // 开始调用原始的方法, 调不调用随你,但是不调用的话, 所有的startActivity都失效了.
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(base, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            // 某该死的rom修改了  需要手动适配
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }


    public static void attachContext() throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // 创建代理对象
        Instrumentation evilInstrumentation = new InstrumentationHook(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);
    }


}
