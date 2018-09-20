package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import bepp.com.bepp.R;

public class ResultadoFarmaciasActivity extends AppCompatActivity {


    private TextView verOferta;
    private String nombre,rfc ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_farmacias);





        verOferta = (TextView) findViewById(R.id.ver_oferta);


        verOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recetasIntent = new Intent(ResultadoFarmaciasActivity.this, DetalleCompraActivity.class);
                startActivity(recetasIntent);
            }
        });
    }


}
