package org.devio.rn.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
    private static Dialog mSplashDialog;
    private static WeakReference<Activity> mActivity;

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final boolean fullScreen) {
        if (activity == null) return;
        mActivity = new WeakReference<Activity>(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {

                    mSplashDialog = new Dialog(activity, fullScreen ? R.style.SplashScreen_Fullscreen : R.style.SplashScreen_SplashTheme);
                    mSplashDialog.setCancelable(false);

                    if (!mSplashDialog.isShowing()) {
                        mSplashDialog.show();
                    }
                }
            }
        });
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity) {
        show(activity, false);
    }

    /**
     * 关闭启动屏
     */
    public static void hide(final Activity a) {
        Activity referencedActivity = a;
        if (referencedActivity == null) {
            if (mActivity == null) {
                return;
            }
            referencedActivity = mActivity.get();
        }
        if (referencedActivity == null) return;

        referencedActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog != null && mSplashDialog.isShowing()) {
                    mSplashDialog.dismiss();
                    mSplashDialog = null;
                }
            }
        });

        // an ugly hack to make the background white so that the color behind keyboard is white
        // after a delay so that there is no white flicker in status bar when the splash screen goes away
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(350);
                if (a != null) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            View root = a.findViewById(android.R.id.content).getRootView();
                            root.setBackgroundColor(Color.WHITE);
                        }
                    });
                }
            }
        }).start();
    }
}
