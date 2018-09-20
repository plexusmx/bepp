package bepp.com.bepp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.FarmacyAdapter;
import bepp.com.bepp.menus.HomeActivityMenu;
import bepp.com.bepp.models.FiscalData;
import bepp.com.bepp.models.PackageSend;
import bepp.com.bepp.models.PaymentCard;
import bepp.com.bepp.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;
import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT_PACIENT;


public class DetalleCompraActivity extends AppCompatActivity {


    public AlertDialog.Builder registerMesaje;
    public LinearLayout siguenteCotizarFinalizar, layoutDatosFacturacion;
    private String nombre, nombreTrajeta, rfc, numTrajetaRespuesta;
    private TextView txtNombreFacturacion, txtRFC;
    private LinearLayout siguenteCotizar, layouTarjetaCobro;
    private TextView numTrajeta, nombreTarjeta, textProductos,textSubtotal;
    private Button btnComprar;
    private EditText editTextNombre, editTextNumTarjeta, editTextFechaMesVto, editTextFechaAnioVto, editTextCvv;
    private Switch swDebito, swCredito, swPredeterminada;
    private TextInputLayout layNombre, layNumTajeta, layFechaMesVto, layFechaAnioVto, layCvv;
    private UserService mAPIService;
    private RadioButton radioCredito, radioDebito;
    private Vibrator vib;
    Animation animShake;
    private List<PackageSend> packageSendList;
    private Boolean tarjetaDePara = false;
    private  Boolean tarjetaDe;
    private String sourceId ,tipo;
    private List<FiscalData> fiscalDataList;
    private List<PaymentCard> paymentCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_compra);

        int id_receta = getIntent().getIntExtra("id_receta",0);
        Log.i("id_receta", "6 : "+id_receta);


        siguenteCotizarFinalizar = (LinearLayout) findViewById(R.id.siguente_cotizar_finalizar);
        layoutDatosFacturacion = (LinearLayout) findViewById(R.id.layout_datos_facturacion);
        txtNombreFacturacion = (TextView) findViewById(R.id.txt_nombre_factura);
        textProductos = (TextView) findViewById(R.id.text_productos);
        textSubtotal  = (TextView) findViewById(R.id.text_subtotal);
        txtRFC = (TextView) findViewById(R.id.txt_nombre_rfc);
        // EditText
        editTextNombre = (EditText) findViewById(R.id.text_nombre);
        editTextNumTarjeta = (EditText) findViewById(R.id.text_num_tarjeta);
        editTextFechaMesVto = (EditText) findViewById(R.id.text_fecha_mes_vto);
        editTextFechaAnioVto = (EditText) findViewById(R.id.text_fecha_anio_vto);
        editTextCvv = (EditText) findViewById(R.id.text_cvv);
        numTrajeta = (TextView) findViewById(R.id.txt_num_tarjeta);
        nombreTarjeta = (TextView) findViewById(R.id.txt_nombre_tarjeta);
        layouTarjetaCobro = (LinearLayout) findViewById(R.id.layout_datos_pagp);

        // TextInputLayout
        layNombre = (TextInputLayout) findViewById(R.id.layout_nombre);
        layNumTajeta = (TextInputLayout) findViewById(R.id.layout_num_tarjeta);
        layFechaMesVto = (TextInputLayout) findViewById(R.id.layout_fecha_mes_vto);
        layFechaAnioVto = (TextInputLayout) findViewById(R.id.layout_fecha_anio_vto);
        layCvv = (TextInputLayout) findViewById(R.id.layout_cvv);
        numTrajeta.setTransformationMethod(new MyPasswordTransformationMethod());

        siguenteCotizarFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();
                Intent compraExitoIntent = new Intent(DetalleCompraActivity.this, CompraExitosaActivity.class);
                startActivity(compraExitoIntent);

            }
        });


        layoutDatosFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FacturacionRecetaDetalleActivity.class);
                i.putExtra("List_data", "Hello");
                startActivityForResult(i, 1);
            }
        });

        layouTarjetaCobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeleccionPagoRecetaActivity.class);
                i.putExtra("List_data", "Hello");
                startActivityForResult(i, 1);
            }
        });


        USER_CLIENT_RETROFIT.getFiscalData().enqueue(new Callback<List<FiscalData>>() {
            @Override
            public void onResponse(Call<List<FiscalData>> call, Response<List<FiscalData>> response) {

                fiscalDataList = response.body();
                for(FiscalData fiscal:fiscalDataList){

                    if(fiscal.getPredeterminada().equals("1")){
                        txtNombreFacturacion.setText(fiscal.getRazon_social());
                        txtRFC.setText(fiscal.getRfc());

                    }

                }

            }

            @Override
            public void onFailure(Call<List<FiscalData>> call, Throwable t) {

            }
        });

        USER_CLIENT_RETROFIT.getPaymentCards().enqueue(new Callback<List<PaymentCard>>() {
            @Override
            public void onResponse(Call<List<PaymentCard>> call, Response<List<PaymentCard>> response) {

                paymentCardList = response.body();
                for(PaymentCard paymentCard:paymentCardList){

                    if(paymentCard.getPredeterminada().equals("1")){
                        nombreTarjeta.setText(paymentCard.getNombre_titular());
                        numTrajeta.setText(paymentCard.getNro_tarjeta());

                    }

                }


            }

            @Override
            public void onFailure(Call<List<PaymentCard>> call, Throwable t) {

            }
        });


        USER_CLIENT_RETROFIT_PACIENT.infoFarmaciaReceta(id_receta).enqueue(new Callback<List<PackageSend>>() {
            @Override
            public void onResponse(Call<List<PackageSend>> call, Response<List<PackageSend>> response) {



                if(response.code() == 200){

                    packageSendList =  response.body();

                    textProductos.setText(packageSendList.get(0).getFarmacy().get(0).getCantidad_medicamentos());
                    textSubtotal.setText(packageSendList.get(0).getFarmacy().get(0).getSubtotalpedido());



                } else if(response.code() == 500){
                    Toast.makeText( DetalleCompraActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText( DetalleCompraActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<PackageSend>> call, Throwable t) {

            }
        });



    }

    private void submitForm() {

       /* String nombre = editTextNombre.getText().toString().trim();
        String numTarjeta = (editTextNumTarjeta.getText().toString().trim());
        String fechaMesVto = (editTextFechaMesVto.getText().toString().trim());
        String fechAnioVto = (editTextFechaAnioVto.getText().toString().trim());
        String cvv = (editTextCvv.getText().toString().trim());
       */ //boolean tipo = swDebito.isChecked();


        // int tipo = 1;
        //int predeterminada = 0;



        /*if(!radioCredito.isChecked()){
            tipo = 0;
        }else {
            tipo = 1;
        }

        if(!swPredeterminada.isChecked()){
            predeterminada = 0;
        }else {
            predeterminada = 1;
        }*/







       /* PaymentCard paymentCard = new PaymentCard(nombre, predeterminada, tipo, fechaMesVto,
                fechAnioVto, numTarjeta);*/



       /* Address address = new Address("75900", "Av 5 de Febrero", "Roble 207", "col carrillo", "MEXICO","MEXICO","MX");

        Card card = new Card(nombre,fechaMesVto,fechAnioVto,address,numTarjeta,cvv);
*/


        try {
            Log.i("Bepp", "antes de send post");
//            sendPost(card);
            Log.i("Bepp", "despues de send post");
        } catch (Exception e) {
            Log.e("Bepp", "valio merga sendpost");
            Toast.makeText(DetalleCompraActivity.this, "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("Bepp", e.getMessage(), e);
        }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {


                nombre = data.getStringExtra("Nombre");
                rfc = data.getStringExtra("RFC");


                tipo = data.getStringExtra("tipo");
                nombreTrajeta = data.getStringExtra("NombreTarjeta");
                numTrajetaRespuesta = data.getStringExtra("NumCard");
                Log.i("tar" ,": "+tarjetaDePara);

                if(tipo.equals("facturacion") ){
                    txtNombreFacturacion.setText(nombre);
                    txtRFC.setText(rfc);

                }

                if(tipo.equals("tarjeta")) {
                    nombreTarjeta.setText(nombreTrajeta);
                    numTrajeta.setText(numTrajetaRespuesta);
                }




            }
        }
    }

    ;


/*
    public void sendPost(Card card) {
        USER_CLIENT_RETROFIT_OPENPAY.agregarTarjeta(card).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                sourceId = response.body().getId().toString().trim();
                Intent intentCobro = new Intent(DetalleCompraActivity.this, CobroActivity.class);

                intentCobro.putExtra("sourceId", sourceId);
                startActivity(intentCobro);



            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }
*/


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
                switch (index) {
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
                        if (index < 15)
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
    }

    ;
}