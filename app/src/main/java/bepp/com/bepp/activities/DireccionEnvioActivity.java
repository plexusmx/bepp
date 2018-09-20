package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import bepp.com.bepp.R;
import bepp.com.bepp.models.Address;
import bepp.com.bepp.services.UserService;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class DireccionEnvioActivity extends AppCompatActivity {

    private ImageView cancelarDireccion, saveAddress;
    private EditText editTextNombre, editTextCalle, editTextNumExt, editTextNUmInt,editReferencia, editTextCodigoPastal, editTextColonia, editTextDelegacionMunicipio, editTextEstado,editTextPais;
    private TextInputLayout  textInputLayoutNombre, textInputLayoutCalle,textInputLayoutNumExt, textInputLayoutNumInt,textInputLayoutCodigoPastal,textInputLayoutPais,
            textInputLayoutColonia, textInputLayoutDelegacionMunicipio, textInputLayoutEstado,textLayoutReferencia;
    private UserService mAPIService;
    private Vibrator vib;
    Animation animShake;
    private String sendToken;
    // User Session Manager Class
    UserSessionManager session;
    private String cpMain;
    private String calleMain;
    private String numeroExtMain;
    private String coloniaMain;
    private String ciudadMain;
    private  String municipioMain;
    private  String estadoMain;
    private  String paisMain;
    private  String longitudMain ;
    private  String latitudMain ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_envio);

        Toolbar principal = (Toolbar) findViewById(R.id.toolbar_edit);
        principal.setTitle("");
        setSupportActionBar(principal);

        Intent busquedaMapa = getIntent();
        Bundle bundle = busquedaMapa.getExtras();


        cpMain =(String) bundle.get("cp");
        calleMain =    (String) bundle.get("calle");
        numeroExtMain =    (String) bundle.get("numeroExt");
        coloniaMain =    (String) bundle.get("colonia");
        ciudadMain =    (String) bundle.get("ciudad");
        municipioMain =    (String) bundle.get("municipio");
        estadoMain =    (String) bundle.get("estado");
        paisMain =    (String) bundle.get("pais");
        latitudMain =    (String) bundle.get("latitud");
        longitudMain =    (String) bundle.get("longitud");

        bindUI();


        editTextCalle.setText(calleMain);
        editTextNumExt.setText(numeroExtMain);
        editTextCodigoPastal.setText(cpMain);
        editTextColonia.setText(coloniaMain);
        editTextDelegacionMunicipio.setText(municipioMain);
        editTextEstado.setText(estadoMain);
        editTextEstado.setEnabled(false);
        editTextPais.setText(paisMain);
        editTextPais.setEnabled(false);



                // Session class instance
        session = new UserSessionManager(getApplicationContext());

        if (session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get token
        sendToken = user.get(UserSessionManager.TOKEN_KEY);

        HttpClient.setToken(user.get(UserSessionManager.TOKEN_KEY));

        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });

        cancelarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelarDireccionIntent = new Intent(DireccionEnvioActivity.this, DireccionEnvioMainActivity.class);
                startActivity(cancelarDireccionIntent);
            }
        });








    }


    private void bindUI() {

        // ImageView
        cancelarDireccion = (ImageView)findViewById(R.id.imagecancelarDireccion);
        saveAddress = (ImageView)findViewById(R.id.saveAddress);

        // EditText
        editTextNombre = (EditText) findViewById(R.id.text_nombre);
        editTextCalle = (EditText) findViewById(R.id.text_calle);
        editTextNumExt = (EditText) findViewById(R.id.text_numero_ext);
        editTextNUmInt = (EditText) findViewById(R.id.text_numero_int);
        editTextCodigoPastal = (EditText) findViewById(R.id.text_codigopostal);
        editTextColonia = (EditText) findViewById(R.id.text_colonia);
        editTextDelegacionMunicipio = (EditText) findViewById(R.id.text_delegacion_municipio);
        editTextEstado = (EditText) findViewById(R.id.text_estado);
        editTextPais = (EditText) findViewById(R.id.text_pais);
        editReferencia = (EditText) findViewById(R.id.text_referencia);



        // TextInputLayout
        textInputLayoutNombre = (TextInputLayout)findViewById(R.id.layout_direccion_nombre);
        textInputLayoutCalle = (TextInputLayout)findViewById(R.id.layout_calle);
        textInputLayoutNumExt = (TextInputLayout)findViewById(R.id.layout_numero_ext);
        textInputLayoutNumInt = (TextInputLayout)findViewById(R.id.layout_numero_int);
        textInputLayoutCodigoPastal = (TextInputLayout)findViewById(R.id.layout_codigopostal);
        textInputLayoutColonia = (TextInputLayout)findViewById(R.id.layout_colonia);
        textInputLayoutDelegacionMunicipio = (TextInputLayout)findViewById(R.id.layout_delegacion_municipio);
        textInputLayoutEstado = (TextInputLayout)findViewById(R.id.layout_estado);
        textInputLayoutPais= (TextInputLayout)findViewById(R.id.layout_pais);
        textLayoutReferencia = (TextInputLayout)findViewById(R.id.layout_referencia);


        // Others
        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }

    private void saveAddress() {

        Log.i("Validacion", "entro0");

        String nombre = editTextNombre.getText().toString().trim();
        String telefono = editTextNombre.getText().toString().trim();
        String cp = editTextCodigoPastal.getText().toString().trim();
        String calle = editTextCalle.getText().toString().trim();
        String nroext = editTextNumExt.getText().toString().trim();
        String norint = editTextNUmInt.getText().toString().trim();
        String colonia = editTextColonia.getText().toString().trim();
        String delegacion = editTextDelegacionMunicipio.getText().toString().trim();
        String estado = editTextEstado.getText().toString().trim();
        String pais = editTextPais.getText().toString().trim();
        String referencia = editReferencia.getText().toString().trim();


        Log.i("Validacion", "enombre"+nombre);
        if (!checkNombre(nombre)) {
            Log.i("Validacion", "entro3");

            editTextNombre.setAnimation(animShake);
            editTextNombre.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }


        textInputLayoutNombre.setErrorEnabled(false);

        Integer id_direccion = null;


        Address address = new Address(nombre, "", Integer.valueOf(cp), calle, nroext, norint, colonia, delegacion, estado, pais, referencia, longitudMain, latitudMain, id_direccion,"1");
        Log.i("direccion", "datos"+address.toString());

    sendPost(address);
    }


    public void sendPost(Address address){
        USER_CLIENT_RETROFIT.createAddress(address).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    Toast.makeText(DireccionEnvioActivity.this, "Dirección de envio exitosamente creado", Toast.LENGTH_LONG).show();
                    Intent backDirectoryIntent = new Intent(DireccionEnvioActivity.this, DireccionEnvioMainActivity.class);
                    startActivity(backDirectoryIntent);

                } else if(response.code() == 500){
                    Toast.makeText(DireccionEnvioActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){
                    Toast.makeText(DireccionEnvioActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }



    private boolean checkNombre(String nombre) {
        if (nombre.isEmpty() || !isValidField(nombre)) {

            textInputLayoutNombre.setErrorEnabled(true);
            textInputLayoutNombre.setError(getString(R.string.err_msg_nombre));
            editTextNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editTextNombre);
            return false;
        }

        if (editTextNombre.length() < 3) {

            textInputLayoutNombre.setErrorEnabled(true);
            textInputLayoutNombre.setError(getString(R.string.err_msg_minLengtexto));
            editTextNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editTextNombre);
            return false;
        }


        textInputLayoutNombre.setErrorEnabled(false);
        return true;
    }





    private static boolean isValidField(String field) {
        return !TextUtils.isEmpty(field) && Pattern.compile("^([\\p{L}]+?[ ]?|[\\p{L}])+$").matcher(field).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
