package bepp.com.bepp.activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import bepp.com.bepp.R;
import bepp.com.bepp.alarm.AlarmNotificationService;
import bepp.com.bepp.alarm.AlarmReceiver;
import bepp.com.bepp.alarm.AlarmSoundService;
import bepp.com.bepp.database.AlarmaDB;
import bepp.com.bepp.modelDB.Alarma;


public class AlarmaActivity extends AppCompatActivity {

    private EditText textCantidad, textAlarmaNombre,textHora, textDias;
    private ImageView imagecancelar,saveAlarmData;
    //Pending intent instance
    private SQLiteDatabase bd;
    private PendingIntent pendingIntent;
    private  AlarmaDB admin;
    private Cursor fila;
    private NotificationManager notificationManager;
    private String nombreMedicamento,cantidad;
   // private  Intent alarmIntent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        Toolbar principal = (Toolbar) findViewById(R.id.toolbar_edit);
        principal.setTitle("");
        setSupportActionBar(principal);


        textCantidad = (EditText) findViewById(R.id.text_cantidad);
        textAlarmaNombre = (EditText) findViewById(R.id.text_alarma_nombre);
        textHora = (EditText) findViewById(R.id.text_hora);
        textDias = (EditText) findViewById(R.id.text_dias);

        imagecancelar = (ImageView) findViewById(R.id.imagecancelar);
        saveAlarmData = (ImageView) findViewById(R.id.save_alarm_data);


        imagecancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentAlarmMain = new Intent(AlarmaActivity.this, AlarmMainActivity.class);
                startActivity(intentAlarmMain);
            }
        });


        saveAlarmData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlarmaDB alarmaDB = new AlarmaDB(getBaseContext());
                Alarma alarm = new Alarma();
                int requestCode = alarmaDB.maxIdAlarms();
                Log.d("Bepp", "requestCode Inicial " + requestCode);






                String numeroDiasString = String.valueOf(textDias.getText());
                numeroDiasString = numeroDiasString != null && numeroDiasString.matches("\\d+") ? numeroDiasString : "1";
                int numerodias = new Integer(numeroDiasString);

                String numeroHorasString = String.valueOf(textHora.getText());
                numeroHorasString = numeroHorasString != null && numeroHorasString.matches("\\d+") ? numeroHorasString : "1";
                int numeroHoras = new Integer(numeroHorasString);



                String pattern = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


                Calendar c = new GregorianCalendar();
                Log.d("Bepp", "Ahora " + simpleDateFormat.format(c.getTime()));

                c.add(Calendar.DAY_OF_MONTH, numerodias);

                Log.d("Bepp", "Fin " + simpleDateFormat.format(c.getTime()));


                Long fechaVencimiento = c.getTimeInMillis();

                Log.d("Bepp", "Fin Long " + simpleDateFormat.format(new Date(fechaVencimiento)));

                Log.d("Bepp", "FechaTermino " + fechaVencimiento);





                String cantidadf = textAlarmaNombre.getText().toString();
                alarm.setNombreMedicamento(textAlarmaNombre.getText().toString());
                alarm.setRequestCode(requestCode);
                alarm.setCantidad(textCantidad.getText().toString());
                alarm.setFecha(textDias.getText().toString());
                alarm.setTiempo(textHora.getText().toString());
                alarm.setFechaTemino(fechaVencimiento);



                Log.d("Bepp", "alarma :  " + alarm);

                if (alarmaDB.create(alarm)) {

                    //Intent intenAlarmaCreacion = new Intent(AlarmaActivity.this, AlarmMainActivity.class);
                    //startActivity(intenAlarmaCreacion);


                   Intent alarmIntent = new Intent(AlarmaActivity.this, AlarmReceiver.class);
                    alarmIntent.putExtra(AlarmaDB.FECHA_TERMINO, alarm.getFechaTemino());
                    alarmIntent.putExtra(AlarmaDB.idRequestCode, alarm.getRequestCode());
                    alarmIntent.putExtra(AlarmaDB.medicamentoColumn, alarm.getNombreMedicamento());
                    triggerAlarmManager(requestCode, numeroHoras, alarmIntent);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlarmaActivity.this);
                    builder.setMessage("Fail");
                    builder.setCancelable(false);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    builder.create().show();

                }




            }
        });


    }

    public void triggerAlarmManager(int requestCode , int hours , Intent alarmIntent) {

        final AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Intent alarmIntent;
        PendingIntent pi;
        /*Cursor fila;
        long firstMillis = System.currentTimeMillis(); //first run of alarm is immediate // aranca la palicacion



        Calendar calendar = Calendar.getInstance();
        int hora, min, dia, mes, anio;
        String cadenaF, cadanaH, fecha_sistema, hora_sistema;

        alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);

        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anio = calendar.get(Calendar.YEAR);
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        fecha_sistema = mes + "-" + dia + "-" + anio + " ";
        hora_sistema = hora + ":" + min;

        admin = new AlarmaDB(this);
        bd = admin.getReadableDatabase();*/

        Log.d("BeeppBack", "Alarma Service 2");
       // if (bd != null) {
       //     fila = bd.rawQuery("SELECT * FROM alarma WHERE fecha='" + fecha_sistema + "' AND hora= '" + hora_sistema + "'", null);
       //     if (fila.moveToFirst()) {

          //      nombreMedicamento = fila.getString(1);
          //      cantidad = fila.getString(3);
              //  Log.d("BeeppBack", "Alarma Service 3");

             //   do {


        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Calendar c = new GregorianCalendar();

        Log.d("Bepp", "Ahora HORA " + simpleDateFormat.format(c.getTime()));

        c.add(Calendar.HOUR_OF_DAY, hours);

        Log.d("Bepp", "Fin HORA " + simpleDateFormat.format(c.getTime()));

        //c.add(Calendar.MINUTE, hours);
        Long fechaInicio = c.getTimeInMillis();

        Log.d("Bepp", "Fin HORA Long " + simpleDateFormat.format(new Date(fechaInicio)));

        // Para horas exactas
        int intervalMillis = hours * 60 * 60 * 1000;


        Log.d("Bepp", "Fin HORA Long 2 " + simpleDateFormat.format(new Date(fechaInicio + (1 * intervalMillis) )));
        Log.d("Bepp", "Fin HORA Long 3 " + simpleDateFormat.format(new Date(fechaInicio + (2 * intervalMillis) )));
        Log.d("Bepp", "Fin HORA Long 4 " + simpleDateFormat.format(new Date(fechaInicio + (3 * intervalMillis) )));
        Log.d("Bepp", "Fin HORA Long 5 " + simpleDateFormat.format(new Date(fechaInicio + (4 * intervalMillis) )));
        Log.d("Bepp", "Fin HORA Long 6 " + simpleDateFormat.format(new Date(fechaInicio + (5 * intervalMillis) )));


        // Para minutos para pruebas
       // intervalMillis = hours * 60 * 1 * 1000;


        pi = PendingIntent.getBroadcast(this, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP,fechaInicio ,intervalMillis, pi);
        Log.d("BeeppBack", "Alarma Service 4");
        Log.d("Beep", "fechaInicio" +fechaInicio);
        Log.d("Beep", "intervalMillis" +intervalMillis);

        //Toast.makeText(this, "Se creo la alarma" , Toast.LENGTH_LONG).show();

        Intent intentListarAlrms = new Intent(this, AlarmMainActivity.class);
        startActivity(intentListarAlrms);

               // } while (fila.moveToNext());
         //   }
        //}
       // bd.close();


    }


    //Stop/Cancel alarm manager
    public void stopAlarmManager() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent


        //Stop the Media Player Service to stop sound
        stopService(new Intent(AlarmaActivity.this, AlarmSoundService.class));

        //remove the notification from notification tray

        //Toast.makeText(this, "Alarma cancelada/detenida .", Toast.LENGTH_SHORT).show();
    }

}