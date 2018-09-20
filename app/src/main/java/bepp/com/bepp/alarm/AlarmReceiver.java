package bepp.com.bepp.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.AlarmMainActivity;
import bepp.com.bepp.activities.AlarmaActivity;
import bepp.com.bepp.database.AlarmaDB;
import bepp.com.bepp.modelDB.Alarma;
import bepp.com.bepp.models.Alarm;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by charlie on 05/12/17.
 */

public class AlarmReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("BeeppBackAlarma", "en alarma reciver");

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);





        Bundle extras = intent.getExtras();
         int requestCode  = extras.getInt(AlarmaDB.idRequestCode);
         long fechaVencimiento = extras.getLong(AlarmaDB.FECHA_TERMINO);

        Log.d("Bepp", "On ReciverFecha Vencimiento " + simpleDateFormat.format(new Date(fechaVencimiento)));


        GregorianCalendar gregorianCalendarVencimiento = new GregorianCalendar();

        gregorianCalendarVencimiento.setTime(new Date(fechaVencimiento));

        Log.d("Bepp", "On ReciverFecha Vencimiento Gregorian " + simpleDateFormat.format(gregorianCalendarVencimiento.getTime()));

        GregorianCalendar gregorianCalendarAhora = new GregorianCalendar();

        Log.d("Bepp", "On ReciverFecha Vencimiento gregorianCalendarAhora " + simpleDateFormat.format(gregorianCalendarAhora.getTime()));


        String nombreMedicamento = extras.getString(AlarmaDB.medicamentoColumn);



        AlarmaDB alarmaDB = new AlarmaDB(context);
        Alarma alarma = alarmaDB.findByRequestCode(requestCode);

        Log.d("BeeppBackAlarma", "FechaVencimiento "+ fechaVencimiento);
        Log.d("BeeppBackAlarma", "GregorianCalendar "+GregorianCalendar.getInstance().getTimeInMillis());
        Log.d("BeeppBackAlarma", "comparacion "+ (fechaVencimiento < GregorianCalendar.getInstance().getTimeInMillis()));
        Log.d("BeeppBackAlarma", "Cierre ");

        Log.d("Bepp", "condicion " +gregorianCalendarAhora.after(gregorianCalendarVencimiento));
        if(  gregorianCalendarAhora.after(gregorianCalendarVencimiento) ){

            Log.d("Bepp", "entro  ");

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(requestCode);

        }else {
            Log.d("BeeppBackAlarma", "Mandar alerta ");
            //Stop sound service to play sound for alarm
            Intent intentSonido  = new Intent(context, AlarmSoundService.class);
            intentSonido.putExtra(AlarmaDB.medicamentoColumn, nombreMedicamento);
            context.startService(intentSonido);

            //This will send a notification message and show notification in notification tray


            ComponentName comp = new ComponentName(context.getPackageName(),
                    AlarmNotificationService.class.getName());
            startWakefulService(context, (intent.setComponent(comp)));

        }

    }


}
