package bepp.com.bepp.activities;

import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import bepp.com.bepp.R;
import bepp.com.bepp.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CobroActivity extends AppCompatActivity {

    private LinearLayout siguenteCotizar,layouTarjetaCobro;
    private TextView numTrajeta,  nombreTarjeta;
    private  String nombre, numTrajetaRespuesta;
    private Button btnComprar;
    private EditText editTextNombre, editTextNumTarjeta, editTextFechaMesVto, editTextFechaAnioVto, editTextCvv;
    private Switch swDebito, swCredito, swPredeterminada;
    private TextInputLayout layNombre, layNumTajeta, layFechaMesVto, layFechaAnioVto, layCvv;
    private UserService mAPIService;
    private RadioButton radioCredito, radioDebito;
    private Vibrator vib;
    Animation animShake;
    private String sourceId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro);



        Intent intent = getIntent();
        Bundle id = intent.getExtras();
        if(id != null)
        {
            sourceId= (String) id.get("sourceId");

        }



        siguenteCotizar = (LinearLayout) findViewById(R.id.siguente_cotizar);
        siguenteCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();


                Intent resultadoIntent = new Intent(CobroActivity.this, CompraExitosaActivity.class);
                startActivity(resultadoIntent);



            }
        });

        numTrajeta = (TextView) findViewById(R.id.txt_num_tarjeta);
        nombreTarjeta  = (TextView) findViewById(R.id.txt_nombre_tarjeta);
        layouTarjetaCobro = (LinearLayout) findViewById(R.id.layout_datos_pagp);




        numTrajeta.setTransformationMethod(new MyPasswordTransformationMethod());

        layouTarjetaCobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeleccionPagoRecetaActivity.class);
                i.putExtra("List_data", "Hello");
                startActivityForResult(i, 1);
            }
        });



    }

    private void submitForm() {


        //Incrementar



        /*Customer customer = new Customer( "Juan","Vazquez Juarez","4423456723","juan.vazquez@empresa.com.mx");
        Charge charge = new Charge(sourceId, "card", 20,"Campra Bepp","oid-00054",
                "kR1MiQhz2otdIuUlQkbEyitIqVMiI16f",customer);
            sendPost(charge);*/



    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                nombre = data.getStringExtra("Nombre");
                numTrajetaRespuesta = data.getStringExtra("NumCard");
                nombreTarjeta.setText(nombre);
                numTrajeta.setText(numTrajetaRespuesta);

            }}};




    public static class MyPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source;
            }
            public char charAt(int index) {
                char caracter;
                switch (index)
                {
                    case 4:
                        caracter = ' ';
                        break;
                    case 9:
                        caracter = ' ';
                        break;
                    case 14:
                        caracter = ' ';
                        break;
                    default:
                        if(index < 15)
                            return '*';
                        else
                            caracter = mSource.charAt(index);
                        break;



                }


                return caracter;
            }
            public int length() {
                return mSource.length();
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end);
            }
        }
    };


/*
    public void sendPost(Charge charge) {
        USER_CLIENT_RETROFIT_OPENPAY.cargoTarjeta(charge).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
*/

}

