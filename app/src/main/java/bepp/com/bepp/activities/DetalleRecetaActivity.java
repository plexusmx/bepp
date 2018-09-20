package bepp.com.bepp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT_PACIENT;


import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.DetalleMedicamentosAdapter;
import bepp.com.bepp.models.Receta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleRecetaActivity extends AppCompatActivity {


    private Button mMoveTopButton;
    private boolean active = false;
    public AlertDialog.Builder registerMesaje;
    private ExpandableLayout expandableLayout0, expandableLayoutEstudios;
    public LinearLayout linearLayoutDiagnostico, linearLayoutMedicamentos, linearLayoutEstudios;
    private Button btnCotizar;
    private DetalleMedicamentosAdapter mAdapter;
    private ListView mListView;

    private List<Receta> recetas;
    private String textDiagnostico  = "";
    private TextView textIdReceta , textFechaExp , textFehcaVen , textNombrePaciente , textNombreDoctor , textMedicamentos ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta);


        bindUI();
        final int id_receta = getIntent().getExtras().getInt("id_receta");
        Log.i("Card", "recetaid " +id_receta);

        textIdReceta = (TextView) findViewById(R.id.text_id_dereceta);
        textFechaExp = (TextView) findViewById(R.id.text_fecha_deexp);
        textFehcaVen = (TextView) findViewById(R.id.text_fecha_deven);
        textNombrePaciente = (TextView) findViewById(R.id.text_nombre_paciente);
        textNombreDoctor = (TextView) findViewById(R.id.text_nombre_doctor);
        textMedicamentos = (TextView) findViewById(R.id.text_cantidad_medicamentos);
        mListView = (ListView) findViewById(R.id.list_receta_medicamentos);


        USER_CLIENT_RETROFIT_PACIENT.detalleReceta(id_receta).enqueue(new Callback<List<Receta>>() {
            @Override
            public void onResponse(Call<List<Receta>> call, Response<List<Receta>> response) {

                Log.i("Card", "receta3 " +response.body().get(0).getFecha_vencimiento());




                textIdReceta.setText(String.valueOf(response.body().get(0).getId_receta()));

                textFechaExp.setText(response.body().get(0).getFecha_registro());
                textFehcaVen.setText(response.body().get(0).getFecha_vencimiento());
                textNombrePaciente.setText(response.body().get(0).getNombre_paciente());
                textNombreDoctor.setText(response.body().get(0).getMedico());
                textMedicamentos.setText(String.valueOf(response.body().get(0).getTotal_medicamentos()));

                textDiagnostico = response.body().get(0).getDiagnostico();

                textMedicamentos.setText(String.valueOf(response.body().get(0).getTotal_medicamentos()));


                mAdapter = new DetalleMedicamentosAdapter(DetalleRecetaActivity.this, response.body().get(0).getDetalle() );
                mListView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<List<Receta>> call, Throwable t) {
                Log.e("Card", "recetaid " +t.getMessage());

            }
        });









        registerMesaje = new AlertDialog.Builder(this);

        linearLayoutDiagnostico = (LinearLayout) findViewById(R.id.linearLayout_diagnostico);


        linearLayoutMedicamentos  = (LinearLayout) findViewById(R.id.linearLayout_medicamentos);

       // linearLayoutEstudios  = (LinearLayout) findViewById(R.id.linearLayout_estudios);

        btnCotizar = (Button) findViewById(R.id.btn_cotizar);

        expandableLayout0 = (ExpandableLayout) findViewById(R.id.expandable_layout_0);
       // expandableLayoutEstudios = (ExpandableLayout) findViewById(R.id.expandable_layout_estudios);


        expandableLayout0.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });


   /*     expandableLayoutEstudios.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });*/


        linearLayoutMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (expandableLayout0.isExpanded()) {
                    expandableLayout0.collapse();

                } else {
                    expandableLayout0.expand();
                }

            }
        });


  /*      linearLayoutEstudios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (expandableLayoutEstudios.isExpanded()) {
                    expandableLayoutEstudios.collapse();

                } else {
                    expandableLayoutEstudios.expand();
                }

            }
        });*/



        linearLayoutDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerMesaje.setTitle("Diagnostico");
                registerMesaje.setMessage(textDiagnostico);
                registerMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.show();

            }
        });



        btnCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCotizar = new Intent(DetalleRecetaActivity.this, RecetaEnvioOpcionMainActivity.class);
                intentCotizar.putExtra("id_receta", id_receta);
                startActivity(intentCotizar);

            }
        });









    }


    private void bindUI() {

        textIdReceta = (TextView) findViewById(R.id.text_id_dereceta);
        textFechaExp = (TextView) findViewById(R.id.text_fecha_deexp);
        textFehcaVen = (TextView) findViewById(R.id.text_fecha_deven);
        textNombrePaciente = (TextView) findViewById(R.id.text_nombre_paciente);
        textNombreDoctor = (TextView) findViewById(R.id.text_nombre_doctor);
        textMedicamentos = (TextView) findViewById(R.id.text_cantidad_medicamentos);

    }
}
