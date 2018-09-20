package bepp.com.bepp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import bepp.com.bepp.R;
import bepp.com.bepp.menus.HomeActivityMenu;
import bepp.com.bepp.models.BasicResponse;
import bepp.com.bepp.models.Login;
import bepp.com.bepp.models.LoginUserModel;
import bepp.com.bepp.models.ResponseLoginUserModel;
import bepp.com.bepp.services.LoginUserService;
import bepp.com.bepp.services.RecoverPassService;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.LoginUserUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;


public class LoginActivity extends AppCompatActivity {

    private LoginUserService mAPIService;
    private Button btnLogin;
    private EditText inptCuenta, inptPassword;
    private TextView textRecuperar, registro;
    private TextInputLayout inptCuentaLayout , inptPasswordLayout;
    private Vibrator vib;
    private ImageView imageBack;
    Animation animShake;
    public int tipo = 4;
    public AlertDialog.Builder loginMesaje;

    // User Session Manager Class
    UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());


        bindUI();
        mAPIService = LoginUserUtil.getRetrofitService();
        loginMesaje = new AlertDialog.Builder(this);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });



        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opcioneLoginIntent = new Intent(LoginActivity.this, LoginOpcionActivity.class);
                startActivity(opcioneLoginIntent);
            }
        });


        textRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restablecerIntent = new Intent(LoginActivity.this, RecoverPassActivity.class);
                startActivity(restablecerIntent);

            }
        });


        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registroMenuIntent = new Intent(LoginActivity.this, RegistroMenuActivity.class);
                startActivity(registroMenuIntent);

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


        btnLogin = (Button) findViewById(R.id.btn_inicio_sesion);
        inptCuenta =(EditText) findViewById(R.id.text_user);
        inptPassword =(EditText) findViewById(R.id.text_password);
        imageBack = ( ImageView) findViewById(R.id.image_back);
        textRecuperar = (TextView) findViewById(R.id.text_recuperar);
        registro  = (TextView) findViewById(R.id.registro);

        inptCuentaLayout = (TextInputLayout) findViewById(R.id.text_user_layout);
        inptPasswordLayout = (TextInputLayout) findViewById(R.id.text_password_layout);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }


    private void submitForm() {


        String email = inptCuenta.getText().toString().trim();
        String password = inptPassword.getText().toString().trim();



        if (!checkEmail(email)) {
            inptCuenta.setAnimation(animShake);
            inptCuenta.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if (!checkPassword(password)) {
            inptPassword.setAnimation(animShake);
            inptPassword.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        inptCuentaLayout.setErrorEnabled(false);
        inptPasswordLayout.setErrorEnabled(false);


        Log.i("login", "datosemail "+email+" "+password+" "+tipo );
        Login login = new Login(email, password , tipo, null);
        sendPost(login);
    }



    private boolean checkEmail(String email) {

        if (email.isEmpty() || !isValidEmail(email)) {

            inptCuentaLayout.setErrorEnabled(true);
            inptCuentaLayout.setError(getString(R.string.err_msg_email));
            inptCuenta.setError(getString(R.string.err_msg_required));
            requestFocus(inptCuenta);
            return false;
        }


        inptCuentaLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword(String password) {
        if (password.isEmpty()) {

            inptPasswordLayout.setError(getString(R.string.err_msg_password));
            requestFocus(inptPassword);
            return false;
        }
        inptPasswordLayout.setErrorEnabled(false);
        return true;
    }




    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void sendPost(final Login login) {

        USER_CLIENT_RETROFIT.login(login).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                if(response.code() == 200){

                    String usuario =login.getUsuario();
                    String token = response.body().getToken();

                    Intent homeIntent = new Intent(LoginActivity.this, HomeActivityMenu.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    //Toast.makeText(LoginActivity.this, "-----"+token, Toast.LENGTH_LONG).show();
                    session.createUserLoginSession(usuario, token);
                    startActivity(homeIntent);

                } else if(response.code() == 500){
                    Toast.makeText(LoginActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){
                    Toast.makeText(LoginActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                } else if(response.code() == 400){
                    Toast.makeText(LoginActivity.this, "Datos invalidos", Toast.LENGTH_LONG).show();

                }else if(response.code() == 401){
                Toast.makeText(LoginActivity.this, "Datos invalidos", Toast.LENGTH_LONG).show();

            }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

            }
        });
    }



}
