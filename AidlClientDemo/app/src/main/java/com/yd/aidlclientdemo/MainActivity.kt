package com.yd.aidlclientdemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Process
import android.util.Log
import android.view.View
import android.widget.Toast
import com.yd.aidlserverdemo.IServece

class MainActivity : AppCompatActivity() {

    var TAG = javaClass.simpleName
    var iServece: IServece? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    var mConnect: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.e(TAG, "------onServiceDisconnected")
            iServece = null;

        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.e(TAG, "Client------bind success-----" + p0.toString())

            iServece = IServece.Stub.asInterface(p1);

            var pid = (this@MainActivity.iServece as IServece).processId

            Log.e(TAG, "currentPID: " + Process.myPid() + "  remotePID: " + pid);

        }
    }

    fun buttonClick(v: View) {
        when (v.id) {
            R.id.btn_bind -> {
                if (iServece != null) return
                Log.d(TAG, "Client-----bindServer------:")
                val intent = Intent()
                //5.0只允许显示意图
                intent.setComponent(ComponentName("com.yd.aidlserverdemo", "com.yd.aidlserverdemo.RemoteService"))
                bindService(intent, mConnect, Context.BIND_AUTO_CREATE)
            }

            R.id.btn_send -> {
                if (iServece != null && iServece is IServece) {
                    (iServece as IServece).basicTypes(123, 1234, true, 12.3f, 12.3, "来自客户端内容");
                    val p = (iServece as IServece).animal
                    Log.d(TAG, "Client-----来自remote------animal:" + p.toString())
                }
            }
            else -> {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnect)
    }
}
