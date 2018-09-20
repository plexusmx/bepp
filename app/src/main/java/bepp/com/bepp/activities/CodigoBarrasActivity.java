package bepp.com.bepp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import bepp.com.bepp.R;

public class CodigoBarrasActivity extends AppCompatActivity {

    private Button button;
    private ImageView cancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_barras);

        Toolbar principalScanner = (Toolbar) findViewById(R.id.toolbar_escanner);
        principalScanner.setTitle("");
        setSupportActionBar(principalScanner);

        button = (Button) findViewById(R.id.btn_escanner);

        final Activity activity = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator =  new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Escanner");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        cancelar = (ImageView)findViewById(R.id.imagecancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelarIntent = new Intent(CodigoBarrasActivity.this, ConfigurationActivity.class);
                startActivity(cancelarIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result= IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null ) {
            if (result.getContents() == null) {
                Log.d("CodigoBarrasActivity", "Escaner cancelado");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Log.d("CodigoBarrasActivity", "Escaneado");
                Toast.makeText(this, "Codigo:" + result.getContents(), Toast.LENGTH_LONG).show();

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
}
