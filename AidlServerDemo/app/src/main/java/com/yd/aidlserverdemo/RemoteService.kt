package com.yd.aidlserverdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.os.RemoteException
import android.util.Log
import com.yd.aidlserverdemo.bean.Animal

/**
 * @Description
 * @Auther yedong
 * @Date 2018/9/13 21:53
 */
class RemoteService : Service() {

    var TAG = javaClass.simpleName

    private val iServece = object : IServece.Stub() {
        override fun getAnimal(): Animal {
            return Animal("form remote animal")
        }

        @Throws(RemoteException::class)
        override fun getProcessId(): Int {
            Log.d(TAG, "Server-----getProcessId------thread:" + Thread.currentThread())
            Log.d(TAG, "Server-----getProcessId------pid:" + Process.myPid())
            return Process.myPid()
        }

        @Throws(RemoteException::class)
        override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String) {
            Log.d(TAG, "Thread: " + Thread.currentThread().name)
            Log.d(TAG, "basicTypes  anInt: $anInt||aBoolean $aBoolean||aDouble: $aDouble||aString $aString")
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "-----onCreate------")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "-----onBind------")
        return iServece
    }
}
