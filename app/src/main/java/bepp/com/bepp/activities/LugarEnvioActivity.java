package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import bepp.com.bepp.R;

public class LugarEnvioActivity extends AppCompatActivity {

    private LinearLayout siguenteCotizar , layoutDireccionReceta;
    private String nombre,calle, numero ;
    private TextView txtNombreDireccionReceta, txtCalleReceta, txtNumExtReceta;
    private Button btnRecogerFarmacia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_envio);





        siguenteCotizar = ( LinearLayout) findViewById(R.id.siguente_cotizar);
        layoutDireccionReceta = (LinearLayout) findViewById(R.id.layout_direccion_receta);
        btnRecogerFarmacia = (Button) findViewById(R.id.btn_recoger_farmacia);

        txtNombreDireccionReceta = (TextView) findViewById(R.id.txt_nombre_direccion_receta);
        txtCalleReceta = (TextView)findViewById(R.id.txt_calle_receta);
        txtNumExtReceta = (TextView) findViewById(R.id.txt_num_ext_receta);

        siguenteCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultadoIntent = new Intent(LugarEnvioActivity.this, MedicinasRecetasActivity.class);
                startActivity(resultadoIntent);
            }
        });

       /* btnRecogerFarmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent recogerIntent = new Intent(LugarEnvioActivity.this, RecogerAFarmaciaActivity.class);
                startActivity(recogerIntent);

            }
        });*/

        layoutDireccionReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), DireccionEnvioRecetaActivity.class);
                i.putExtra("List_data", "Hello");
                startActivityForResult(i, 1);



            }
        });






    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                 nombre = data.getStringExtra("Nombre");
                 calle = data.getStringExtra("Calle");
                 numero = data.getStringExtra("Numero");


                txtNombreDireccionReceta.setText(nombre);
                txtCalleReceta.setText(calle);
                txtNumExtReceta.setText(numero);




            }}};


}





















