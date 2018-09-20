package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bepp.com.bepp.R;

public class SeleccionPagoActivity extends AppCompatActivity {

    private TextView numTrajeta,  nombreTarjeta;
    private LinearLayout layouTarjetaCobro;
    private  String nombre, numTrajetaRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_pago);

        numTrajeta = (TextView) findViewById(R.id.txt_num_tarjeta);
        nombreTarjeta  = (TextView) findViewById(R.id.txt_nombre_tarjeta);
        layouTarjetaCobro = (LinearLayout) findViewById(R.id.layout_tarjeta_cobro);


        layouTarjetaCobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeleccionPagoRecetaActivity.class);
                i.putExtra("List_data", "Hello");
                startActivityForResult(i, 1);

            }
        });


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                nombre = data.getStringExtra("Nombre");
                numTrajetaRespuesta = data.getStringExtra("NumCard");

                nombreTarjeta.setText(nombre);
                numTrajeta.setText(numTrajetaRespuesta);



            }}};
}
