package bepp.com.bepp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import bepp.com.bepp.R;

public class ConfigurationActivity extends AppCompatActivity {

    private ImageView btnEdit,btnPassword,btnCorreo,btnMetodoPago, btnLealtad,btnPago, btnDireccion, btnFacturacion;
    private LinearLayout layoutEditarPerfil, layoutPago, layoutDireccion, layoutFacturacion , layoutLealtad ,layoutDatosCuenta,  layoutContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        bindUI();


        layoutEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdicionPaciente = new Intent(ConfigurationActivity.this, EditPacienteActivity.class);
                startActivity(intentEdicionPaciente);
            }
        });


        layoutContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePass = new Intent(ConfigurationActivity.this, ChangePasswordActivity.class);
                startActivity(intentChangePass);
            }
        });

        layoutDatosCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditEmail = new Intent(ConfigurationActivity.this, EditEmailActivity.class);
                startActivity(intentEditEmail);
            }
        });


        layoutPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditPago = new Intent(ConfigurationActivity.this, MetodoPagoMainActivity.class);
                startActivity(intentEditPago);
            }
        });

      /*  layoutLealtad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCodigoBarras = new Intent(ConfigurationActivity.this, CodigoBarrasActivity.class);
                startActivity(intentCodigoBarras);
            }
        });*/

        layoutDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDireccion = new Intent(ConfigurationActivity.this, DireccionEnvioMainActivity.class);
                startActivity(intentDireccion);
            }
        });

        layoutFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFacturacion = new Intent(ConfigurationActivity.this, FacturacionMainActivity.class);
                startActivity(intentFacturacion);
            }
        });
    }


    private void bindUI() {

        btnEdit = (ImageView) findViewById(R.id.text_editar_perfil);
        btnCorreo= (ImageView) findViewById(R.id.img_correo);
       //btnLealtad = (ImageView) findViewById(R.id.img_lealtad);
       btnPago = (ImageView) findViewById(R.id.img_pago);
        btnDireccion = (ImageView) findViewById(R.id.img_direccion);
        btnFacturacion = (ImageView) findViewById(R.id.img_facturacion);


        layoutEditarPerfil = (LinearLayout) findViewById(R.id.layout_editar_perfil);
        layoutPago  = (LinearLayout) findViewById(R.id.layout_pago);
        layoutDireccion = (LinearLayout) findViewById(R.id.layout_direccion);
        layoutFacturacion = (LinearLayout) findViewById(R.id.layout_facturacion);
        //layoutLealtad = (LinearLayout) findViewById(R.id.layout_lealtad);
        layoutDatosCuenta = (LinearLayout) findViewById(R.id.layout_datos_cuenta);
        layoutContraseña = (LinearLayout) findViewById(R.id.layout_contraseña);


    }
}
