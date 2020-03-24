package ru.adfmp.officegym.utils.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ru.adfmp.officegym.MainActivity
import ru.adfmp.officegym.R

class AlarmNotification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.putExtra("workout_id", intent.getLongExtra("workout_id", -1))
        val resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = getNotificationBuilder(context)
            .setSmallIcon(R.drawable.sport)
            .setContentTitle("OfficeJym")
            .setContentText("Time to exercise!")
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)

        val notification = builder.build()

        val notificationManager = getNotificationManager(context)
        notificationManager.notify(1, notification)
    }

    private fun getNotificationManager(context: Context) =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    private fun getNotificationBuilder(context: Context) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = getNotificationChannel()
        val manager = getNotificationManager(context)
        manager.createNotificationChannel(channel)
        NotificationCompat.Builder(context, channel.id)
    } else {
        @Suppress("DEPRECATION")
        NotificationCompat.Builder(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel() =
        NotificationChannel("111", "AlarmChannel", NotificationManager.IMPORTANCE_DEFAULT)
}
