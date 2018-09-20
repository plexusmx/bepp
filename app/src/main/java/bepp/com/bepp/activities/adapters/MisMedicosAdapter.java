package bepp.com.bepp.activities.adapters;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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
import bepp.com.bepp.activities.EditFacturacionActivity;
import bepp.com.bepp.activities.MisDoctoresActivity;
import bepp.com.bepp.activities.MisDoctoresMainActivity;
import bepp.com.bepp.models.Address;
import bepp.com.bepp.models.Doctor;
import bepp.com.bepp.models.FiscalData;
import bepp.com.bepp.models.ResponseList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

/**
 * Created by charlie on 13/11/17.
 */

public class MisMedicosAdapter extends BaseSwipeAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<Doctor> doctors;
    private ListView mListView;
    private static final int REQUEST_PHONE_CALL = 1;



    public MisMedicosAdapter(Context mContext, List<Doctor> doctors, ListView mListView) {
        this.mContext = mContext;
        this.doctors = doctors;
        this.mListView = mListView;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_doctores;

    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        registerMesaje = new AlertDialog.Builder(mContext);

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_mismedicos, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));


        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentEditFact = new Intent(mContext, MisDoctoresActivity.class);

                intentEditFact.putExtra("id_dato", doctors.get(position).getId_usuario());
                mContext.startActivity(intentEditFact);


            }
        });

        v.findViewById(R.id.cel_doc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("bepp", "Click "  );





                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+doctors.get(position).getTelefono().toString()));
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                }
                else
                {
                    mContext.startActivity(callIntent);
                }






            }
        });






        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentEditFact = new Intent(mContext, MisDoctoresActivity.class);

                intentEditFact.putExtra("id_dato", doctors.get(position).getId_usuario());
                mContext.startActivity(intentEditFact);


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



        return v;


    }

    @Override
    public void fillValues(int position, View convertView) {


        Doctor doctor = doctors.get(position);
        String especialidades = "" ;



        TextView textNombreDoctor = (TextView) convertView.findViewById(R.id.nombre_doctor);
        TextView txtespecialidad = (TextView) convertView.findViewById(R.id.txt_especialidad);





        // Populate the data into the template view using the data object
        textNombreDoctor.setText(doctor.getNombre());


        for (int i = 0 ; i < doctor.getEspecialidades().size(); i ++){
            especialidades = especialidades + " - "+ doctor.getEspecialidades().get(i).getNombreEpecialidad()  ;

        }
        txtespecialidad.setText(especialidades);

    }

    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Object getItem(int position) {
        return doctors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return doctors.get(position).getId_usuario();
    }




}

