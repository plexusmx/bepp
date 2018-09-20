package bepp.com.bepp.activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.Calendar;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.AlarmsAdapter;
import bepp.com.bepp.alarm.AlarmNotificationService;
import bepp.com.bepp.alarm.AlarmReceiver;
import bepp.com.bepp.alarm.AlarmService;
import bepp.com.bepp.alarm.AlarmSoundService;
import bepp.com.bepp.database.AlarmaDB;
import bepp.com.bepp.models.Alarm;

public class AlarmMainActivity extends AppCompatActivity {

    private ListView mListView;
    private AlarmsAdapter mAdapter;
    private Button btnNvoAlarma, btnDetener;

    //Alarma

    private static final String REQUEST_CODE = "REQUEST_CODE";
    //Pending intent instance
    private SQLiteDatabase bd;
    private PendingIntent pendingIntent;
    private  AlarmaDB admin;
    private Cursor fila;
    private NotificationManager notificationManager;
    private String nombreMedicamento,cantidad;
    private  int requestCode;
    private  Intent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_main);


        if(getIntent().hasExtra("esNotificacion") &&  getIntent().getBooleanExtra("esNotificacion" , true)){

            stopService(new Intent(this, AlarmSoundService.class));

        }


        btnNvoAlarma =(Button) findViewById(R.id.button_nva_alarma);
        btnDetener  =(Button) findViewById(R.id.button_detener);
        mListView = (ListView) findViewById(R.id.list_alarmas);



        btnDetener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(AlarmMainActivity.this, AlarmSoundService.class));
            }
        });



        btnNvoAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenAlarm = new Intent(AlarmMainActivity.this, AlarmaActivity.class);
                startActivity(intenAlarm);
            }
        });




        AlarmaDB alarmaDB = new AlarmaDB(this);





        mAdapter = new AlarmsAdapter( AlarmMainActivity.this, alarmaDB.findAll());

        //Toast.makeText(AlarmMainActivity.this, "alarmaDB.findAll()"+ alarmaDB.findAll(), Toast.LENGTH_LONG).show();

        mListView.setAdapter(mAdapter);

        mAdapter.setMode(Attributes.Mode.Single);
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetInvalidated();
        mListView.invalidateViews();
        mListView.refreshDrawableState();













    }








}
