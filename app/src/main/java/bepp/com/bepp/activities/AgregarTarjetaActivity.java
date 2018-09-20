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
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import bepp.com.bepp.R;
import bepp.com.bepp.models.Address;
import bepp.com.bepp.models.BasicResponse;
import bepp.com.bepp.models.Doctor;
import bepp.com.bepp.models.EmptyRequest;
import bepp.com.bepp.models.FiscalData;
import bepp.com.bepp.models.Login;
import bepp.com.bepp.models.MedicalSpeciality;
import bepp.com.bepp.models.Message;
import bepp.com.bepp.models.Patient;
import bepp.com.bepp.models.PaymentCard;
import bepp.com.bepp.models.ResponseList;
import bepp.com.bepp.services.UserService;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Path;


import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class AgregarTarjetaActivity extends AppCompatActivity {

    private ImageView cancelar, guardar;
    private EditText editTextNombre, editTextNumTarjeta, editTextFechaMesVto, editTextFechaAnioVto, editTextCvv;
    private Switch swDebito, swCredito, swPredeterminada;
    private TextInputLayout layNombre, layNumTajeta, layFechaMesVto, layFechaAnioVto, layCvv;
    private UserService mAPIService;
    private Vibrator vib;
    Animation animShake;
    private String sendToken;
    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarjeta);
        Toolbar principal = (Toolbar) findViewById(R.id.toolbar_tarjeta);
        principal.setTitle("");
        setSupportActionBar(principal);

        bindUI();

        mAPIService = USER_CLIENT_RETROFIT;
        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelarIntent = new Intent(AgregarTarjetaActivity.this, MetodoPagoMainActivity.class);
                startActivity(cancelarIntent);
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        if (session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get token
        sendToken = user.get(UserSessionManager.TOKEN_KEY);

        HttpClient.setToken(user.get(UserSessionManager.TOKEN_KEY));

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

        // ImageView
        cancelar = (ImageView) findViewById(R.id.imagecancelar);
        guardar = (ImageView) findViewById(R.id.guardar);

        // EditText
        editTextNombre = (EditText) findViewById(R.id.text_nombre);
        editTextNumTarjeta = (EditText) findViewById(R.id.text_num_tarjeta);
        editTextFechaMesVto = (EditText) findViewById(R.id.text_fecha_mes_vto);
        editTextFechaAnioVto = (EditText) findViewById(R.id.text_fecha_anio_vto);
        editTextCvv = (EditText) findViewById(R.id.text_cvv);

        // TextInputLayout
        layNombre = (TextInputLayout) findViewById(R.id.layout_nombre);
        layNumTajeta = (TextInputLayout) findViewById(R.id.layout_num_tarjeta);
        layFechaMesVto = (TextInputLayout) findViewById(R.id.layout_fecha_mes_vto);
        layFechaAnioVto = (TextInputLayout) findViewById(R.id.layout_fecha_anio_vto);
        layCvv = (TextInputLayout) findViewById(R.id.layout_cvv);

        // Switch
       /* swDebito = (Switch) findViewById(R.id.debito);
        swCredito = (Switch) findViewById(R.id.credito);
        swPredeterminada = (Switch) findViewById(R.id.sw_predeterminada);*/

        // Others
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }


    private void submitForm() {

        String nombre = editTextNombre.getText().toString().trim();
        String numTarjeta = editTextNumTarjeta.getText().toString().trim();
        String fechaMesVto = editTextFechaMesVto.getText().toString().trim();
        String fechAnioVto = editTextFechaAnioVto.getText().toString().trim();
        // int cvv = Integer.parseInt(editTextCvv.getText().toString().trim());
        //boolean tipo = swDebito.isChecked();
        String tipo = "1";
        boolean predeterminada = true;

        if (!checkNombre(nombre)) {
            editTextNombre.setAnimation(animShake);
            editTextNombre.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }


        layNombre.setErrorEnabled(false);

        PaymentCard paymentCard = new PaymentCard(nombre, "1", "0", fechaMesVto,
                fechAnioVto, numTarjeta);


        try {
            Log.i("Bepp", "antes de send post"+paymentCard);
            sendPost(paymentCard);
            Log.i("Bepp", "despues de send post");
        } catch (Exception e) {
            Log.e("Bepp", "valio merga sendpost");
            Toast.makeText(AgregarTarjetaActivity.this, "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("Bepp", e.getMessage(), e);
        }


    }

    private boolean checkNombre(String nombre) {
        if (nombre.isEmpty() || !isValidField(nombre)) {

            layNombre.setErrorEnabled(true);
            layNombre.setError(getString(R.string.err_msg_nombre));
            editTextNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editTextNombre);
            return false;
        }

        if (editTextNombre.length() < 3) {

            layNombre.setErrorEnabled(true);
            layNombre.setError(getString(R.string.err_msg_minLengtexto));
            editTextNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editTextNombre);
            return false;
        }


        layNombre.setErrorEnabled(false);
        return true;
    }

    private boolean checkNumTarjeta(String nombre) {
        if (nombre.isEmpty() || !isValidField(nombre)) {

            layNombre.setErrorEnabled(true);
            layNombre.setError(getString(R.string.err_msg_nombre));
            editTextNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editTextNombre);
            return false;
        }

        if (editTextNombre.length() < 3) {

            layNombre.setErrorEnabled(true);
            layNombre.setError(getString(R.string.err_msg_minLengtexto));
            editTextNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editTextNombre);
            return false;
        }


        layNombre.setErrorEnabled(false);
        return true;
    }


    // Validations
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isValidNumberPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    private static boolean isValidField(String field) {
        return !TextUtils.isEmpty(field) && Pattern.compile("^([\\p{L}]+?[ ]?|[\\p{L}])+$").matcher(field).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    public void sendPost(PaymentCard paymentCard){
        USER_CLIENT_RETROFIT.createPaymentCard(paymentCard).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AgregarTarjetaActivity.this, "  se agrego " + response.code(), Toast.LENGTH_LONG).show();

                if(response.code() == 201){
                    Toast.makeText(AgregarTarjetaActivity.this, "Ha sido exitosamente creado", Toast.LENGTH_LONG).show();
                    Intent backDirectoryIntent = new Intent(AgregarTarjetaActivity.this, MetodoPagoMainActivity.class);
                    startActivity(backDirectoryIntent);

                } else if(response.code() == 500){
                    Toast.makeText(AgregarTarjetaActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){
                    Toast.makeText(AgregarTarjetaActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AgregarTarjetaActivity.this, "Fallido ", Toast.LENGTH_LONG).show();
                Log.e("Bepp",t.getMessage(),t);
            }
        });

    }
}

