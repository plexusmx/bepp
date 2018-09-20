package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import bepp.com.bepp.R;

public class FarmaciaRecetaActivity extends AppCompatActivity {


    private Button btnMenos, btnMas;
    private TextView numCantidad;
    private LinearLayout linearSiguenteCotizarFinalizar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmacia_receta);


        buidUI();




        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidad = Integer.parseInt(numCantidad.getText().toString().trim());
                if(cantidad > 0){
                    cantidad--;
                    numCantidad.setText(String.valueOf(cantidad));


                }

            }
        });



        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidad = Integer.parseInt(numCantidad.getText().toString().trim());
                if(cantidad >=  cantidad  ){
                    cantidad++;
                    numCantidad.setText(String.valueOf(cantidad));


                }


            }
        });



        linearSiguenteCotizarFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultaIntent = new Intent(FarmaciaRecetaActivity.this, DetalleCompraActivity.class);
                startActivity(resultaIntent);
            }
        });





    }


    private void buidUI(){

        btnMenos = (Button) findViewById(R.id.button_menos);
        btnMas = (Button) findViewById(R.id.button_mas);
        numCantidad = (TextView) findViewById(R.id.text_cantidad);

        linearSiguenteCotizarFinalizar = (LinearLayout) findViewById(R.id.linear_siguente_cotizar_finalizar);

    }
}
