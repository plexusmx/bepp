package bepp.com.bepp.activities.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.models.DatosLaboral;
import bepp.com.bepp.models.Doctor;

/**
 * Created by charlie on 18/12/17.
 */

public class DoctorHospitalesAdapter extends BaseSwipeAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<DatosLaboral> datosLaborals;


    public DoctorHospitalesAdapter(Context mContext, List<DatosLaboral> datosLaborals){
        this.mContext = mContext;
        this.datosLaborals = datosLaborals;
        Log.d("BeppDatos", datosLaborals != null? datosLaborals.size()+ " " : "No pudo parsear" );

    }



    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_dato_laborales;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {


        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_doctor_hospitales, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });



        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {

        TextView nombreHospital = (TextView) convertView.findViewById(R.id.nombre_hospital);
        TextView direccionHospital = (TextView) convertView.findViewById(R.id.txt_direccion_hospital);
        TextView horarioHopital = (TextView) convertView.findViewById(R.id.txt_horario_hospita);


        nombreHospital.setText(datosLaborals.get(position).getHopital());
        direccionHospital.setText(datosLaborals.get(position).getDomicilio());
        horarioHopital.setText(datosLaborals.get(position).getHorario());
        Log.d("BeppDatos", datosLaborals.get(position).getHopital() );




    }

    @Override
    public int getCount() {
        return datosLaborals.size();
    }

    @Override
    public Object getItem(int position) {
        return datosLaborals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}


