package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.DireccionAdapter;
import bepp.com.bepp.activities.adapters.DireccionesRecetaAdapter;
import bepp.com.bepp.models.Address;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class DireccionEnvioRecetaActivity extends AppCompatActivity {


    private List<Address> addresses;
    private DireccionesRecetaAdapter mAdapter;
    private ListView mListView;
    private Context mContext = this;
    private LinearLayout siguenteCotizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_envio_receta);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        getWindow().setLayout((int)(width * .8) ,(int) (height * .8));

        final int id_receta = getIntent().getExtras().getInt("id_receta");
        Log.i("id_receta", ": "+id_receta);




        mListView = (ListView) findViewById(R.id.list_address_receta);
        siguenteCotizar =(LinearLayout) findViewById(R.id.siguente_cotizar);



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

                    mAdapter = new DireccionesRecetaAdapter( DireccionEnvioRecetaActivity.this, addresses, id_receta);
                    mListView.setAdapter(mAdapter);



                    mAdapter.setMode(Attributes.Mode.Single);




                } else if(response.code() == 500){
                    Toast.makeText(DireccionEnvioRecetaActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(DireccionEnvioRecetaActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Log.e("Bepp",t.getMessage(),t);
            }
        });




        siguenteCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultadoIntent = new Intent(DireccionEnvioRecetaActivity.this, MedicinasRecetasActivity.class);
                startActivity(resultadoIntent);
            }
        });

    }
}

