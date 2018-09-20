package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.EspecialidadAdapter;
import bepp.com.bepp.models.Doctor;
import bepp.com.bepp.sessionManager.UserSessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT_PACIENT;

public class DirectorioMedicoEspecialidadActivity extends AppCompatActivity {


    private List<Doctor> doctors;
    private EspecialidadAdapter mAdapter;
    private ListView mListView;
    private Context mContext = this;
    private int idEspecialidad;


    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directorio_medico_especialidad);


        Intent intent = getIntent();
        Bundle bundEpecialidad = intent.getExtras();
        if(bundEpecialidad != null)
        {
            idEspecialidad = bundEpecialidad.getInt("idEspecialidad");
            //Toast.makeText(DirectorioMedicoEspecialidadActivity.this, "idEspecialidad :" +idEspecialidad, Toast.LENGTH_LONG).show();



        }


        mListView = (ListView) findViewById(R.id.list_doctores);

        session = new UserSessionManager(getApplicationContext());


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
                Toast.makeText(mContext, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
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

        USER_CLIENT_RETROFIT_PACIENT.getDoctorEspecialidad(idEspecialidad).enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {

                Log.i("Bepp", "card response ::"+response.code());
                if(response.code() == 200){

                    doctors =  response.body();
                    for (Doctor doctor: doctors
                            ) {
                        Log.i("BEPP", "tarjeta-->"+doctor.toString());
                    }

                    mAdapter = new EspecialidadAdapter( DirectorioMedicoEspecialidadActivity.this, doctors);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if(response.code() == 500){
                    Toast.makeText(DirectorioMedicoEspecialidadActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(DirectorioMedicoEspecialidadActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {

            }
        });




    }
}
