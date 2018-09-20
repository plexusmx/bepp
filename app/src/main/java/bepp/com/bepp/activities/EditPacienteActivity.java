package bepp.com.bepp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.regex.Pattern;

import bepp.com.bepp.Manifest;
import bepp.com.bepp.R;
import bepp.com.bepp.menus.HomeActivityMenu;
import bepp.com.bepp.models.BasicResponse;
import bepp.com.bepp.models.Patient;
import bepp.com.bepp.services.UserService;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;
import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT_PACIENT;

public class EditPacienteActivity extends AppCompatActivity {

    private ImageView cancelar,guardar;
    private EditText editTextNombre, editTextApellidoP, editTextApellidoM, editTextCorreo, editTextMovil;
    private TextInputLayout textInputLayoutNombre, textInputLayoutApellidoP, textInputLayoutApellidoM,textInputLayoutCorreo, textInputLayoutMovil;
    private UserService mAPIService;
    private Vibrator vib;
    Animation animShake;
    // User Session Manager Class
    UserSessionManager session;
    public String apellidoMOpcional = "";
    public static String fotografia = "";
    public AlertDialog.Builder alertMesaje;
    public Button butFoto;
    public CircleImageView profileImage;



    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_paciente);
        Toolbar principal = (Toolbar) findViewById(R.id.toolbar_edit);
        principal.setTitle("");
        setSupportActionBar(principal);

        bindUI();
        session = new UserSessionManager(getApplicationContext());
        alertMesaje = new AlertDialog.Builder(this);

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get token

        HttpClient.setToken(user.get(UserSessionManager.TOKEN_KEY));



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelarIntent = new Intent(EditPacienteActivity.this, ConfigurationActivity.class);
                startActivity(cancelarIntent);
            }
        });

        butFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });



        int permissionCheck = ContextCompat.checkSelfPermission(EditPacienteActivity.this,
                android.Manifest.permission.CAMERA);


        if (ContextCompat.checkSelfPermission(EditPacienteActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditPacienteActivity.this,
                    android.Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(EditPacienteActivity.this,
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });






        editTextNombre.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextApellidoP.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextApellidoM.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        getInfoUser();

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

        guardar = (ImageView)findViewById(R.id.guardar);

        // ImageView
        editTextNombre = (EditText)findViewById(R.id.text_paciente_nombre);
        editTextApellidoP = (EditText)findViewById(R.id.text_paciente_apellidoP);
        editTextApellidoM = (EditText)findViewById(R.id.text_paciente_apellidoM);
        editTextCorreo = (EditText)findViewById(R.id.text_paciente_correo);
        editTextMovil = (EditText)findViewById(R.id.text_paciente_movil);

        // TextInputLayout
        textInputLayoutNombre = (TextInputLayout) findViewById(R.id.layout_paciente_nombre);
        textInputLayoutApellidoP = (TextInputLayout) findViewById(R.id.layout_paciente_apellidoP);
        textInputLayoutApellidoM = (TextInputLayout) findViewById(R.id.layout_paciente_apellidoM);
        textInputLayoutCorreo = (TextInputLayout) findViewById(R.id.layout_paciente_correo);
        textInputLayoutMovil = (TextInputLayout) findViewById(R.id.layout_paciente_movil);

        // Others
        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        butFoto = (Button) findViewById(R.id.button_tomar_foto);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
    }

    private void submitForm() {


        String nombre = editTextNombre.getText().toString().trim();
        String apellidoP = editTextApellidoP.getText().toString().trim();
        String apellidoM = editTextApellidoM.getText().toString().trim();
        String movil = editTextMovil.getText().toString().trim();
        String usuario = editTextCorreo.getText().toString().trim();



        if (!checkNombre(nombre)) {
            editTextNombre.setAnimation(animShake);
            editTextNombre.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }


        if (!checkApellidoP(apellidoP)) {
            editTextApellidoP.setAnimation(animShake);
            editTextApellidoP.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (!checkApellidoM(apellidoM)) {
            editTextApellidoM.setAnimation(animShake);
            editTextApellidoM.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (!checkMovil(movil)) {
            editTextMovil.setAnimation(animShake);
            editTextMovil.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (!checkEmail(usuario)) {
            editTextCorreo.setAnimation(animShake);
            editTextCorreo.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (editTextApellidoM.length() < 3) {
            apellidoM = apellidoMOpcional;
        }


        textInputLayoutNombre.setErrorEnabled(false);
        textInputLayoutApellidoP.setErrorEnabled(false);
        textInputLayoutApellidoM.setErrorEnabled(false);
        textInputLayoutCorreo.setErrorEnabled(false);
        textInputLayoutMovil.setErrorEnabled(false);







        Patient patient = new Patient(usuario, nombre,  apellidoP,  apellidoM, null,  movil,  null,  fotografia,  null,  null);
        sendPost(patient);

        Log.e("Paciente", "objeot : "+ patient.getApellidom());
        Log.i("Test", "entro01");

    }

    public void sendPost( Patient patient){
        USER_CLIENT_RETROFIT_PACIENT.updatePatient(patient).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Test", "entro02");
                Log.i("Test", "code: "+response.code());


                if(response.code() == 200){
                    Log.i("Test", "entro03");


                    alertMesaje.setTitle("Edición de perfil");
                    alertMesaje.setMessage("Paciente actualizado");
                    alertMesaje.setIcon(R.mipmap.logo_bepp_blank);
                    alertMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent backDirectoryIntent = new Intent(EditPacienteActivity.this, ConfigurationActivity.class);
                                    startActivity(backDirectoryIntent);
                                }
                            }
                    );
                    alertMesaje.show();





                } else if(response.code() == 500){
                    alertMesaje.setTitle(R.string.error_msg_bandeja);
                    alertMesaje.setView(R.layout.error_registro);
                    alertMesaje.setMessage("Error en el servidor");
                    alertMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertMesaje.show();

                }else if(response.code() == 401){
                    alertMesaje.setTitle(R.string.error_msg_bandeja);
                    alertMesaje.setView(R.layout.error_registro);
                    alertMesaje.setMessage("La sesión ha expirado");
                    alertMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertMesaje.show();




                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                alertMesaje.setTitle(R.string.error_msg_bandeja);
                alertMesaje.setView(R.layout.error_registro);
                alertMesaje.setMessage("Error en la conexión");
                alertMesaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertMesaje.show();

                Log.e("Bepp",t.getMessage(),t);
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

    private boolean checkApellidoP(String apellidoP) {
        if (apellidoP.isEmpty() || !isValidField(apellidoP)) {

            textInputLayoutApellidoP.setErrorEnabled(true);
            textInputLayoutApellidoP.setError(getString(R.string.err_msg_apellido_paterno));
            editTextApellidoP.setError(getString(R.string.err_msg_required));
            requestFocus(editTextApellidoP);
            return false;
        }
        if (apellidoP.length() < 3) {

            textInputLayoutApellidoP.setErrorEnabled(true);
            textInputLayoutApellidoP.setError(getString(R.string.err_msg_minLengtexto));
            editTextApellidoP.setError(getString(R.string.err_msg_required));
            requestFocus(editTextApellidoP);
            return false;
        }


        textInputLayoutApellidoP.setErrorEnabled(false);
        return true;
    }



    private boolean checkApellidoM(String apellidoM) {

        if (!apellidoM.isEmpty() && !isValidField(apellidoM)) {

            textInputLayoutApellidoM.setErrorEnabled(true);
            textInputLayoutApellidoM.setError(getString(R.string.err_msg_apellidoM));
            requestFocus(editTextApellidoM);
            return false;
        }

        if (!apellidoM.isEmpty() && editTextApellidoM.length() < 3) {

            textInputLayoutApellidoM.setErrorEnabled(true);
            textInputLayoutApellidoM.setError(getString(R.string.err_msg_minLengtexto));
            requestFocus(editTextApellidoM);
            return false;

        }


        textInputLayoutApellidoM.setErrorEnabled(false);
        return true;
    }

    private boolean checkMovil(String movil) {
        if (movil.isEmpty() || !isValidNumberPhone(movil)) {

            textInputLayoutMovil.setErrorEnabled(true);
            textInputLayoutMovil.setError(getString(R.string.err_msg_movil));
            editTextMovil.setError(getString(R.string.err_msg_required));
            requestFocus(editTextMovil);
            return false;
        }
        if (editTextMovil.length() < 10) {

            textInputLayoutMovil.setErrorEnabled(true);
            textInputLayoutMovil.setError(getString(R.string.err_msg_minLengNum));
            editTextMovil.setError(getString(R.string.err_msg_required));
            requestFocus(editTextMovil);
            return false;
        }

        textInputLayoutMovil.setErrorEnabled(false);
        return true;
    }


    private boolean checkEmail(String email) {
        if (email.isEmpty() || !isValidEmail(email)) {

            textInputLayoutCorreo.setErrorEnabled(true);
            textInputLayoutCorreo.setError(getString(R.string.err_msg_email));
            editTextCorreo.setError(getString(R.string.err_msg_required));
            requestFocus(editTextCorreo);
            return false;
        }

        textInputLayoutCorreo.setErrorEnabled(false);
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(imageBitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            fotografia = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(this, ""+ fotografia, Toast.LENGTH_LONG).show();




        }
    }





    public  void getInfoUser(){
        USER_CLIENT_RETROFIT.generalInfo().enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {

                Log.i("Test", "entro04");

                if(response.code() == 200){
                    Log.i("Test", "entro06");

                    // Toast.makeText(HomeActivityMenu.this, "Entro", Toast.LENGTH_LONG).show();
                    response.body();

                    Log.e("bepp",  "Objeto "+response.body().getApellidom());
                    Log.e("bepp",  "Objeto "+response.body().getApellidop());


                    editTextNombre.setText(response.body().getNombre());
                    editTextApellidoP.setText(response.body().getApellidop());
                    editTextApellidoM.setText(response.body().getApellidom());
                    editTextCorreo.setText(response.body().getUsuario());
                    editTextMovil.setText(response.body().getTel_movil());





                } else if(response.code() == 403){
                    Toast.makeText(EditPacienteActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 404){
                    Toast.makeText(EditPacienteActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 500){
                    Toast.makeText(EditPacienteActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });


    }






}
