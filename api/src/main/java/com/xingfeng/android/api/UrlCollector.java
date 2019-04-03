package com.xingfeng.android.api;

import android.app.Activity;

import java.util.Map;

/**
 * @Author: 王立
 * @Date: 2019/3/28 20:52
 * @Desc:
 */
public interface UrlCollector {

    Map<String, Class<? extends Activity>> getUrlRouterMap();

}
