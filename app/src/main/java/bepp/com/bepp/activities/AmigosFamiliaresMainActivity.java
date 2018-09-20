package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.FamiliresAdapter;
import bepp.com.bepp.activities.adapters.FiscalDataAdapter;
import bepp.com.bepp.models.Familiar;
import bepp.com.bepp.models.FiscalData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class AmigosFamiliaresMainActivity extends AppCompatActivity {

    private Button btnnNvaFamiliar;
    private List<Familiar> familiars;
    private ListView mListView;
    private FamiliresAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos_familiares_main);




        mListView = (ListView) findViewById(R.id.list_familiares);

        btnnNvaFamiliar = (Button) findViewById(R.id.button_familiar);



        USER_CLIENT_RETROFIT.getFamily().enqueue(new Callback<List<Familiar>>() {
            @Override
            public void onResponse(Call<List<Familiar>> call, Response<List<Familiar>> response) {

                if(response.code() == 200){

                    familiars =  response.body();

                    //actualizarListaTarjetas();

                    mAdapter = new FamiliresAdapter(AmigosFamiliaresMainActivity.this, familiars, mListView);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if(response.code() == 500){
                    Toast.makeText(AmigosFamiliaresMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(AmigosFamiliaresMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<Familiar>> call, Throwable t) {
                Log.e("Bepp",t.getMessage(),t);

            }
        });

        btnnNvaFamiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFamiliar = new Intent(AmigosFamiliaresMainActivity.this, AmigosFamiliaresActivity.class);
                startActivity(intentFamiliar);

            }
        });

    }
}
