package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import bepp.com.bepp.R;
import bepp.com.bepp.menus.HomeActivityMenu;

public class CompraExitosaActivity extends AppCompatActivity {


    public LinearLayout finalizarCotizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_exitosa);




        finalizarCotizar =  (LinearLayout) findViewById(R.id.finalizar_cotizar);



        finalizarCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FinalizadoIntent = new Intent(CompraExitosaActivity.this, HomeActivityMenu.class);
                startActivity(FinalizadoIntent);
            }
        });
    }
}
