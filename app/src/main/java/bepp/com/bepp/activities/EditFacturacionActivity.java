package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.HashMap;

import bepp.com.bepp.R;
import bepp.com.bepp.models.FiscalData;
import bepp.com.bepp.services.UserService;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class EditFacturacionActivity extends AppCompatActivity {


    private ImageView cancelar, guardar;
    private EditText editTextNombre, editTextRfc, editTextCalle, editTextNumeroExt, editTextNumeroInt, editTextCodigoPostal, editTextColonia,
            editTextDelegacionMunicipio, editTextEstado, editTextPais;
    private TextInputLayout textInputLayoutNombre, textInputLayoutRfc, textInputLayoutCalle, textInputLayoutNumeroExt, textInputLayoutNumeroInt, textInputLayoutCodigoPostal, textInputLayoutColonia,
            textInputLayoutDelegacionMunicipio, textInputLayoutEstado, textInputLayoutPais;
    private RadioButton radioMoral, radioFisica;
    private UserService mAPIService;
    private Vibrator vib;
    Animation animShake;
    // User Session Manager Class
    UserSessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_facturacion);




        Toolbar principal = (Toolbar) findViewById(R.id.toolbar_edit);
        principal.setTitle("");
        setSupportActionBar(principal);

        bindUI();

        mAPIService = USER_CLIENT_RETROFIT;
        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelarIntent = new Intent(EditFacturacionActivity.this, FacturacionMainActivity.class);
                startActivity(cancelarIntent);
            }
        });

        if (session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        HttpClient.setToken(user.get(UserSessionManager.TOKEN_KEY));


        radioMoral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    radioFisica.setChecked(false);
                }
            }

        });


        radioFisica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    radioMoral.setChecked(false);
                }
            }

        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFacturationData();
            }
        });

        findViewById(R.id.layoutgeneral).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
    }

    private void bindUI() {

        //  ImageView
        cancelar = (ImageView) findViewById(R.id.imagecancelar);
        guardar = (ImageView) findViewById(R.id.saveFacturationData);

        // EditText
        editTextNombre = (EditText) findViewById(R.id.text_nombre);
        editTextRfc = (EditText) findViewById(R.id.text_rfc);
        editTextCalle = (EditText) findViewById(R.id.text_calle);
        editTextNumeroExt = (EditText) findViewById(R.id.text_numero_ext);
        editTextNumeroInt = (EditText) findViewById(R.id.text_numero_int);
        editTextCodigoPostal = (EditText) findViewById(R.id.text_codigopostal);
        editTextColonia = (EditText) findViewById(R.id.text_colonia);
        editTextDelegacionMunicipio = (EditText) findViewById(R.id.text_delegacion_municipio);
        editTextEstado = (EditText) findViewById(R.id.text_estado);
        editTextPais = (EditText) findViewById(R.id.text_pais);


        radioMoral = (RadioButton)findViewById(R.id.radio_moral);
        radioFisica = (RadioButton)findViewById(R.id.radio_fisica);

        // TextInputLayout
        textInputLayoutNombre = (TextInputLayout) findViewById(R.id.layout_paciente_nombre);
        textInputLayoutRfc = (TextInputLayout) findViewById(R.id.layout_rfc);
        textInputLayoutCalle = (TextInputLayout) findViewById(R.id.layout_calle);
        textInputLayoutNumeroExt = (TextInputLayout) findViewById(R.id.layout_numero_ext);
        textInputLayoutNumeroInt = (TextInputLayout) findViewById(R.id.layout_numero_int);
        textInputLayoutCodigoPostal = (TextInputLayout) findViewById(R.id.layout_codigopostal);
        textInputLayoutColonia = (TextInputLayout) findViewById(R.id.layout_colonia);
        textInputLayoutDelegacionMunicipio = (TextInputLayout) findViewById(R.id.layout_delegacion_municipio);
        textInputLayoutEstado = (TextInputLayout) findViewById(R.id.layout_estado);
        textInputLayoutPais = (TextInputLayout) findViewById(R.id.layout_pais);

        // Others
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }

    private void saveFacturationData() {

        String razon_social = editTextNombre.getText().toString().trim();
        String rfc = editTextRfc.getText().toString().trim();
        String calle = editTextCalle.getText().toString().trim();
        String nroext = editTextNumeroExt.getText().toString().trim();
        String nroint = editTextNumeroInt.getText().toString().trim();
        String colonia = editTextColonia.getText().toString().trim();
        String delegacion = editTextDelegacionMunicipio.getText().toString().trim();
        String estado = editTextEstado.getText().toString().trim();
        String pais = editTextPais.getText().toString().trim();
        Integer tipo = 1;
        String cp = editTextCodigoPostal.getText().toString().trim();
        Integer id_dato = null;



        if(!radioMoral.isChecked()){
            tipo = 2;
        }else {
            tipo = 1;
        }



        Integer idDato = getIntent().getExtras().getInt("id_dato");

        FiscalData data = new FiscalData(razon_social,rfc,calle,nroext,nroint,colonia,
                delegacion,estado,calle,tipo,cp,idDato, "1" );

        USER_CLIENT_RETROFIT.updateFiscalData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(EditFacturacionActivity.this, "Datos de Facturación exitosamente creados", Toast.LENGTH_LONG).show();
                    Intent backDirectoryIntent = new Intent(EditFacturacionActivity.this, FacturacionMainActivity.class);
                    startActivity(backDirectoryIntent);

                } else if(response.code() == 500){
                    Toast.makeText(EditFacturacionActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){
                    Toast.makeText(EditFacturacionActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }else if(response.code() == 404){
                    Toast.makeText(EditFacturacionActivity.this, "datos ine", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditFacturacionActivity.this, "Error al consultar el servicio", Toast.LENGTH_LONG).show();
            }
        });





    }
}
