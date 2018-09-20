package bepp.com.bepp.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.DetalleRecetaActivity;
import bepp.com.bepp.models.Receta;

public class RecetasAdapter extends BaseSwipeAdapter {


    private Context mContext;
    List<Receta> recetas;
    private ListView mListView;


    public RecetasAdapter(Context mContext, List<Receta> recetas ,ListView mListView){
        this.mContext = mContext;
        this.recetas = recetas;
        this.mListView = mListView;

    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_recetas;
    }


    @Override
    public View generateView(final int position, ViewGroup parent) {


        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_recetas, null);




        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");



                return false;
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentEditFact = new Intent(mContext, DetalleRecetaActivity.class);

                intentEditFact.putExtra("id_receta", recetas.get(position).getId_receta());
                mContext.startActivity(intentEditFact);


            }
        });


        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                return false;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });



        return v;
    }

    @Override
    public void fillValues(int position, final View convertView) {

        final Receta receta = recetas.get(position);

        TextView textNombreDoctor = (TextView) convertView.findViewById(R.id.text_nombre_doctor);
        TextView txtIdReceta = (TextView) convertView.findViewById(R.id.txt_id_receta);
        TextView txtFechaExp = (TextView) convertView.findViewById(R.id.text_fecha_expedicion);
        TextView txtFechaVen = (TextView) convertView.findViewById(R.id.text_fecha_vencimiento);

        // Populate the data into the template view using the data object
        textNombreDoctor.setText(receta.getMedico());
        txtIdReceta.setText(String.valueOf(receta.getId_receta()));
        txtFechaExp.setText(receta.getFecha_registro());
        txtFechaVen.setText(receta.getFecha_vencimiento());


    }

    @Override
    public int getCount() {
        return recetas.size();
    }

    @Override
    public Object getItem(int position) {
        return recetas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recetas.get(position).getId_receta();
    }
}



