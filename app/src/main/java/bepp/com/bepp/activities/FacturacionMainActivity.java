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
import bepp.com.bepp.activities.adapters.FiscalDataAdapter;
import bepp.com.bepp.models.FiscalData;
import bepp.com.bepp.sessionManager.UserSessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;


public class FacturacionMainActivity extends AppCompatActivity {

    private Button btnnNvaFacturacion;
    private ListView mListView;
    private Context mContext = this;
    UserSessionManager session;
    private FiscalDataAdapter mAdapter;
    private List<FiscalData> fiscalDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturacion_main);

        mListView = (ListView) findViewById(R.id.list_fiscal_data);

        session = new UserSessionManager(getApplicationContext());




        btnnNvaFacturacion = (Button) findViewById(R.id.button_nva_facturacion);


        btnnNvaFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nvaFacturacionIntent = new Intent(FacturacionMainActivity.this, FacturacionActivity.class);
                startActivity(nvaFacturacionIntent);
            }
        });





        USER_CLIENT_RETROFIT.getFiscalData().enqueue(new Callback<List<FiscalData>>() {
            @Override
            public void onResponse(Call<List<FiscalData>> call, Response<List<FiscalData>> response) {

                if(response.code() == 200){

                    fiscalDatas =  response.body();
                    for (FiscalData card: fiscalDatas
                            ) {
                        Log.i("BEPP", "tarjeta-->"+card.toString());
                    }
                    //actualizarListaTarjetas();

                    mAdapter = new FiscalDataAdapter(FacturacionMainActivity.this, fiscalDatas, mListView);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if(response.code() == 500){
                    Toast.makeText(FacturacionMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(FacturacionMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<FiscalData>> call, Throwable t) {
                Log.e("Bepp",t.getMessage(),t);

            }
        });
    }



}
