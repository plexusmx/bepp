package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

import bepp.com.bepp.R;
import bepp.com.bepp.models.BasicResponse;
import bepp.com.bepp.models.Login;
import bepp.com.bepp.models.Patient;
import bepp.com.bepp.services.UserService;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class ChangePasswordActivity extends AppCompatActivity {

    private ImageView cancelar, changePassword;
    private EditText editTextPasswordActual, editTextPasswordNew;
    private TextInputLayout textInputLayoutPasswordOld, textInputLayoutPasswordNew, textInputLayoutPasswordConfirm;
    private UserService mAPIService;
    private Vibrator vib;
    Animation animShake;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar principal = (Toolbar) findViewById(R.id.toolbar_change_pass);
        principal.setTitle("");
        setSupportActionBar(principal);
        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        HttpClient.setToken(user.get(UserSessionManager.TOKEN_KEY));


        bindUI();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelarIntent = new Intent(ChangePasswordActivity.this, ConfigurationActivity.class);
                startActivity(cancelarIntent);

            }
        });




        changePassword.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();

               /* if (editTextPasswordNew.toString().trim() == editTextPasswordConfirm.toString().trim()){
                    changePassword();
                }else {

                }
*/

            }
        }));

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
        cancelar = (ImageView)findViewById(R.id.imagecancelar);
        changePassword  = (ImageView)findViewById(R.id.changePassword);

        // EditText
        editTextPasswordActual = (EditText)findViewById(R.id.text_password_actual);
        editTextPasswordNew = (EditText)findViewById(R.id.text_password_nueva);


        // TextInputLayout
        textInputLayoutPasswordNew = (TextInputLayout) findViewById(R.id.layout_password_new);
         textInputLayoutPasswordConfirm = (TextInputLayout) findViewById(R.id.layout_password_confirm);

        //Others
        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }



    private void submitForm() {


        String passwdNueva = editTextPasswordNew.getText().toString().trim();
        String passwdActual = editTextPasswordActual.getText().toString().trim();

        Log.i("Pass","actual: "+passwdActual);
        Log.i("Pass","nueva: "+passwdNueva);



        if (!checkPassword(passwdNueva)) {
            editTextPasswordNew.setAnimation(animShake);
            editTextPasswordNew.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }


        if (!checkPassword(passwdActual)) {
            editTextPasswordNew.setAnimation(animShake);
            editTextPasswordNew.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }


        textInputLayoutPasswordNew.setErrorEnabled(false);
        textInputLayoutPasswordConfirm.setErrorEnabled(false);
        changePassword();


    }


    private boolean checkPassword(String password) {


        if (password.isEmpty()) {
            textInputLayoutPasswordNew.setError(getString(R.string.err_msg_password));
            requestFocus(editTextPasswordNew);
            return false;
        }
        if (password.length() < 4) {

            textInputLayoutPasswordNew.setError(getString(R.string.err_msg_minLengPass));
            requestFocus(editTextPasswordNew);
            return false;
        }

        textInputLayoutPasswordNew.setErrorEnabled(false);
        return true;
    }

    private boolean checkRePassword(String password, String repassword) {


        if (repassword.isEmpty()) {
            textInputLayoutPasswordConfirm.setError(getString(R.string.err_msg_password));
            requestFocus(editTextPasswordActual);
            return false;
        }
        if (!password.equals(repassword)) {
            textInputLayoutPasswordConfirm.setError(getString(R.string.err_msg_diferen_password));
            requestFocus(editTextPasswordActual);
            return false;
        }
        if (editTextPasswordActual.length() < 4) {

            textInputLayoutPasswordConfirm.setError(getString(R.string.err_msg_minLengPass));
            requestFocus(editTextPasswordActual);
            return false;
        }
        textInputLayoutPasswordConfirm.setErrorEnabled(false);
        return true;
    }

    private void changePassword(){

        String passwdNueva = editTextPasswordNew.getText().toString().trim();
        String passwdActual = editTextPasswordActual.getText().toString().trim();

        Log.i("Pass","actual: "+passwdActual);
        Log.i("Pass","nueva: "+passwdNueva);


        Patient patientPass = new Patient(passwdNueva, passwdActual  );


        USER_CLIENT_RETROFIT.updatePassword(patientPass).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(ChangePasswordActivity.this, "Password actualizado exitosamente", Toast.LENGTH_LONG).show();
                    Intent backDirectoryIntent = new Intent(ChangePasswordActivity.this, ConfigurationActivity.class);
                    startActivity(backDirectoryIntent);

                } else if(response.code() == 500){
                    Toast.makeText(ChangePasswordActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 401){
                    Toast.makeText(ChangePasswordActivity.this, "NO tienes permisos para cambiar el password", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){
                    Toast.makeText(ChangePasswordActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Bepp",t.getMessage(),t);

            }
        });
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
