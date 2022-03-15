package com.developer.onboardingfinalprojecthcl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.NonDisposableHandle.parent

class NotificationBuilder : AppCompatActivity() {

    private lateinit var return_Intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun createNotificationChannel(intent: Intent, notificationManager: NotificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        return_Intent = intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Return to app channel"
            val descriptionText = "Channel to return to app"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun makeNotification( intent: Intent, con: Context, notificationManager: NotificationManager )
    {
        createNotificationChannel(intent, notificationManager)
        sendNotification( con )
    }

    fun sendNotification(con: Context)
    {


// Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(con).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(return_Intent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        var builder = NotificationCompat.Builder(con, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("MESSAGE RECEIVED")
            .setContentText("Click here to return to Robotland!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent( resultPendingIntent )

        with(NotificationManagerCompat.from(con)) {
            // notificationId is a unique int for each notification that you must define
            val notificationId: Int = 5
            notify(notificationId, builder.build())
        }
    }
}