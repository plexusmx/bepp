package bepp.com.bepp.alarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

import bepp.com.bepp.database.AlarmaDB;
import bepp.com.bepp.modelDB.Alarma;

/**
 * Created by charlie on 13/12/17.
 */

public class AlarmService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String TAG = "AlarmService";

    public static final String POPULATE = "POPULATE";
    public static final String CREATE = "CREATE";
    public static final String CANCEL = "CANCEL";
    private AlarmaDB admin;
    private SQLiteDatabase bd;
    private String nombreMedicamento, cantidad;


    private IntentFilter matcher;

    public AlarmService() {
        super(TAG);
        matcher = new IntentFilter();
        matcher.addAction(POPULATE);
        matcher.addAction(CREATE);
        matcher.addAction(CANCEL);
    }

    public AlarmService(String name) {
        super(name);
    }

    @Override
    public void onHandleIntent(@Nullable Intent intent) {
        Log.d("BeeppBack", "Alarma Service");



    }

    /**
     * @param action
     * @param args   {alarmId, alarmMsgId, startTime, endTime}
     */
    private void execute(String action, String... args) {


    }
}