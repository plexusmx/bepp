package bepp.com.bepp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import bepp.com.bepp.R;
import bepp.com.bepp.models.BasicResponse;
import bepp.com.bepp.models.Patient;
import bepp.com.bepp.models.RegisterUserModel;
import bepp.com.bepp.services.RegisterUserService;
import bepp.com.bepp.utils.RegisterUserUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.PatientService.PATIENT_CLIENT_RETROFIT;


public class RegistroPacienteActivity extends AppCompatActivity {

    private Button btnRegistro;
    private EditText editNombre, editApellidoP, editApellidoM, editCorreo, editMovil, editPassword, editRepassword;
    private CheckBox checkAviso;
    private TextView avisoPrivacidad;
    private TextInputLayout nombreLayout, apellidoPLayout, apellidoMLayout, correoLayout, movilLayout, passwordLayout, repasswordLayout;
    private RegisterUserService mAPIService;
    private RadioButton radioButtonMasc , radioButtonFem;
    private Vibrator vib;
    Animation animShake;
    private ImageView imageBackMenu;
    public String fotografia = "";
    public String apellidoMOpcional = "";
    public AlertDialog.Builder registerMesaje;
    boolean isChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        bindUI();

        mAPIService = RegisterUserUtil.getRetrofitService();
        registerMesaje = new AlertDialog.Builder(this);




