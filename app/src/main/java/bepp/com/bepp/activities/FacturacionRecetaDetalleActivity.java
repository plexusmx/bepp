package bepp.com.bepp.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.FacturacionRecetaAdapter;
import bepp.com.bepp.activities.adapters.FiscalDataAdapter;
import bepp.com.bepp.models.FiscalData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class FacturacionRecetaDetalleActivity extends AppCompatActivity {

    private List<FiscalData> fiscalDatas;
    private FacturacionRecetaAdapter mAdapter;
    private ListView mListView;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturacion_receta_detalle);

        mListView = (ListView) findViewById(R.id.list_fiscal_data);



        USER_CLIENT_RETROFIT.getFiscalData().enqueue(new Callback<List<FiscalData>>() {
            @Override
            public void onResponse(Call<List<FiscalData>> call, Response<List<FiscalData>> response) {

                Log.i("Bepp", "card response ::"+response.code());
                if(response.code() == 200){

                    fiscalDatas =  response.body();
                    for (FiscalData address: fiscalDatas
                            ) {
                        Log.i("BEPP", "tarjeta-->"+address.toString());
                    }

                    mAdapter = new FacturacionRecetaAdapter( FacturacionRecetaDetalleActivity.this, fiscalDatas);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if(response.code() == 500){
                    Toast.makeText(FacturacionRecetaDetalleActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(FacturacionRecetaDetalleActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }




            }

            @Override
            public void onFailure(Call<List<FiscalData>> call, Throwable t) {

            }
        });





    }
}
