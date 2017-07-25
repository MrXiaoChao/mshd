package com.zity.mshd.widegt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.blankj.utilcode.utils.NetworkUtils;

/**
 * Created by luochao on 2017/7/20.
 * 网络监控
 */

public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            Toast.makeText(context, "网络不可用,请去打开网络!", Toast.LENGTH_LONG).show();
        }else {
//            //改变背景或者 处理网络的全局变量
//            if(mobNetInfo.isConnected()){
//                Toast.makeText(context, "当前手机网络", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(context, "当前Wifi网络", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
