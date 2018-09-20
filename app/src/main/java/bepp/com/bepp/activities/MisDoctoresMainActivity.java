package bepp.com.bepp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.DireccionAdapter;
import bepp.com.bepp.activities.adapters.FiscalDataAdapter;
import bepp.com.bepp.activities.adapters.MisMedicosAdapter;
import bepp.com.bepp.models.Address;
import bepp.com.bepp.models.Doctor;
import bepp.com.bepp.models.ResponseList;
import bepp.com.bepp.sessionManager.UserSessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class MisDoctoresMainActivity extends AppCompatActivity {


    private LinearLayout layoutDoctor1;
    private Button buttonNvaDireccion;
    private List<Doctor> doctors;
    private MisMedicosAdapter mAdapter;
    private ListView mListView;
    private SearchView searchView;
    private Context mContext = this;
    private static final int REQUEST_PHONE_CALL = 1;

    UserSessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mis_doctores);

        mListView = (ListView) findViewById(R.id.list_mis_medicos);
        session = new UserSessionManager(getApplicationContext());

        //searchView = (SearchView)findViewById(R.id.searchView);





        if (ContextCompat.checkSelfPermission(MisDoctoresMainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MisDoctoresMainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }








        USER_CLIENT_RETROFIT.getDoctors().enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, final Response<List<Doctor>> response) {

                Log.i("Bepp", "card response ::"+response.code());
                if(response.code() == 200){

                    doctors =  response.body();
                    for (Doctor doctor: doctors
                            ) {

                        Log.i("BEPP", "tarjeta-->"+doctor.toString());
                    }

                    mAdapter = new MisMedicosAdapter(MisDoctoresMainActivity.this, doctors, mListView);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);



                   /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {

                            mAdapter.getFilter().filter(s);
                            return false;
                        }
                    });
*/



                } else if(response.code() == 500){
                    Toast.makeText(MisDoctoresMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(MisDoctoresMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {

                Log.e("BEPP", "doctor-->"+t);

            }
        });




    }




}
