package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.HashMap;
import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.CustomSpinnerAdapter;
import bepp.com.bepp.models.MedicalSpeciality;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static bepp.com.bepp.services.CatalogService.CATALOG_CLIENT_RETROFIT;
public class DirectorioMedicoActivity extends AppCompatActivity {


    private MaterialSpinner spinnerDirectorio;
    // User Session Manager Class
    UserSessionManager session;

    private List<MedicalSpeciality> especialidadesMed;
    private int idEspecialidad;
    private String nombreDoctor;
    private Button btnbusqueda, btnbusquedaMedico;
    private boolean listaText = false;
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directorio_medico);




        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin())
            finish();


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        HttpClient.setToken(user.get(UserSessionManager.TOKEN_KEY));

        sendGet();


        btnbusqueda = (Button) findViewById(R.id.button_nva_busqueda);
        btnbusquedaMedico = (Button) findViewById(R.id.button_nva_busqueda_nombre);
        searchView = (EditText) findViewById(R.id.searchView);



        btnbusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listaText ) {
                    Intent intentBusqueda = new Intent(DirectorioMedicoActivity.this, DirectorioMedicoEspecialidadActivity.class);

                    intentBusqueda.putExtra("idEspecialidad", idEspecialidad);
                    startActivity(intentBusqueda);
                }
            }
        });


        btnbusquedaMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreDoctor = searchView.getText().toString().trim();
                Log.e("NOMBRE", "nombreDoctor :"+nombreDoctor);

                if(!nombreDoctor.isEmpty()){
                    Log.e("NOMBRE", "nombreDoctor :"+nombreDoctor);

                    Intent intentBusqueda = new Intent(DirectorioMedicoActivity.this, ResultadosMedicoActivity.class);
                intentBusqueda.putExtra("nombreDoctor", nombreDoctor);
                startActivity(intentBusqueda);
                }
            }
        });

    }



    public void  sendGet() {
        CATALOG_CLIENT_RETROFIT.getMedicalSpeciality().enqueue(new Callback<List<MedicalSpeciality>>() {
            @Override
            public void onResponse(Call<List<MedicalSpeciality>> call, Response<List<MedicalSpeciality>> response) {

             //    Toast.makeText(DirectorioMedicoActivity.this, "  se agrego " + response.code(), Toast.LENGTH_LONG).show();

                if(response.code() == 200){

                    especialidadesMed = response.body();
                    cargarLista();
                } else if(response.code() == 500){
                    Toast.makeText(DirectorioMedicoActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 404){
                    Toast.makeText(DirectorioMedicoActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<List<MedicalSpeciality>> call, Throwable t) {

            }
        });

    }


    public void cargarLista( ){

        String[] medSpeciality = new String[especialidadesMed.size()+1];
        int i = 1;
        medSpeciality[0] = "Selecciona la Especialidad Médica";

        for (MedicalSpeciality especiality:especialidadesMed) {
            medSpeciality[i++]= especiality.getNombre();
        }

        spinnerDirectorio = (MaterialSpinner) findViewById(R.id.spinner_especialidad);
        spinnerDirectorio.getHint();
        spinnerDirectorio.setItems(medSpeciality);



        spinnerDirectorio.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if(!item.toString().equals("Selecciona la Especialidad Médica*")){

                    for (int i =0 ;i < especialidadesMed.size(); i++ ){
                        if(especialidadesMed.get(i).getNombre().equals(item.toString())){
                            idEspecialidad = Integer.parseInt(especialidadesMed.get(i).getId_especialidad());
                            listaText = true;
                        } else if (item.toString().equals("Selecciona la Especialidad Médica")){

                            listaText = false;
                        }
                    }

                }
            }
        });

        spinnerDirectorio.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Seleccionar la Especialidad Médica", Snackbar.LENGTH_LONG).show();
            }
        });


    }


}