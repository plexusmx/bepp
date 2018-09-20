package bepp.com.bepp.menus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

import java.util.HashMap;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.AlarmMainActivity;
import bepp.com.bepp.activities.AmigosFamiliaresMainActivity;
import bepp.com.bepp.activities.AtencionClienteActivity;
import bepp.com.bepp.activities.ConfigurationActivity;
import bepp.com.bepp.activities.DirectorioMedicoActivity;
import bepp.com.bepp.activities.HistorialClinicoMainActivity;
import bepp.com.bepp.activities.HistorialCompraMainActivity;
import bepp.com.bepp.activities.MessagesActivity;
import bepp.com.bepp.activities.MisDoctoresMainActivity;
import bepp.com.bepp.activities.RecetaMainActivity;
import bepp.com.bepp.models.EmptyRequest;
import bepp.com.bepp.models.Patient;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView escanearCodigo, textCorreoMenu, textNombreUser,textNombreHomeUser;

    private ImageButton imageButtonConfiguraciones, imageButtonDoctores, imageButtonRecetas, imageButtonHistorialCompra, imageButtonHistorialclinico,imageButtonTratamiento,imageButtonFamiliares;
    private ImageView imgNotificacion, imgCarrito, imageFoto, imageDirectorio;
    private CircleImageView fotoImageProFILE;


    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());


        NavigationView menuTutulos = (NavigationView) findViewById(R.id.nav_view);
        View v = menuTutulos.getHeaderView(0);
        textCorreoMenu = (TextView) v.findViewById(R.id.text_correo_menu);

        textNombreUser = (TextView) v.findViewById(R.id.text_nombre_menu);
        textNombreHomeUser= (TextView) findViewById(R.id.text_nombre_user);


        imageButtonConfiguraciones = (ImageButton) findViewById(R.id.imageButtonConfiguraciones);
        imageDirectorio = (ImageButton) findViewById(R.id.image_directorio);



        imgNotificacion = (ImageView) findViewById(R.id.img_notificacion);
        imgCarrito = (ImageView) findViewById(R.id.img_carrito);

        imageButtonDoctores = (ImageButton) findViewById(R.id.imageButtonDoctores);


        imageButtonRecetas = (ImageButton) findViewById(R.id.imageButtonRecetas);


        imageButtonHistorialCompra = (ImageButton) findViewById(R.id.imageButtonHistorialCompra);

        imageButtonFamiliares= (ImageButton) findViewById(R.id.imageButtonFamiliares);
        imageButtonHistorialclinico = (ImageButton)  findViewById(R.id.image_button_historialclinico);

        imageButtonTratamiento = (ImageButton)  findViewById(R.id.imageButtonTratamiento);

        fotoImageProFILE = (CircleImageView) v.findViewById(R.id.image_foto);





        imageButtonTratamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTratamientos = new Intent(HomeActivityMenu.this, AlarmMainActivity.class);
                startActivity(intentTratamientos);
            }
        });






        imageButtonConfiguraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentConfiguracion = new Intent(HomeActivityMenu.this, ConfigurationActivity.class);
                startActivity(intentConfiguracion);
            }
        });

        imageButtonDoctores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMisDoc = new Intent(HomeActivityMenu.this, MisDoctoresMainActivity.class);
                startActivity(intentMisDoc);
            }
        });


        imgNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mensajeIntent = new Intent(HomeActivityMenu.this, MessagesActivity.class);
                startActivity(mensajeIntent);

            }
        });

        imageButtonFamiliares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recetasMainIntent = new Intent(HomeActivityMenu.this, AmigosFamiliaresMainActivity.class);
                startActivity(recetasMainIntent);
            }
        });



        imageButtonRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recetasMainIntent = new Intent(HomeActivityMenu.this, RecetaMainActivity.class);
                startActivity(recetasMainIntent);
            }
        });


        imageButtonHistorialCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historialMainIntent = new Intent(HomeActivityMenu.this, HistorialCompraMainActivity.class);
                startActivity(historialMainIntent);
            }
        });


        imageButtonHistorialclinico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historialclinicoMainIntent = new Intent(HomeActivityMenu.this, HistorialClinicoMainActivity.class);
                startActivity(historialclinicoMainIntent);
            }
        });

        imageDirectorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDirecotioMed = new Intent(HomeActivityMenu.this, DirectorioMedicoActivity.class);
                startActivity(intentDirecotioMed);
            }
        });









        // textNombreUser = (TextView) findViewById(R.id.text_nombre_user);
        // textNombreUser.setText("ssdsds");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        if(session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        // String name = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);


        // get token
        String token = user.get(UserSessionManager.TOKEN_KEY);


        HttpClient.setToken(user.get(UserSessionManager.TOKEN_KEY));


        getInfoUser();








    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
        } else if (id == R.id.nav_receta) {

            Intent recetasMainIntent = new Intent(HomeActivityMenu.this, RecetaMainActivity.class);
            startActivity(recetasMainIntent);

        } else if (id == R.id.nav_tratamiento) {
            Intent intentTratamientos = new Intent(HomeActivityMenu.this, AlarmMainActivity.class);
            startActivity(intentTratamientos);


        } else if (id == R.id.nav_historial) {
            Intent intentMisHis = new Intent(HomeActivityMenu.this, HistorialClinicoMainActivity.class);
            startActivity(intentMisHis);


        } else if (id == R.id.nav_familiares) {
            Intent intentFamiliares = new Intent(HomeActivityMenu.this, AmigosFamiliaresMainActivity.class);
            startActivity(intentFamiliares);


        } else if (id == R.id.nav_doctores) {

            Intent intentMisDoc = new Intent(HomeActivityMenu.this, MisDoctoresMainActivity.class);
            startActivity(intentMisDoc);


        }else if (id == R.id.nav_dir_medico) {

            Intent intentDirecotioMed = new Intent(this, DirectorioMedicoActivity.class);
            startActivity(intentDirecotioMed);

        }else if (id == R.id.nav_pagos) {

        }else if (id == R.id.nav_tienda) {

        }else if (id == R.id.nav_configuracion) {

            Intent intentConfiguracion = new Intent(this, ConfigurationActivity.class);
            startActivity(intentConfiguracion);


        }else if (id == R.id.nav_atencion) {
            Intent intentAtencion = new Intent(this, AtencionClienteActivity.class);
            startActivity(intentAtencion);

        }else if (id == R.id.nav_cerrar_session) {




            sendPost();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void sendPost(){
        USER_CLIENT_RETROFIT.logout(EmptyRequest.INSTANCE).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
               // Toast.makeText(HomeActivityMenu.this, " Hasta Luego" + response.code(), Toast.LENGTH_LONG).show();


                if(response.code() == 200){
                    session.logoutUser();

                } else if(response.code() == 500){
                    Toast.makeText(HomeActivityMenu.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(HomeActivityMenu.this, "Fallido ", Toast.LENGTH_LONG).show();

            }
        });

    }

    public  void getInfoUser(){
        USER_CLIENT_RETROFIT.generalInfo().enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {

                if(response.code() == 200){
                   // Toast.makeText(HomeActivityMenu.this, "Entro", Toast.LENGTH_LONG).show();
                    response.body();

                    String nombreApellido = response.body().getNombre() + " " +response.body().getApellidop();
                    textCorreoMenu.setText(response.body().getUsuario());

                    textNombreUser.setText(nombreApellido);
                    textNombreHomeUser.setText("¡Hola " +response.body().getNombre() +"!");
                    byte[] decodedString = Base64.decode(response.body().getFotografia(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    if(decodedByte != null)
                    fotoImageProFILE.setImageBitmap(decodedByte);




                } else if(response.code() == 403){
                    Toast.makeText(HomeActivityMenu.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 404){
                    Toast.makeText(HomeActivityMenu.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 500){
                    Toast.makeText(HomeActivityMenu.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });


    }



}
