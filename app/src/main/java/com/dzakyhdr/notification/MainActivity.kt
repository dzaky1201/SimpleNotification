package com.dzakyhdr.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val CHANNEL_ID = "channelId"
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificatioChannel()

        //intent notifikasi
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //notifikasi
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notifikasi ini berhasil")
            .setContentText("Ini adalah konten dari notifikasi")
            .setSmallIcon(R.drawable.ic_star)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        button.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }

    }

    //cek versi untuk notifikasi
    fun createNotificatioChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}