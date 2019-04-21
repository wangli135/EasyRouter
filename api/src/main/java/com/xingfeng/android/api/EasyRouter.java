package com.xingfeng.android.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.net.URI;
import java.util.Map;

/**
 * @Author: 王立
 * @Date: 2019/3/28 20:51
 * @Desc:
 */
public class EasyRouter {

    private static final String URL_COLLECTOR_IMPL_CLASS_NAME = "com.xingfeng.android.api.AppUrlCollectorImpl";

    private static volatile EasyRouter instance = null;

    private Map<String, Class<? extends Activity>> urlRouterMap;

    //scheme://host/path?query
    private String scheme;
    private String host;
    private RouterListener routerListener;

    public void setRouterListener(RouterListener routerListener) {
        this.routerListener = routerListener;
    }

    private EasyRouter() {
    }

    public static EasyRouter getInstance() {
        if (instance == null) {
            synchronized (EasyRouter.class) {
                if (instance == null) {
                    instance = new EasyRouter();
                }
            }
        }
        return instance;
    }


    /**
     * 初始化
     *
     * @return true表示成功, false表示失败
     */
    public boolean init(String scheme, String host) {
        try {
            UrlCollector urlCollector = (UrlCollector) Class.forName(URL_COLLECTOR_IMPL_CLASS_NAME).newInstance();
            urlRouterMap = urlCollector.getUrlRouterMap();
            this.scheme = scheme;
            this.host = host;
            //Hook
            InstrumentationHook.attachContext();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 添加一个module
     *
     * @param urlCollector
     * @return
     */
    public void addModule(UrlCollector urlCollector) {
        if (urlCollector != null) {
            urlRouterMap.putAll(urlCollector.getUrlRouterMap());
        }
    }

    public void addUrl(String url, Class<? extends Activity> clazz) {
        if (urlRouterMap == null) {
            return;
        }
        urlRouterMap.put(url, clazz);
    }

    private String currentUrl;//当前处理的URL

    public String getCurrentUrl() {
        return currentUrl;
    }

    public boolean goToPages(Context context, String url) {
        boolean find = false;
        if (TextUtils.isEmpty(url)) {
            return find;
        }
        //判断是否拦截
        if (routerListener != null && routerListener.onIntercept(url)) {
            find = true;
            //保存URL
            currentUrl = url;
            return find;
        }

        URI uri = URI.create(url);
        String urlScheme = uri.getScheme();
        String urlHost = uri.getHost();
        String urlPath = uri.getPath();
        String urlQuery = uri.getQuery();

        //scheme和host要么都不为null，要么都为null
        boolean rightUrl = checkRightUrl(urlScheme, urlHost);
        if (!rightUrl) {
            if (routerListener != null) {
                routerListener.onLost(url);
            }
            return find;
        }

        //判断是否是内部识别的url，绝对url的话，scheme和host匹配；或者是相对url
        boolean isInnerUrl = checkInnerUrl(urlScheme, urlHost);
        if (!isInnerUrl) {
            if (routerListener != null) {
                routerListener.onLost(url);
            }
            return find;
        }

        for (String key : urlRouterMap.keySet()) {
            //key肯定是大于等于urlPath的，包含了绝对URL和相对URL
            if (key.endsWith(urlPath)) {
                find = true;
                if (routerListener != null) {
                    routerListener.onFound(url);
                }
                //在跳转之前先由客户端进行判断
                Intent intent = new Intent(context, urlRouterMap.get(key));
                if (!TextUtils.isEmpty(urlQuery)) {
                    try {
                        String[] queryParametes = urlQuery.split("&");
                        for (String paramete : queryParametes) {
                            String[] extras = paramete.split("=");
                            intent.putExtra(extras[0], extras[1]);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //清除URL
                currentUrl = null;
                context.startActivity(intent);
                break;
            }
        }
        //没有找到
        if (!find && routerListener != null) {
            routerListener.onLost(url);
        }
        return find;
    }

    /**
     * 检查是否是内部url
     *
     * @param urlScheme
     * @param urlHost
     * @return true表示是，false表示不是
     */
    private boolean checkInnerUrl(String urlScheme, String urlHost) {

        if (!TextUtils.isEmpty(urlScheme) && scheme.equals(urlScheme) && host.equals(urlHost)) {
            return true;
        }

        if (TextUtils.isEmpty(urlScheme) && TextUtils.isEmpty(urlHost)) {
            return true;
        }

        return false;
    }

    /**
     * 检查url合法性
     *
     * @param urlScheme
     * @param urlHost
     * @return true表示合法，false表示不合法
     */
    private boolean checkRightUrl(String urlScheme, String urlHost) {
        if (TextUtils.isEmpty(urlScheme) && TextUtils.isEmpty(urlHost)) {
            return true;
        }

        if (!TextUtils.isEmpty(urlScheme) && !TextUtils.isEmpty(urlHost)) {
            return true;
        }

        return false;

    }

    public interface RouterListener {
        //true表示拦截
        boolean onIntercept(String url);

        //没找到
        void onLost(String url);

        //找到了
        void onFound(String url);

    }

}
