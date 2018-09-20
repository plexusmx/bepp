package bepp.com.bepp.alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.AlarmMainActivity;
import bepp.com.bepp.activities.AlarmaActivity;
import bepp.com.bepp.database.AlarmaDB;

/**
 * Created by charlie on 05/12/17.
 */

public class AlarmNotificationService extends IntentService {
    private NotificationManager alarmNotificationManager;

    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 100;

    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        String nombreMedicamento = extras.getString(AlarmaDB.medicamentoColumn);



        //Send notification
        sendNotification("Es hora de tomar : " + nombreMedicamento);
    }

    //handle notification
    private void sendNotification(String msg) {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);



        Intent secondIntent =  new Intent(this, AlarmMainActivity.class);
        secondIntent.putExtra("esNotificacion", true);

        //get pending intent
        PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,
                secondIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        //Create notification
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this);

        Log.d("Bepp", "android.os.Build.VERSION.SDK_INT :  " + android.os.Build.VERSION.SDK_INT);


        Log.d("Bepp", "Build.VERSION_CODES.LOLLIPOP :  " + Build.VERSION_CODES.LOLLIPOP);


        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {

            alamNotificationBuilder

                    .setContentTitle("Alarma Bepp")
                    .setVibrate(new long[] { 1000, 1000})
                    .setSmallIcon(R.drawable.ic_letras_bepp)
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setLights(Color.RED, 1000, 1000)

                    //            .setContentTitle(title)
                    //                        .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText(msg).setAutoCancel(true);


        } else
        {
            alamNotificationBuilder

                    .setContentTitle("Alarma Bepp")
                    .setSmallIcon(R.drawable.ic_bepp_nt)

                    .setColor(getResources().getColor(R.color.colorPrimary))
            .setVibrate(new long[] { 1000, 1000})
                    .setLights(Color.RED, 1000, 1000)
                    //                                .setContentTitle(title)
                    //                        .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText(msg).setAutoCancel(true);
        }


        alamNotificationBuilder.setContentIntent(contentIntent);

        //notiy notification manager about new notification
        alarmNotificationManager.notify((int) (Math.random()*1000), alamNotificationBuilder.build());
    }


}
