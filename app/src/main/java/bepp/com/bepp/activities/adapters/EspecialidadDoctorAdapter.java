package bepp.com.bepp.activities.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.models.DatosLaboral;
import bepp.com.bepp.models.Especialidad;

/**
 * Created by charlie on 26/12/17.
 */

public class EspecialidadDoctorAdapter extends BaseAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<Especialidad> especialidads;


    public EspecialidadDoctorAdapter(Context mContext, List<Especialidad> especialidads){
        this.mContext = mContext;
        this.especialidads = especialidads;
        Log.d("BeppDatos", especialidads != null? especialidads.size()+ " " : "No pudo parsear" );

    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;


        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_especialidades, null);
        }

        TextView especialidadtext = (TextView) v.findViewById(R.id.especialidad);

        especialidadtext.setText(especialidads.get(position).getNombreEpecialidad());





        return v;
    }


    @Override
    public int getCount() {
        return especialidads.size();
    }

    @Override
    public Object getItem(int position) {
        return especialidads.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }





}


