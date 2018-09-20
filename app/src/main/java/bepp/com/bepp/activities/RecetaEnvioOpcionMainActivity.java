package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.DireccionesRecetaAdapter;
import bepp.com.bepp.models.Address;
import bepp.com.bepp.models.PackageSend;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT_PACIENT;

public class RecetaEnvioOpcionMainActivity extends AppCompatActivity {


    private LinearLayout siguenteCotizar;
    private Button btnMenos, btnMas,btnMenos2, btnMas2, btnSservicioDomicilio, btnRecogerFermacia;
    private TextView numCantidad, numCantidad2;
    private List<Address> addresses;
    private DireccionesRecetaAdapter mAdapter;
    private ListView mListView;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_opcion_envio_main);


        buidUI();


        final int id_receta = getIntent().getExtras().getInt("id_receta");
        Log.i("Card", "recetaid " +id_receta);


        btnSservicioDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageSend packageSend = new PackageSend(String.valueOf(id_receta), "1");
                Log.i("id_receta", "2 : "+id_receta);

                USER_CLIENT_RETROFIT_PACIENT.formaEnvio(packageSend).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if(response.code() == 201){
                            Intent direccionaIntent = new Intent(RecetaEnvioOpcionMainActivity.this , DireccionEnvioRecetaActivity.class);
                            direccionaIntent.putExtra("id_receta", id_receta);
                            startActivity(direccionaIntent);

                        } else if(response.code() == 500){
                            Toast.makeText(RecetaEnvioOpcionMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                        }else if(response.code() == 403){
                            Toast.makeText(RecetaEnvioOpcionMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                        }


                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });




            }
        });

        btnRecogerFermacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageSend packageSend = new PackageSend(String.valueOf(id_receta), "2");
                Log.i("id_receta", "2: "+id_receta);


                USER_CLIENT_RETROFIT_PACIENT.formaEnvio(packageSend).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if(response.code() == 201){
                            Intent direccionaIntent = new Intent(RecetaEnvioOpcionMainActivity.this , RecogerFarmaciaMapaActivity.class);
                            direccionaIntent.putExtra("id_receta", id_receta);
                            startActivity(direccionaIntent);

                        } else if(response.code() == 500){
                            Toast.makeText(RecetaEnvioOpcionMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                        }else if(response.code() == 403){
                            Toast.makeText(RecetaEnvioOpcionMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                        }



                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });


            }
        });








    }


    private void buidUI(){


        btnSservicioDomicilio = (Button) findViewById(R.id.btn_servicio_domicilio);
        btnRecogerFermacia = (Button) findViewById(R.id.btn_recoger_farmacia);


    }
}
