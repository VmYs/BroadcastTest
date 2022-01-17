package com.example.broadcasttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.OutcomeReceiver
import android.widget.Toast
import com.example.broadcasttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var timeChangeReceiver: TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        //动态注册
        registerReceiver(timeChangeReceiver, intentFilter)

        //发送自定义标准广播
//        binding.button.setOnClickListener {
//            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
//            //在Android 8.0之后，静态注册的BroadcastReceiver是无法接收隐式广播的，而默认情况下我们发出的自定义广播恰恰都是隐式广播。
//            //因此这里一定要调用setPackage()方法，指定这条广播是发送给哪个应用程序的，从而让它变成一条显式广播，否则静态注册的BroadcastReceiver将无法接收到这条广播。
//            //packageName是getPackageName()的语法糖写法，用于获取当前应用程序的包名。
//            intent.setPackage(packageName)
//            sendBroadcast(intent)
//        }

        //发送自定义有序广播
        //有序广播在通过第一个接收器之后可拦截
        binding.button.setOnClickListener {
            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
            intent.setPackage(packageName)
            sendOrderedBroadcast(intent,null)
        }
    }

    //动态注册需要取消注册
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }

    inner class TimeChangeReceiver: BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "Time has changed", Toast.LENGTH_SHORT).show()
        }
    }
}