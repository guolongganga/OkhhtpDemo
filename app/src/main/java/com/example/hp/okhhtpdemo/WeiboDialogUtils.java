package com.example.hp.okhhtpdemo;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 * @CSDN博客: http://blog.csdn.net/qq_21376985
 */

public class WeiboDialogUtils  extends Dialog {

    private static WeiboDialogUtils mLoadingProgress;

    public WeiboDialogUtils(Context context) {
        super(context);

    }

    public WeiboDialogUtils(Context context, int theme) {
        super(context, theme);
    }

    public static void showprogress(Context context, CharSequence message, boolean iscanCancel) {
        mLoadingProgress = new WeiboDialogUtils(context, R.style.MyDialogStyle);//自定义style文件主要让背景变成透明并去掉标题部分<!-- 自定义loading dialog -->


        //触摸外部无法取消,必须
        mLoadingProgress.setCanceledOnTouchOutside(false);

        mLoadingProgress.setTitle("");
        mLoadingProgress.setContentView(R.layout.dialog_loading);
        mLoadingProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if(message==null|| TextUtils.isEmpty(message)){
            mLoadingProgress.findViewById(R.id.tipTextView).setVisibility(View.GONE);
        }else {
            TextView tv = (TextView) mLoadingProgress.findViewById(R.id.tipTextView);
            tv.setText(message);
        }
        //按返回键响应是否取消等待框的显示
        mLoadingProgress.setCancelable(iscanCancel);

        mLoadingProgress.show();



//    public static Dialog createLoadingDialog(Context context, String msg) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
//        LinearLayout layout = (LinearLayout) v
//                .findViewById(R.id.dialog_loading_view);// 加载布局
//        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
//        tipTextView.setText(msg);// 设置加载信息
//
//        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
//        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
//        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
//        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
//        /**
//         *将显示Dialog的方法封装在这里面
//         */
//        Window window = loadingDialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setGravity(Gravity.CENTER);
//        window.setAttributes(lp);
//        window.setWindowAnimations(R.style.PopWindowAnimStyle);
//        loadingDialog.show();
//
//        return loadingDialog;
//    }
//
//    /**
//     * 关闭dialog
//     *
//     * http://blog.csdn.net/qq_21376985
//     *
//     * @param mDialogUtils
//     */
//    public static void closeDialog(Dialog mDialogUtils) {
//        if (mDialogUtils != null && mDialogUtils.isShowing()) {
//            mDialogUtils.dismiss();
//        }
//    }

    }

    public static void dismissprogress(){
        if(mLoadingProgress!=null){

            mLoadingProgress.dismiss();
        }
    }
}