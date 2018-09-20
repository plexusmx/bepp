package bepp.com.bepp.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.CardAdapter;
import bepp.com.bepp.activities.adapters.DoctorHospitalesAdapter;
import bepp.com.bepp.activities.adapters.EspecialidadAdapter;
import bepp.com.bepp.activities.adapters.EspecialidadDoctorAdapter;
import bepp.com.bepp.models.DatosLaboral;
import bepp.com.bepp.models.Doctor;
import bepp.com.bepp.models.Especialidad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class MisDoctoresActivity extends AppCompatActivity {

    private ImageView imageRegresar;
    private Doctor doctor;
    private TextView txtNombreDoctor, telefonoDoctor, correoDoctor;
    private ImageView imageDesvincunlar, callDoctor;
    private List<DatosLaboral> datosLaborals;
    private List<Especialidad> especialidads;
    private List<Doctor> doctors;
    private ListView mListView, bListView;
    private DoctorHospitalesAdapter mAdapter;
    private EspecialidadDoctorAdapter bAdapter;
    public AlertDialog.Builder registerMesaje;
    private String celular;
    private static final int REQUEST_PHONE_CALL = 1;
    private Context mContext = this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_doctores);


        //Toolbar principal = (Toolbar) findViewById(R.id.toolbar_edit_doc);
        //principal.setTitle("");
        //setSupportActionBar(principal);
        registerMesaje = new AlertDialog.Builder(MisDoctoresActivity.this);


        // imageRegresar = (ImageView) findViewById(R.id.image_regresar);
        txtNombreDoctor = (TextView) findViewById(R.id.txt_nombre_doctor);

        mListView = (ListView) findViewById(R.id.list_datos_laborales);
        bListView = (ListView) findViewById(R.id.list_especialidades);

        imageDesvincunlar = (ImageView) findViewById(R.id.image_desvincunlar);
        callDoctor = (ImageView) findViewById(R.id.call_doctor);

        telefonoDoctor = (TextView) findViewById(R.id.telefono_doctor);
        correoDoctor = (TextView) findViewById(R.id.correo_doctor);








        /*imageRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDoctor = new Intent(MisDoctoresActivity.this, MisDoctoresMainActivity.class);
                startActivity(intentDoctor);


            }
        });*/

        final Integer idDoctor = getIntent().getExtras().getInt("id_dato");

        USER_CLIENT_RETROFIT.getDoctor(idDoctor).enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {


                if (response.code() == 200) {


                    doctors = response.body();

                    datosLaborals = doctors.get(0).getDatosLaborales();
                    especialidads = doctors.get(0).getEspecialidades();


                    txtNombreDoctor.setText(response.body().get(0).getNombre());


                    celular = doctors.get(0).getTelefono();

                    telefonoDoctor.setText(doctors.get(0).getTelefono());

                    telefonoDoctor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+telefonoDoctor.getText().toString()));
                            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            }
                            else
                            {
                                mContext.startActivity(callIntent);
                            }


                        }
                    });


                    correoDoctor.setText(doctors.get(0).getUsuario());

                    correoDoctor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {correoDoctor.getText().toString()});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Correo de Bepp");

                            try {
                                startActivity(Intent.createChooser(intent, "Mail Bepp"));
                            } catch (android.content.ActivityNotFoundException ex) {
                                //do something else
                            }


                        }
                    });


                    Log.d("Bepp", doctors.get(0).getDatosLaborales() != null ? doctors.get(0).getDatosLaborales().size() + " " : "No pudo parsear");


                    bAdapter = new EspecialidadDoctorAdapter(MisDoctoresActivity.this, especialidads);
                    bListView.setAdapter(bAdapter);

                    mAdapter = new DoctorHospitalesAdapter(MisDoctoresActivity.this, datosLaborals);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);


                } else if (response.code() == 500) {
                    Toast.makeText(MisDoctoresActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                } else if (response.code() == 403) {


                    Toast.makeText(MisDoctoresActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Log.e("Bepp", t.getMessage(), t);
            }
        });


        imageDesvincunlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registerMesaje.setTitle("Quitar Médico ");
                registerMesaje.setMessage("¿Deseas quitarlo de tu lista de Médicos?");
                registerMesaje.setIcon(R.mipmap.logo_bepp_blank);
                registerMesaje.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                USER_CLIENT_RETROFIT.desvincularDoctor(idDoctor).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        if (response.code() == 200) {

                                            //Toast.makeText(MisDoctoresActivity.this, "Se elimino al doctor", Toast.LENGTH_LONG).show();
                                            Intent mainIntetnDoctor = new Intent(MisDoctoresActivity.this, MisDoctoresMainActivity.class);
                                            startActivity(mainIntetnDoctor);


                                        } else if (response.code() == 500) {
                                            Toast.makeText(MisDoctoresActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                                        } else if (response.code() == 403) {


                                            Toast.makeText(MisDoctoresActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                        Log.e("Bepp", t.getMessage(), t);


                                    }
                                });


                            }
                        }
                );
                registerMesaje.show();

//Click
            }
        });


        callDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:5539291202"));
                if (ContextCompat.checkSelfPermission(MisDoctoresActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MisDoctoresActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    startActivity(callIntent);
                }














            }
        });

    }
}


