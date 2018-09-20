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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.DireccionAdapter;
import bepp.com.bepp.models.Address;
import bepp.com.bepp.sessionManager.UserSessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;


public class DireccionEnvioMainActivity extends AppCompatActivity {

    private Button buttonNvaDireccion;
    private List<Address> addresses;
    private DireccionAdapter mAdapter;
    private ListView mListView;
    private Context mContext = this;


    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_envio_main);


        mListView = (ListView) findViewById(R.id.list_direcciones);

        session = new UserSessionManager(getApplicationContext());



        buttonNvaDireccion = (Button) findViewById(R.id.button_nva_direccion);

        buttonNvaDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nvaDireccionIntent = new Intent(DireccionEnvioMainActivity.this, BusquedaMaps.class);
                startActivity(nvaDireccionIntent);
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

        USER_CLIENT_RETROFIT.getAddress().enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {

                Log.i("Bepp", "card response ::"+response.code());
                if(response.code() == 200){

                    addresses =  response.body();
                    for (Address address: addresses
                         ) {
                        Log.i("BEPP", "tarjeta-->"+address.toString());
                    }

                    mAdapter = new DireccionAdapter( DireccionEnvioMainActivity.this, addresses);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if(response.code() == 500){
                    Toast.makeText(DireccionEnvioMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(DireccionEnvioMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Log.e("Bepp",t.getMessage(),t);
            }
        });

    }




}





    /*private void obtenerDireciones(){
        USER_CLIENT_RETROFIT.getAddress().enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {

                Log.i("Bepp", "card response ::"+response.code());
                if(response.code() == 200){

                    addresses =  response.body();
                    actualizarListaDirecciones();

                } else if(response.code() == 500){
                    Toast.makeText(DireccionEnvioMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(DireccionEnvioMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Log.e("Bepp",t.getMessage(),t);
            }
        });

    }

    private void actualizarListaDirecciones(){

        DireccionAdapter adapter = new DireccionAdapter(this, addresses);
        ListView listView = (ListView) findViewById(R.id.list_direcciones);
        listView.setAdapter(adapter);

    }*/


