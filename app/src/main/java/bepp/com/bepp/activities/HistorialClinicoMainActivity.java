package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.models.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class HistorialClinicoMainActivity extends AppCompatActivity {

    public TextView textEditHistorialClinico;
    public TextView editFechaNacimiento, editEstatura, editPeso, editSangre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_clinico_main);



        editFechaNacimiento = (TextView) findViewById(R.id.fecha_nacimiento);
        editEstatura = (TextView) findViewById(R.id.estatura);
        editPeso = (TextView) findViewById(R.id.peso);
        editSangre = (TextView) findViewById(R.id.sangre);

        textEditHistorialClinico = (TextView) findViewById(R.id.text_edit_historial_clinico);


        textEditHistorialClinico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditHistorilCli = new Intent(HistorialClinicoMainActivity.this, HistorialClinicoActivity.class);
                startActivity(intentEditHistorilCli);
            }
        });

        getClinicalUser();


    }




    public  void getClinicalUser() {
        USER_CLIENT_RETROFIT.obtenerFichaMedica().enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {

                if (response.code() == 200) {
                    Toast.makeText(HistorialClinicoMainActivity.this, "Entro"+response.body() , Toast.LENGTH_LONG).show();



                    editFechaNacimiento.setText(response.body().get(0).getFecha_nacimiento());
                    editEstatura.setText(response.body().get(0).getEstatura());
                    editPeso.setText(response.body().get(0).getPeso());
                    editSangre.setText(response.body().get(0).getTipo_sangre());


                } else if (response.code() == 403) {
                    Toast.makeText(HistorialClinicoMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                } else if (response.code() == 404) {
                    Toast.makeText(HistorialClinicoMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                } else if (response.code() == 500) {
                    Toast.makeText(HistorialClinicoMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {

            }
        });
    }

    }
