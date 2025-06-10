package com.example.aletheia

import android.Manifest
import android.animation.ObjectAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


const val CHANNEL_ID = "AletheiaChannel"

fun createNotificationChannel(context: Context) {
    // Créer le canal de notification pour Android 8.0 et supérieur
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Aletheia Notifications"
        val descriptionText = "Notifications de streaming EEG"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Enregistrer le canal de notification
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun showNotification(context: Context, message: String) {
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info) // Icône de la notification
        .setContentTitle("Aletheia")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true) // Supprimer la notification après un clic
        .build()

    // Afficher la notification
    val notificationManagerCompat = NotificationManagerCompat.from(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    notificationManagerCompat.notify(1, notification) // 0 est l'ID de la notification
}

fun showFloatingToast(context: Context, message: String) {
    // Inflate a custom layout
    val layout: View = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null)

    // Get the TextView from the layout
    val text: TextView = layout.findViewById(R.id.toast_text)
    text.text = message

    /*
    // Customize the TextView properties (font, color, etc.)
    text.setTextColor(0xFFFFFFFF.toInt()) // Changer la couleur du texte
    text.setTextSize(18f) // Modifier la taille du texte

     */

    // Create the Toast
    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 120)  // Positionner en bas
    toast.view = layout // Set the custom view for the toast
    val fadeIn = ObjectAnimator.ofFloat(layout, "alpha", 0f, 1f)
    fadeIn.duration = 500
    fadeIn.start()
    toast.show()
}

/*
fun showFloatingToast(context: Context, message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100) // Positionne le toast en haut
    toast.show()
}

 */