        radioButtonFem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    radioButtonMasc.setChecked(false);
                }
            }

        });


        radioButtonMasc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    radioButtonFem.setChecked(false);
                }
            }

        });


        checkAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked = ((CheckBox)view).isChecked();
                if(isChecked) {
                    btnRegistro.setBackgroundResource(R.drawable.button_bg_rounded_corners);
                    // submitForm();
                } else {
                    btnRegistro.setBackgroundResource(R.drawable.button_bg_rounded_disable_corners);
                }

            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked){
                    submitForm();
                }
            }
        });





        editNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                editNombre.setText(editNombre.getText().toString().trim());


            }
        });

        editApellidoP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                editApellidoP.setText(editApellidoP.getText().toString().trim());


            }
        });

        editApellidoM.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                editApellidoM.setText(editApellidoM.getText().toString().trim());


            }
        });

        imageBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuRegistro = new Intent(RegistroPacienteActivity.this, RegistroMenuActivity.class);
                startActivity(menuRegistro);
            }
        });

        avisoPrivacidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMesaje.setTitle("AVISO DE PRIVACIDAD");
                registerMesaje.setMessage("Best Pharmacy Price BEPP, S.A.P.I. de C.V. , (\"BEPP\"), con domicilio en (Presa Falcón 166 Col. Irrigación 11500 Ciudad de México), en su carácter de responsable del tratamiento de su Datos Personales (según dicho término se define más adelante), pone a su disposición el presente aviso de privacidad (el \"Aviso de Privacidad\") de conformidad con la Ley Federal de Datos Personales en Posesión de los Particulares (la \"Ley\"), su Reglamento (el \"Reglamento\") y los lineamientos del Aviso de Privacidad (los \"Lineamientos\").   BEPP es una sociedad mercantil legalmente constituida de conformidad con las leyes de los Estados Unidos Mexicanos cuya actividad principal es la prestación del servicio de comunicaciones y cobro que facilita la conexión entre usuarios que buscan realizar la compra de un producto y/o medicamento a través de aplicaciones móviles o página web, con alguna de las farmacias que tenga un acuerdo con BEPP.  Sus Datos Personales serán utilizados con la finalidad de llevar a cabo operaciones inherentes a BEPP, así como cualquier actividad derivada de la relación jurídica existente o que llegare a existir entre Usted y BEPP.  En cualquier momento Usted podrá limitar el uso y divulgación de sus Datos Personales mediante el envío de un correo electrónico a la siguiente dirección: privacidad@bepp.mx.  Se hace de su conocimiento que sus Datos Personales serán salvaguardados en todo momento bajo los más altos estándares de seguridad, garantizando en todo momento, la más estricta confidencialidad y privacidad de los mismos, apegándonos en todo momento a lo establecido por la Ley, su Reglamento y los Lineamientos.  Para la prestación de sus servicios BEPP podrá solicitar los siguientes Datos Personales (los \"Datos Personales\"), a través de su aplicación o página web:  Nombre Correo electrónico Fotografía Datos Fiscales Domicilios de entrega Teléfono Celular Teléfono Fijo Cuentas Bancarias Número de Tarjeta de Crédito y/o de Débito Otras formas de pago   BEPP se compromete a no transferir, compartir ni transmitir sus datos personales a terceros sin su consentimiento previo, salvo que la transferencia se realice entre las subsidiarias de BEPP.  En caso de alguna transferencia de datos personales a algún tercero, le informamos que dicho tercero asume las mismas obligaciones y responsabilidades asumidas por BEPP en términos del presente Aviso de Privacidad.  Todos los Datos Personales que Usted proporcione a través de los diversos formularios en la aplicación o Portal de BEPP, se encuentran debidamente protegidos por servidores seguros propiedad de BEPP o de terceros profesionales contratados por BEPP bajo diversos protocolos de seguridad a efecto de evitar el acceso no autorizado a sus Datos Personales y procurar que los mismos sean estrictamente utilizados para los fines descritos en el presente Aviso Privacidad.  En virtud que BEPP presta el servicio de cobro a través de su plataforma, se le pedirán datos bancarios para los cuales BEPP ofrece seguridad y confidencialidad sobre los mismos.  Para el caso que desee revocar el consentimiento otorgado a BEPP para el tratamiento de sus Datos Personales, será necesario enviar un correo electrónico a la siguiente dirección: privacidad@bepp.mx., donde se le informará el procedimiento a seguir.   Para ejercitar los derechos de acceso, rectificación, cancelación y oposición respecto a sus Datos Personales, lo deberá realizar, enviando un correo electrónico a la siguiente dirección: privacidad@bepp.mx., adjuntando la siguiente información y documentos: i) Identificación Oficial Vigente, ii) Nombre completo del Titular, iii) Descripción clara de los datos personales sujetos al ejercicio del derecho ejercido. Asimismo, se deberá llenar el formato que le será enviado por correo electrónico. Usted tendrá una respuesta por correo electrónico, respecto a la procedencia o no del ejercicio de su derecho, dentro de los siguientes 20 días hábiles de recibido su correo. Dentro de los siguientes 15 días hábiles posteriores a la respuesta anterior, en el caso de que sea procedente su derecho, será aplicado.  BEPP podrá efectuar en cualquier momento modificaciones y/o actualizaciones al presente Aviso de Privacidad, los cuales serán publicados dentro de la Plataforma o Sitio de BEPP.  Para cualquier asunto relacionado con el presente Aviso de Privacidad, el tratamiento de sus Datos Personales, podrá enviar un correo electrónico a la siguiente dirección: privacidad@bepp.mx., con un horario de atención de (lunes a viernes de 10:00 a 14:00 hrs.).");
                registerMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.show();
            }
        });


        editNombre.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editApellidoP.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editApellidoM.setFilters(new InputFilter[]{new InputFilter.AllCaps()});


    }

    private void bindUI() {

        editNombre = (EditText) findViewById(R.id.text_paciente_nombre);
        editApellidoP = (EditText) findViewById(R.id.text_paciente_apellidoPR);
        editApellidoM = (EditText) findViewById(R.id.text_paciente_apellidoM);
        editCorreo = (EditText) findViewById(R.id.text_paciente_correo);
        editMovil = (EditText) findViewById(R.id.text_paciente_movil);
        imageBackMenu = (ImageView) findViewById(R.id.image_back_menu);

        avisoPrivacidad = (TextView) findViewById(R.id.aviso_privacidad);

        // Radios
        radioButtonMasc= (RadioButton) findViewById(R.id.radio_button_masc);
        radioButtonFem= (RadioButton) findViewById(R.id.radio_button_fem);


        editPassword = (EditText) findViewById(R.id.text_paciente_password);
        editRepassword = (EditText) findViewById(R.id.text_paciente_repassword);
        btnRegistro = (Button) findViewById(R.id.btn_registro_paciente);
        checkAviso = (CheckBox) findViewById(R.id.check_aviso_privacidad);
        

        //Layouts
        nombreLayout = (TextInputLayout) findViewById(R.id.layout_paciente_nombre);
        apellidoPLayout = (TextInputLayout) findViewById(R.id.layout_paciente_apellidoP);
        apellidoMLayout = (TextInputLayout) findViewById(R.id.layout_paciente_apellidoM);
        correoLayout = (TextInputLayout) findViewById(R.id.layout_paciente_correo);
        movilLayout = (TextInputLayout) findViewById(R.id.layout_paciente_movil);
        passwordLayout = (TextInputLayout) findViewById(R.id.layout_paciente_password);
        repasswordLayout = (TextInputLayout) findViewById(R.id.layout_paciente_repassword);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }




    private void submitForm() {

        String nombre = editNombre.getText().toString().trim();
        String apellidoP = editApellidoP.getText().toString().trim();
        String apellidoM = editApellidoM.getText().toString().trim();
        String movil = editMovil.getText().toString().trim();
        String usuario = editCorreo.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String repassword = editRepassword.getText().toString().trim();
        String sexo = "1";


        if(!radioButtonMasc.isChecked()){
            sexo = "0";
        }else {
            sexo = "1";
        }



        if (!checkNombre(nombre)) {
            editNombre.setAnimation(animShake);
            editNombre.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if (!checkApellidoP(apellidoP)) {
            editApellidoP.setAnimation(animShake);
            editApellidoP.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if (!checkApellidoM(apellidoM)) {
            editApellidoM.setAnimation(animShake);
            editApellidoM.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (!checkEmail(usuario)) {
            editCorreo.setAnimation(animShake);
            editCorreo.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (!checkMovil(movil)) {
            editMovil.setAnimation(animShake);
            editMovil.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (!checkPassword(password)) {
            editPassword.setAnimation(animShake);
            editPassword.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (!checkRePassword(password, repassword)) {
            editRepassword.setAnimation(animShake);
            editRepassword.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }


        nombreLayout.setErrorEnabled(false);
        apellidoPLayout.setErrorEnabled(false);
        apellidoMLayout.setErrorEnabled(false);
        correoLayout.setErrorEnabled(false);
        movilLayout.setErrorEnabled(false);
        passwordLayout.setErrorEnabled(false);
        repasswordLayout.setErrorEnabled(false);

        if (editApellidoM.length() > 3) {
            apellidoM = apellidoMOpcional;
        }




        Patient patient = new Patient(usuario,
                nombre,apellidoP,apellidoM,sexo, movil,password,null,null,null);

        Log.e("Paciente", patient.toString());

        sendPost(patient);

    }


    private boolean checkNombre(String nombre) {
        if (nombre.isEmpty() || !isValidField(nombre)) {

            nombreLayout.setErrorEnabled(true);
            nombreLayout.setError(getString(R.string.err_msg_nombre));
            editNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editNombre);
            return false;
        }

        if (editNombre.length() < 3) {

            nombreLayout.setErrorEnabled(true);
            nombreLayout.setError(getString(R.string.err_msg_minLengtexto));
            editNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editNombre);
            return false;
        }


        nombreLayout.setErrorEnabled(false);
        return true;
    }


    private boolean checkApellidoP(String apellidoP) {
        if (apellidoP.isEmpty() || !isValidField(apellidoP)) {

            apellidoPLayout.setErrorEnabled(true);
            apellidoPLayout.setError(getString(R.string.err_msg_apellido_paterno));
            editApellidoP.setError(getString(R.string.err_msg_required));
            requestFocus(editApellidoP);
            return false;
        }
        if (apellidoP.length() < 3) {

            apellidoPLayout.setErrorEnabled(true);
            apellidoPLayout.setError(getString(R.string.err_msg_minLengtexto));
            editApellidoP.setError(getString(R.string.err_msg_required));
            requestFocus(editApellidoP);
            return false;
        }


        apellidoPLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkApellidoM(String apellidoM) {

        if (!apellidoM.isEmpty() && !isValidField(apellidoM)) {

            apellidoMLayout.setErrorEnabled(true);
            apellidoMLayout.setError(getString(R.string.err_msg_apellidoM));
            requestFocus(editApellidoM);
            return false;
        }

        if (!apellidoM.isEmpty() && editApellidoM.length() < 3) {

            apellidoMLayout.setErrorEnabled(true);
            apellidoMLayout.setError(getString(R.string.err_msg_minLengtexto));
            requestFocus(editApellidoM);
            return false;

        }


        apellidoMLayout.setErrorEnabled(false);
        return true;
    }


    private boolean checkMovil(String movil) {
        if (movil.isEmpty() || !isValidNumberPhone(movil)) {

            movilLayout.setErrorEnabled(true);
            movilLayout.setError(getString(R.string.err_msg_movil));
            editMovil.setError(getString(R.string.err_msg_required));
            requestFocus(editMovil);
            return false;
        }
        if (editMovil.length() < 10) {

            movilLayout.setErrorEnabled(true);
            movilLayout.setError(getString(R.string.err_msg_minLengNum));
            editMovil.setError(getString(R.string.err_msg_required));
            requestFocus(editMovil);
            return false;
        }

        movilLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkEmail(String email) {
        if (email.isEmpty() || !isValidEmail(email)) {

            correoLayout.setErrorEnabled(true);
            correoLayout.setError(getString(R.string.err_msg_email));
            editCorreo.setError(getString(R.string.err_msg_required));
            requestFocus(editCorreo);
            return false;
        }

        correoLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword(String password) {


        if (password.isEmpty() || !isValidPassword(password)) {
            passwordLayout.setError("Al menos un número, una mayúscula y 6 caracteres.");
            requestFocus(editPassword);
            return false;
        }


        passwordLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkRePassword(String password, String repassword) {


        if (repassword.isEmpty()) {
            repasswordLayout.setError(getString(R.string.err_msg_password));
            requestFocus(editRepassword);
            return false;
        }
        if (!password.equals(repassword)) {
            repasswordLayout.setError(getString(R.string.err_msg_diferen_password));
            requestFocus(editRepassword);
            return false;
        }
        if (editRepassword.length() < 4) {

            repasswordLayout.setError(getString(R.string.err_msg_minLengPass));
            requestFocus(editRepassword);
            return false;
        }
        repasswordLayout.setErrorEnabled(false);
        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isValidNumberPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    private static boolean isValidField(String field) {
        return !TextUtils.isEmpty(field) && Pattern.compile("^([\\p{L}]+?[ ]?|[\\p{L}])+$").matcher(field).matches();
    }


    private static boolean isValidPassword(String field) {
        return !TextUtils.isEmpty(field) && Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$").matcher(field).matches();
    }





    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public void sendPost(Patient patient) {
        PATIENT_CLIENT_RETROFIT.createPatient(patient).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                {
                    if(response.code() == 200){

                        // Other pager
                        registerMesaje.setTitle(R.string.msg_dialog_registado);
                        registerMesaje.setMessage("Paciente exitosamente creado");
                        registerMesaje.setIcon(R.mipmap.logo_bepp_blank);
                        registerMesaje.setPositiveButton("Iniciar sesión", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent inicioSesion = new Intent(RegistroPacienteActivity.this, LoginActivity.class);
                                    startActivity(inicioSesion);
                                }
                            }
                         );
                         registerMesaje.show();



                    } else if(response.code() == 500){
                        registerMesaje.setTitle(R.string.error_msg_bandeja);
                        registerMesaje.setView(R.layout.error_registro);
                        registerMesaje.setMessage("Error en el servidor");
                        registerMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                            }
                       });
                        registerMesaje.show();
                        Toast.makeText(RegistroPacienteActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                    }else if(response.code() == 403){
                        registerMesaje.setTitle(R.string.error_msg_bandeja);
                        registerMesaje.setView(R.layout.error_registro);
                        registerMesaje.setMessage("La sesión ha expirado");
                        registerMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        registerMesaje.show();


                    }else if(response.code() == 409){
                        registerMesaje.setTitle(R.string.error_msg_ya_registado);
                        registerMesaje.setView(R.layout.error_registro);
                        registerMesaje.setMessage("El usuario ya se encuetra registrado.");
                        registerMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        registerMesaje.show();

                    }
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerMesaje.setTitle(R.string.error_msg_bandeja);
                registerMesaje.setView(R.layout.error_registro);
                registerMesaje.setMessage("Error en la conexión");
                registerMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.show();

                Log.e("Bepp",t.getMessage(),t);
            }
        });

    }
}
