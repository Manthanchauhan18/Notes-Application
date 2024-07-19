package com.example.notesdemo.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DeleteNotification: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "ACTION_DISMISS") {
            Log.e("Dissmiss", "ACTION_DISMISS received")
            // Handle the dismiss action here
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(1)
        }

    }
}