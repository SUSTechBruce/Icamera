package com.example.a86182.icamera;
import android.content.pm.*;
import java.util.*;
import android.content.*;
public class PlatformUtil {
    public static final String PACKAGE_WECHAT = "com.tencent.mm";
    public static final String PACKAGE_MOBILE_QQ = "com.tencent.mobileqq";
    public static final String PACKAGE_QZONE = "com.qzone";
    public static final String PACKAGE_SINA = "com.sina.weibo";
    // 判断是否安装指定app

    /**
     *
     * @param context
     * @param appPackage name of app
     * @return open or not
     */
    public static boolean judgeInstalled(final Context context, final String appPackage){
        final PackageManager packageManager = context.getPackageManager();
        final List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        boolean flag = false;
        if (pInfo != null) {
//            for (int i = 0; i < pInfo.size(); i++) {
            for (final PackageInfo i:pInfo) {
                final String packageName = i.packageName;
                if (appPackage.equals(packageName)) {
                    flag = true;
                }
            }
        }
        return flag;
    }
}
