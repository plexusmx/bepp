package bepp.com.bepp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.DoctorHospitalesAdapter;
import bepp.com.bepp.activities.adapters.EspecialidadDoctorAdapter;
import bepp.com.bepp.models.Doctor;
import bepp.com.bepp.models.Familiar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class AmigoFamiliaActivity extends AppCompatActivity {

    private TextView txtNombreFamiliar, telefonoFamiliar, correoFamiliar, txtBirthday;
    private ImageView imageDesvincunlar;
    public AlertDialog.Builder registerMesaje;
    private Familiar familiar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo_familia);


        registerMesaje = new AlertDialog.Builder(AmigoFamiliaActivity.this);

        // imageRegresar = (ImageView) findViewById(R.id.image_regresar);
        txtNombreFamiliar = (TextView) findViewById(R.id.txt_nombre_familiar);
        txtBirthday = (TextView) findViewById(R.id.text_birthday);
        correoFamiliar = (TextView) findViewById(R.id.correo_familiar);
        imageDesvincunlar = (ImageView) findViewById(R.id.image_desvincunlar);
        telefonoFamiliar = (TextView) findViewById(R.id.telefono_familiar);


        final int idFamiliar = getIntent().getExtras().getInt("id_familiar");


        USER_CLIENT_RETROFIT.getFamiliar(idFamiliar).enqueue(new Callback<Familiar>() {
            @Override
            public void onResponse(Call<Familiar> call, Response<Familiar> response) {

                familiar = response.body();
                txtNombreFamiliar.setText(response.body().getNombre());
                txtBirthday.setText(response.body().getFecha_nacimiento());
                correoFamiliar.setText(response.body().getCorreo());
                telefonoFamiliar.setText(response.body().getTel_movil());


            }

            @Override
            public void onFailure(Call<Familiar> call, Throwable t) {

            }
        });

        imageDesvincunlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registerMesaje.setTitle("Quitar Médico ");
                registerMesaje.setMessage("¿Deseas quitarlo de tu lista de Amigos y Familiares?");
                registerMesaje.setIcon(R.mipmap.logo_bepp_blank);
                registerMesaje.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Familiar familiarId = new Familiar(idFamiliar);
                        USER_CLIENT_RETROFIT.desvincularFamiliar(familiarId).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                if (response.code() == 201) {

                                    //Toast.makeText(MisDoctoresActivity.this, "Se elimino al doctor", Toast.LENGTH_LONG).show();
                                    Intent mainIntetnDoctor = new Intent(AmigoFamiliaActivity.this, AmigosFamiliaresMainActivity.class);
                                    startActivity(mainIntetnDoctor);


                                } else if (response.code() == 500) {
                                    Toast.makeText(AmigoFamiliaActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                                } else if (response.code() == 403) {


                                    Toast.makeText(AmigoFamiliaActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });


                    }
                });


                registerMesaje.show();

            }
        });

    }

}