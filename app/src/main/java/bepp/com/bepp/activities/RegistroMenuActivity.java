package bepp.com.bepp.activities;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bepp.com.bepp.R;

public class RegistroMenuActivity extends AppCompatActivity {


    private Boolean pressImgPaciente = false;
    private Button mostrar;
    private ImageView paciente, imageRegBack, imagenMedico;
    private TextView iniciaSesion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_menu);


        mostrar = (Button) findViewById(R.id.btn_registro_o);
        paciente = (ImageView) findViewById(R.id.img_paciete);
        imageRegBack = (ImageView) findViewById(R.id.image_reg_back);
        iniciaSesion = (TextView) findViewById(R.id.text_iniciar_sesionR);
        imagenMedico =( ImageView) findViewById(R.id.image_medico);


        imagenMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("http://bepp.mx/index.php?llave=erick.nava@servnet.mx");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });


        mostrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pressImgPaciente) {

                        Intent pacienteRegistro = new Intent(RegistroMenuActivity.this, RegistroPacienteActivity.class);
                        startActivity(pacienteRegistro);


                    }
                }
        });

        imageRegBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseOpcion = new Intent(RegistroMenuActivity.this, ChooseActivity.class);
                startActivity(chooseOpcion);
            }

        });

        iniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciaSecionIntent = new Intent(RegistroMenuActivity.this, LoginActivity.class);
                startActivity(iniciaSecionIntent);
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

/*

 if(!pressImgPaciente) {
         imgPacienteInactivo.setImageResource(R.mipmap.icono_paciente_activo);
         pressImgPaciente = true;
         } else if (pressImgPaciente) {
         imgPacienteInactivo.setImageResource(R.mipmap.icono_paciente_inactivo);
         pressImgPaciente = false;
         }*/
