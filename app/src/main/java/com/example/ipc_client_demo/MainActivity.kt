package com.example.ipc_client_demo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.ipc_service_demo.IUserMsg

class MainActivity : AppCompatActivity() {
    private  val TAG = "IPC_CLIENT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ipc 客户端代码
        var myBinder: IUserMsg? = null

        val clientIntent = Intent()
        //对应 service的 包名
        clientIntent.`package` = "com.example.ipc_service_demo"
        //manifest 中的  action
        clientIntent.action = "com.example.serviceipc.service"

        bindService(clientIntent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d(TAG, " onServiceConnected name  = $name , service  = $service , ")
                myBinder = IUserMsg.Stub.asInterface(service)

                Log.d(TAG, "获取到服务端信息： name  = ${myBinder?.name} , pwd  = ${myBinder?.pwd}  ")
            }

            override fun onServiceDisconnected(name: ComponentName?) {

                Log.d(TAG, " onServiceDisconnected name  = $name ")
            }

        }, Context.BIND_AUTO_CREATE)
    }
}