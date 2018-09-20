package bepp.com.bepp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.commons.logging.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import bepp.com.bepp.R;
import bepp.com.bepp.models.Familiar;
import bepp.com.bepp.models.Relationship;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.CatalogService.CATALOG_CLIENT_RETROFIT;
import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;


public class AmigosFamiliaresActivity extends AppCompatActivity {

    private MaterialSpinner spinnerDirectorio;

    private List<Relationship> parentesco;
    private Button btnbusqueda;
    private boolean listaText = false;
    Animation animShake;
    private String idFamiliar;
    private ImageView imageSave, imagecancelar;
    private Vibrator vib;
    Calendar calendario = Calendar.getInstance();
    public String apellidoMOpcional = "";
    private RadioButton radioButtonMasc , radioButtonFem;
    private TextInputLayout nombreLayout, apellidoPLayout, apellidoMLayout, correoLayout, movilLayout;
    private EditText editTextFamiliarApellidoP,editTextFamiliarNombre,editTextFamiliarApellidoM,editTextFamliarBirthday,editTextFamiliarCorreo,editTextFamiliaMovil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos_familiares);

        sendGet();
        bindUI();
        radioButtonFem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    radioButtonMasc.setChecked(false);
                }
            }

        });




        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, monthOfYear);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                actualizarInput();
            }

        };


        editTextFamliarBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AmigosFamiliaresActivity.this, date, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
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

        imagecancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familiaresIntent = new Intent(AmigosFamiliaresActivity.this, AmigosFamiliaresMainActivity.class);
                startActivity(familiaresIntent);

            }
        });


        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });


        editTextFamiliarApellidoP.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextFamiliarNombre.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextFamiliarApellidoM.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextFamliarBirthday.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextFamiliarCorreo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextFamiliaMovil.setFilters(new InputFilter[]{new InputFilter.AllCaps()});



    }


    private void bindUI() {

        editTextFamiliarApellidoP = (EditText) findViewById (R.id.text_familiar_apellidoP);
        editTextFamiliarNombre = (EditText) findViewById (R.id.text_familiar_nombre);
        editTextFamiliarApellidoM = (EditText) findViewById (R.id.text_familiar_apellidoM);
        editTextFamliarBirthday = (EditText) findViewById (R.id.text_famliar_birthday);
        editTextFamiliarCorreo = (EditText) findViewById (R.id.text_familiar_correo);
        editTextFamiliaMovil = (EditText) findViewById (R.id.text_familia_movil);

        radioButtonMasc = (RadioButton) findViewById(R.id.radio_button_masc);
        radioButtonFem = (RadioButton) findViewById(R.id.radio_button_fem);


        imageSave = (ImageView) findViewById(R.id.image_save);
        imagecancelar =(ImageView) findViewById(R.id.imagecancelar);

        nombreLayout = (TextInputLayout) findViewById(R.id.layout_familiar_nombre);
        apellidoPLayout = (TextInputLayout) findViewById(R.id.layout_familiar_apellidoP);
        apellidoMLayout = (TextInputLayout) findViewById(R.id.layout_familiar_apellidoM);
        correoLayout = (TextInputLayout) findViewById(R.id.layout_familiar_correo);
        movilLayout = (TextInputLayout) findViewById(R.id.layout_familiar_movil);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



    }


    public void  sendGet() {
        CATALOG_CLIENT_RETROFIT.getRelationship().enqueue(new Callback<List<Relationship>>() {
            @Override
            public void onResponse(Call<List<Relationship>> call, Response<List<Relationship>> response) {

                if(response.code() == 200){

                    parentesco = response.body();
                    cargarLista();
                } else if(response.code() == 500){
                    Toast.makeText(AmigosFamiliaresActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 404){
                    Toast.makeText(AmigosFamiliaresActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<Relationship>> call, Throwable t) {

            }
        });



    }

    public void cargarLista( ){

        String[] parentescoArr = new String[parentesco.size()+1];
        int i = 1;
        parentescoArr[0] = "Selecciona el perentesco";

        for (Relationship parentescoItm:parentesco) {
            parentescoArr[i++]= parentescoItm.getNombre();
        }

        spinnerDirectorio = (MaterialSpinner) findViewById(R.id.spinner_parentesco);
        spinnerDirectorio.getHint();
        spinnerDirectorio.setItems(parentescoArr);



        spinnerDirectorio.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if(!item.toString().equals("Selecciona el perentesco")){

                    for (int i =0 ;i < parentesco.size(); i++ ){
                        if(parentesco.get(i).getNombre().equals(item.toString())){
                            idFamiliar = parentesco.get(i).getId_parentesco().toString().trim();
                            listaText = true;
                        } else if (item.toString().equals("Selecciona el perentesco")){

                            listaText = false;
                        }
                    }

                }
            }
        });

        spinnerDirectorio.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Selecciona el perentesco", Snackbar.LENGTH_LONG).show();
            }
        });



    }


    private void submitForm() {

        String parterno = editTextFamiliarApellidoP.getText().toString().trim();
        String nombre  = editTextFamiliarNombre.getText().toString().trim();
        String materno = editTextFamiliarApellidoM.getText().toString().trim();
        String birthday = editTextFamliarBirthday.getText().toString().trim();
        String email = editTextFamiliarCorreo.getText().toString().trim();
        String movil = editTextFamiliaMovil.getText().toString().trim();
        String sexo = "1";

        if(!radioButtonMasc.isChecked()){
            sexo = "0";
        }else {
            sexo = "1";
        }

        if (!checkNombre(nombre)) {
            editTextFamiliarNombre.setAnimation(animShake);
            editTextFamiliarNombre.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if (!checkApellidoP(parterno)) {
            editTextFamiliarApellidoP.setAnimation(animShake);
            editTextFamiliarApellidoP.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if (!checkApellidoM(materno)) {
            editTextFamiliarApellidoM.setAnimation(animShake);
            editTextFamiliarApellidoM.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if (!checkEmail(email)) {
            editTextFamiliarCorreo.setAnimation(animShake);
            editTextFamiliarCorreo.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }






        nombreLayout.setErrorEnabled(false);
        apellidoPLayout.setErrorEnabled(false);
        apellidoMLayout.setErrorEnabled(false);
        correoLayout.setErrorEnabled(false);


        if(movil.isEmpty()){
            movil = "";
        }

        if(email.isEmpty()){
            email = "";
        }

        android.util.Log.i("direccion","fecha "+ birthday);
        Familiar familiar = new Familiar(nombre,parterno,materno,sexo,birthday,movil,email,idFamiliar);





        USER_CLIENT_RETROFIT.addFamiliar(familiar).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    Intent familiaresIntent = new Intent(AmigosFamiliaresActivity.this, AmigosFamiliaresMainActivity.class);
                    startActivity(familiaresIntent);

                } else if(response.code() == 500){
                    Toast.makeText(AmigosFamiliaresActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){
                    Toast.makeText(AmigosFamiliaresActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });


    }





    private boolean checkNombre(String nombre) {
        if (nombre.isEmpty() || !isValidField(nombre)) {

            nombreLayout.setErrorEnabled(true);
            nombreLayout.setError(getString(R.string.err_msg_nombre));
            editTextFamiliarNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editTextFamiliarNombre);
            return false;
        }

        if (editTextFamiliarNombre.length() < 3) {

            nombreLayout.setErrorEnabled(true);
            nombreLayout.setError(getString(R.string.err_msg_minLengtexto));
            editTextFamiliarNombre.setError(getString(R.string.err_msg_required));
            requestFocus(editTextFamiliarNombre);
            return false;
        }


        nombreLayout.setErrorEnabled(false);
        return true;
    }


    private boolean checkApellidoP(String apellidoP) {
        if (apellidoP.isEmpty() || !isValidField(apellidoP)) {

            apellidoPLayout.setErrorEnabled(true);
            apellidoPLayout.setError(getString(R.string.err_msg_apellido_paterno));
            editTextFamiliarApellidoP.setError(getString(R.string.err_msg_required));
            requestFocus(editTextFamiliarApellidoP);
            return false;
        }
        if (apellidoP.length() < 3) {

            apellidoPLayout.setErrorEnabled(true);
            apellidoPLayout.setError(getString(R.string.err_msg_minLengtexto));
            editTextFamiliarApellidoP.setError(getString(R.string.err_msg_required));
            requestFocus(editTextFamiliarApellidoP);
            return false;
        }


        apellidoPLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkApellidoM(String apellidoM) {

        if (!apellidoM.isEmpty() && !isValidField(apellidoM)) {

            apellidoMLayout.setErrorEnabled(true);
            apellidoMLayout.setError(getString(R.string.err_msg_apellidoM));
            requestFocus(editTextFamiliarApellidoM);
            return false;
        }

        if (!apellidoM.isEmpty() && editTextFamiliarApellidoM.length() < 3) {

            apellidoMLayout.setErrorEnabled(true);
            apellidoMLayout.setError(getString(R.string.err_msg_minLengtexto));
            requestFocus(editTextFamiliarApellidoM);
            return false;

        }


        apellidoMLayout.setErrorEnabled(false);
        return true;
    }


    private boolean checkMovil(String movil) {
        if ( !isValidNumberPhone(movil)) {

            movilLayout.setErrorEnabled(true);
            movilLayout.setError(getString(R.string.err_msg_movil));
            editTextFamiliaMovil.setError(getString(R.string.err_msg_required));
            requestFocus(editTextFamiliaMovil);
            return false;
        }
        if (editTextFamiliaMovil.length() !=0  ) {

            movilLayout.setErrorEnabled(true);
            movilLayout.setError(getString(R.string.err_msg_minLengNum));
            editTextFamiliaMovil.setError(getString(R.string.err_msg_required));
            requestFocus(editTextFamiliaMovil);
            return false;
        }

        movilLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkEmail(String email) {
        if (!isValidEmail(email)) {

            correoLayout.setErrorEnabled(true);
            correoLayout.setError(getString(R.string.err_msg_email));
            editTextFamiliarCorreo.setError(getString(R.string.err_msg_required));
            requestFocus(editTextFamiliarCorreo);
            return false;
        }

        correoLayout.setErrorEnabled(false);
        return true;
    }





    private static boolean isValidEmail(String email) {
        return TextUtils.isEmpty(email) || android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isValidNumberPhone(String phone) {
        return TextUtils.isEmpty(phone) || Patterns.PHONE.matcher(phone).matches();
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

    private void actualizarInput() {
        String formatoDeFecha = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);

        editTextFamliarBirthday.setText(sdf.format(calendario.getTime()));
    }

}
