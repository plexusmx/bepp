package bepp.com.bepp.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import bepp.com.bepp.R;

public class LoginOpcionActivity extends AppCompatActivity {


    private Boolean pressImgPaciente = false;
    private Button mostrar;
    private ImageView paciente, imageRegBack, imagenMedico;
    private TextView registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_opcion);


        mostrar = (Button) findViewById(R.id.btn_registro_o);
        paciente = (ImageView) findViewById(R.id.img_paciete);
        imageRegBack = (ImageView) findViewById(R.id.image_reg_back);
        registro = (TextView) findViewById(R.id.text_regitro_sesionR);
        imagenMedico = (ImageView)  findViewById(R.id.imagen_medico);

        imagenMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("http://bepp.mx/loginMedico.php");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });






        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pressImgPaciente) {

                    Intent loginIntent = new Intent(LoginOpcionActivity.this, LoginActivity.class);
                    startActivity(loginIntent);


                }
            }
        });

        imageRegBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseOpcion = new Intent(LoginOpcionActivity.this, ChooseActivity.class);
                startActivity(chooseOpcion);
            }

        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registarIntent = new Intent(LoginOpcionActivity.this, RegistroMenuActivity.class);
                startActivity(registarIntent);
            }
        });


        paciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pressImgPaciente) {
                    paciente.setImageResource(R.mipmap.icono_paciente_activo);
                    mostrar.setBackgroundResource(R.drawable.button_bg_rounded_corners);
                    pressImgPaciente = true;
                } else if (pressImgPaciente) {
                    paciente.setImageResource(R.mipmap.icono_paciente_inactivo);
                    mostrar.setBackgroundResource(R.drawable.button_bg_rounded_disable_corners);
                    pressImgPaciente = false;
                }

            }
        });



    }



}