package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bepp.com.bepp.R;

public class ChooseActivity extends AppCompatActivity {

    private Button btnInicoSesion;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);


        bindUI();


        btnInicoSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OpcionInicioSesion = new Intent(ChooseActivity.this, LoginOpcionActivity.class);
                startActivity(OpcionInicioSesion);

            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inicioResgistro = new Intent(ChooseActivity.this, RegistroMenuActivity.class);
                startActivity(inicioResgistro);

            }
        });

    }

    private void bindUI() {


        btnInicoSesion = (Button) findViewById(R.id.inicio_sesion);
        btnRegistro = (Button) findViewById(R.id.registrate);

    }
}
