package bepp.com.bepp.activities.adapters;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.Date;
import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.AlarmMainActivity;
import bepp.com.bepp.database.AlarmaDB;
import bepp.com.bepp.modelDB.Alarma;
import bepp.com.bepp.models.Alarm;

/**
 * Created by charlie on 28/11/17.
 */

public class AlarmsAdapter  extends BaseSwipeAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<Alarma> alarms;

   public AlarmsAdapter (Context mContext, List<Alarma> alarms) {
        this.mContext = mContext;
        this.alarms = alarms;
      ;

   }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_alarms;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.alarm_list_, null);
        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmaDB alarmaDB = new AlarmaDB(mContext);

                 //TODO

                Alarma alarma = alarms.get(position); //alarmaDB.findById(position);

                Log.d("Bepp", "Borrar *****");



                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

                Log.d("Bepp", "RequestCode " + alarma.getRequestCode());

                // isNotificationActive(alarma.getRequestCode());

                alarmaDB.actualizarFechaVencimiento(alarma.getRequestCode(), new Date().getTime());



                notificationManager.cancel(alarma.getRequestCode());

                alarmaDB.delete(alarms.get(position).getIdAlarma());

                swipeLayout.close(true);
                Intent intent = new Intent(mContext, AlarmMainActivity.class);
                mContext.startActivity(intent);

            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {

        TextView txtCantidad = (TextView) convertView.findViewById(R.id.txt_cantidad);
        TextView txtNombreMedicamento = (TextView) convertView.findViewById(R.id.txt_nombre_medicamento);


        txtCantidad.setText(alarms.get(position).getCantidad());
        txtNombreMedicamento.setText((alarms.get(position).getNombreMedicamento()));


    }

    @Override
    public int getCount() {

        return alarms != null ? alarms.size() : 0;

    }


    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alarms.get(position).getIdAlarma();
    }



}
