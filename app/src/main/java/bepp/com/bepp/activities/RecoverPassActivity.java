package bepp.com.bepp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bepp.com.bepp.R;
import bepp.com.bepp.menus.HomeActivityMenu;
import bepp.com.bepp.models.BasicResponse;
import bepp.com.bepp.models.Login;
import bepp.com.bepp.models.RecoverPassModel;
import bepp.com.bepp.models.ResponseRecoverPassModel;
import bepp.com.bepp.services.RecoverPassService;
import bepp.com.bepp.utils.RecoverPassUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;


public class RecoverPassActivity extends AppCompatActivity {

    private RecoverPassService mAPIService;
    private Button btnRecuperar;
    private EditText text_correo;
    private TextInputLayout text_correo_layout;
    public int tipo = 4;
    private Vibrator vib;
    Animation animShake;
    public AlertDialog.Builder recoverMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrablecer_pass);


        bindUI();
        mAPIService = RecoverPassUtil.getRetrofitService();
        recoverMessage = new AlertDialog.Builder(this);


        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEmail();
            }
        });
    }


    private void bindUI() {

        btnRecuperar = (Button) findViewById(R.id.btn_recuperar);
        text_correo = (EditText) findViewById(R.id.text_correo);
        text_correo_layout = (TextInputLayout) findViewById(R.id.text_correo_layout);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }

    private void submitEmail() {

        String usuario = text_correo.getText().toString().trim();


        if (!checkEmail()) {
            text_correo.setAnimation(animShake);
            text_correo.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        text_correo_layout.setErrorEnabled(false);


        Login recoverPass = new Login(usuario, null, tipo, null);
        sendPostRecover(recoverPass);

    }


    private boolean checkEmail() {
        String email = text_correo.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {

            text_correo_layout.setErrorEnabled(true);
            text_correo_layout.setError(getString(R.string.err_msg_email));
            text_correo.setError(getString(R.string.err_msg_required));
            requestFocus(text_correo);
            return false;
        }


        text_correo_layout.setErrorEnabled(false);
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


    public void sendPostRecover(Login recoverPass) {
        USER_CLIENT_RETROFIT.recoverPassword(recoverPass).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(RecoverPassActivity.this, "codigo: "+response.code(), Toast.LENGTH_LONG).show();

                if (response.code() == 200) {

                    recoverMessage.setTitle(R.string.msg_dialog_titulo_bandeja);
                    recoverMessage.setView(R.layout.recover_message_pop);
                    recoverMessage.setMessage(R.string.msg_dialog_bandeja);
                    recoverMessage.setMessage("Te hemos enviado un email con tu nueva contraseña.");
                    recoverMessage.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    recoverMessage.show();

                } else if (response.code() == 500) {

                    recoverMessage.setTitle(R.string.error_msg_bandeja);
                    recoverMessage.setView(R.layout.error_registro);
                    recoverMessage.setMessage("Error en el servidor");
                    recoverMessage.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    recoverMessage.show();

                }
                else if (response.code() == 401) {

                    recoverMessage.setTitle(R.string.error_msg_bandeja);
                    recoverMessage.setView(R.layout.error_registro);
                    recoverMessage.setMessage("Correo no registrado");
                    recoverMessage.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    recoverMessage.show();

                } else if (response.code() == 400) {

                    recoverMessage.setTitle(R.string.error_msg_bandeja);
                    recoverMessage.setView(R.layout.error_registro);
                    recoverMessage.setMessage("Correo no registrado");
                    recoverMessage.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    recoverMessage.show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
}


