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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.RecetasAdapter;
import bepp.com.bepp.menus.HomeActivityMenu;
import bepp.com.bepp.models.Receta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT_PACIENT;

public class RecetaMainActivity extends AppCompatActivity {


    public LinearLayout listRecetaHard;
    private ListView mListView;
    private Context mContext = this;
    private List<Receta> recetas;
    private RecetasAdapter mAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_main);

        mListView = (ListView) findViewById(R.id.list_recetas);

        listRecetaHard = (LinearLayout) findViewById(R.id.list_receta_hard);






        USER_CLIENT_RETROFIT_PACIENT.listarRecetas().enqueue(new Callback<List<Receta>>() {
            @Override
            public void onResponse(Call<List<Receta>> call, Response<List<Receta>> response) {

                if(response.code() == 200){

                    recetas =  response.body();


                    mAdapter = new RecetasAdapter(RecetaMainActivity.this, recetas, mListView);
                    mListView.setAdapter(mAdapter);

                } else if(response.code() == 500){
                    Toast.makeText(RecetaMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(RecetaMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }






            }

            @Override
            public void onFailure(Call<List<Receta>> call, Throwable t) {





            }
        });


       /* listRecetaHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent recetasIntent = new Intent(RecetaMainActivity.this, DetalleRecetaActivity.class);
                startActivity(recetasIntent);
            }
        });
*/


    }
}
