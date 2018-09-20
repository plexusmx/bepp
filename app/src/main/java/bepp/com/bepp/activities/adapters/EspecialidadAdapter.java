package bepp.com.bepp.activities.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.AlarmMainActivity;
import bepp.com.bepp.activities.MisDoctoresMainActivity;
import bepp.com.bepp.database.AlarmaDB;
import bepp.com.bepp.modelDB.Alarma;
import bepp.com.bepp.models.Address;
import bepp.com.bepp.models.Doctor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

/**
 * Created by charlie on 14/12/17.
 */

public class EspecialidadAdapter extends BaseSwipeAdapter {

    public AlertDialog.Builder agregarPop, agregado, info;
    private Context mContext;
    List<Doctor> doctors;


    public EspecialidadAdapter(Context mContext, List<Doctor> doctors){
        this.mContext = mContext;
        this.doctors = doctors;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_especialidadDoctor;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {

        agregarPop = new AlertDialog.Builder(mContext);
        agregado = new AlertDialog.Builder(mContext);
        info = new AlertDialog.Builder(mContext);



        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_especialidad_medica, null);
        ImageView vinculo = (ImageView) v.findViewById(R.id.vincular);

        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });



        if(doctors.get(position).getVinculo().equals("0")) {
            //vinculo.setImageResource(R.mipmap.vincular_doctor);


            v.findViewById(R.id.vincular).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    agregarPop.setTitle("Agregar Médico");
                    agregarPop.setIcon(R.mipmap.logo_bepp_blank);
                    agregarPop.setMessage("¿Deseas agregarlo a tus Médicos?");
                    agregarPop.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    agregarPop.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            USER_CLIENT_RETROFIT.vincularDoctor(doctors.get(position).getId_usuario()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {


                                    agregado.setTitle("Médico agregado");
                                    agregado.setIcon(R.mipmap.logo_bepp_blank);
                                    agregado.setMessage("Se registro satisfactoriamente");
                                    agregado.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intetListaDoc = new Intent(mContext, MisDoctoresMainActivity.class);
                                                    mContext.startActivity(intetListaDoc);

                                                }
                                            });



                                            agregado.show();

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });


                        }
                    });

                    agregarPop.show();


                }
            });

        }else if (doctors.get(position).getVinculo().equals("1")) {

        vinculo.setVisibility(View.INVISIBLE);

        }

        v.findViewById(R.id.btn_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                info.setTitle("Información General");
                info.setIcon(R.mipmap.avatar_doctor);
                info.setMessage( doctors.get(position).getConoce_mas().toString());
                info.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                info.show();


            }
        });





        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {


        final Doctor doctor = doctors.get(position);
        TextView textNombreUser = (TextView) convertView.findViewById(R.id.txt_nombre_doctor);
        TextView txt_especialidad = (TextView) convertView.findViewById(R.id.txt_especialidad);
        String especialidadesText = "";




        // Populate the data into the template view using the data object
        textNombreUser.setText(doctor.getNombre());


        txt_especialidad.setText(doctor.getEspecialidad());

    }


    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Object getItem(int position) {
        return doctors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return doctors.get(position).getId_usuario();
    }
}















