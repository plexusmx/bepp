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
import bepp.com.bepp.activities.adapters.TarjetaPagoRecetasAdapter;
import bepp.com.bepp.models.PaymentCard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class SeleccionPagoRecetaActivity extends AppCompatActivity {


    private List<PaymentCard> paymentCards;
    private TarjetaPagoRecetasAdapter mAdapter;
    private ListView mListView;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_pago_receta);


        mListView = (ListView) findViewById(R.id.list_tarjetas);


        USER_CLIENT_RETROFIT.getPaymentCards().enqueue(new Callback<List<PaymentCard>>() {
            @Override
            public void onResponse(Call<List<PaymentCard>> call, Response<List<PaymentCard>> response) {

                Log.i("Bepp", "card response ::"+response.code());
                if(response.code() == 200){

                    paymentCards =  response.body();
                    for (PaymentCard address: paymentCards
                            ) {
                        Log.i("BEPP", "tarjeta-->"+address.toString());
                    }

                    mAdapter = new TarjetaPagoRecetasAdapter( SeleccionPagoRecetaActivity.this, paymentCards);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if(response.code() == 500){
                    Toast.makeText(SeleccionPagoRecetaActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(SeleccionPagoRecetaActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }




            }

            @Override
            public void onFailure(Call<List<PaymentCard>> call, Throwable t) {

            }
        });




    }
}
