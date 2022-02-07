package com.example.csi5175assignment1project_zixunxiang_yaqingzhu.service

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.R

class BackGroundMusic: Service() {
    lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.back);
        player.setLooping(true); // Set looping
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        player.start();
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }
}