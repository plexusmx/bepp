package bepp.com.bepp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import bepp.com.bepp.R;
import bepp.com.bepp.models.MedicalSpeciality;
import bepp.com.bepp.models.Patient;
import bepp.com.bepp.models.Relationship;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;


public class HistorialClinicoActivity extends AppCompatActivity {

    private ImageView cancelarImagen, saveClinic;
    private MaterialSpinner spinnerSangre;
    private Boolean flag = false;
    private String tipiSangre;
    Calendar calendario = Calendar.getInstance();
    private EditText pecienteFecha, pacienteEstatura, pacientePeso, pacienteSangre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_clinico);


        Toolbar principal = (Toolbar) findViewById(R.id.toolbar_edit);
        principal.setTitle("");
        setSupportActionBar(principal);


        pecienteFecha = (EditText) findViewById(R.id.text_paciente_fecha);
         pacienteEstatura = (EditText) findViewById(R.id.text_paciente_estatura);
         pacientePeso = (EditText) findViewById(R.id.text_paciente_peso);
         spinnerSangre = (MaterialSpinner) findViewById(R.id.spinner_tipos_sangre);



 saveClinic = (ImageView) findViewById(R.id.save_clinic);
cancelarImagen = (ImageView) findViewById(R.id.imagecancelarDireccion);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, monthOfYear);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                actualizarInput();
            }

        };


        pecienteFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(HistorialClinicoActivity.this, date, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        cancelarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditHistorilCli = new Intent(HistorialClinicoActivity.this, HistorialClinicoMainActivity.class);
                startActivity(intentEditHistorilCli);
            }
        });

        saveClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        getClinicalUser();

        findViewById(R.id.layoutgeneral).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });


        spinnerSangre.setItems( "AB+","A+","B+","A-","B+", "O+", "O-");
spinnerSangre.setText("Selecciona el tipo de sangre");


        spinnerSangre.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                if(item.toString().equals("Selecciona el tipo de sangre")){
                    flag = false;
                }else{

                    tipiSangre = item.toString().trim();
                    flag = true;

                }
            }
        });



        spinnerSangre.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
              //  Snackbar.make(spinner, "Selecciona el tipo de sangre", Snackbar.LENGTH_LONG).show();
            }
        });


    }


    public  void postHistorialClinico(Patient clinico){
        USER_CLIENT_RETROFIT.fichaMedica(clinico).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.code() == 201){
                    Toast.makeText(HistorialClinicoActivity.this, "Datos exitosamente creados", Toast.LENGTH_LONG).show();
                    Intent backDirectoryIntent = new Intent(HistorialClinicoActivity.this, HistorialClinicoMainActivity.class);
                    startActivity(backDirectoryIntent);

                } else if(response.code() == 500){
                    Toast.makeText(HistorialClinicoActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){
                    Toast.makeText(HistorialClinicoActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }else if(response.code() == 404){
                    Toast.makeText(HistorialClinicoActivity.this, "datos ine", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    private void submitForm() {



        String pecienteFechaS = pecienteFecha.getText().toString().trim();
        String pacienteEstaturaS = pacienteEstatura.getText().toString().trim();
        String pacientePesoS = pacientePeso.getText().toString().trim();
        String pacienteSangreS = tipiSangre;


        if(flag) {
            Patient patient = new Patient(pecienteFechaS, "", pacienteEstaturaS, pacientePesoS, pacienteSangreS);
            postHistorialClinico(patient);
        }

    }



    public  void getClinicalUser() {
        USER_CLIENT_RETROFIT.obtenerFichaMedica().enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {

                if (response.code() == 200) {


                    pecienteFecha.setText(response.body().get(0).getFecha_nacimiento());
                    pacienteEstatura.setText(response.body().get(0).getEstatura());
                    pacientePeso.setText(response.body().get(0).getPeso());


                } else if (response.code() == 403) {
                    Toast.makeText(HistorialClinicoActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                } else if (response.code() == 404) {
                    Toast.makeText(HistorialClinicoActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                } else if (response.code() == 500) {
                    Toast.makeText(HistorialClinicoActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {

            }
        });




    }


    private void actualizarInput() {
        String formatoDeFecha = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);

        pecienteFecha.setText(sdf.format(calendario.getTime()));
    }





}
