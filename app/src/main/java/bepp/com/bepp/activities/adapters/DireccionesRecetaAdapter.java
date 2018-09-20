package bepp.com.bepp.activities.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.LugarEnvioActivity;
import bepp.com.bepp.activities.MedicinasRecetasActivity;
import bepp.com.bepp.models.Address;

import static android.app.Activity.RESULT_OK;

/**
 * Created by charlie on 18/01/18.
 */

public class DireccionesRecetaAdapter  extends BaseSwipeAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<Address> addresses;
    int id_receta;


    public DireccionesRecetaAdapter(Context mContext, List<Address> addresses, int id_receta){
        this.mContext = mContext;
        this.addresses = addresses;
        this.id_receta = id_receta;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_direccion_receta;
    }


    @Override
    public View generateView(final int position, ViewGroup parent) {

        registerMesaje = new AlertDialog.Builder(mContext);

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_direcciones_receta, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));


        return v;
    }

    @Override
    public void fillValues(int position, final View convertView) {

        final Address address = addresses.get(position);

        final TextView txtNombreDireccion = (TextView) convertView.findViewById(R.id.txt_nombre_direccion_receta);
        TextView txtCalle = (TextView) convertView.findViewById(R.id.txt_calle_receta);
        TextView txtNumExt = (TextView) convertView.findViewById(R.id.txt_num_ext_receta);
        final CheckBox checkBoxSeleccion = (CheckBox) convertView.findViewById(R.id.checkBox_seleccion);


        txtNombreDireccion.setText(address.getNombre());
        txtCalle.setText(address.getCalle());
        txtNumExt.setText(address.getNroext());



        checkBoxSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intentMessage = new Intent();
                // put the message in Intent
                intentMessage.putExtra("Nombre", address.getNombre());
                intentMessage.putExtra("Calle", address.getCalle());
                intentMessage.putExtra("Numero", address.getNroext());
                //THESE TWO LINES NEED TO BE CHANGED
                ((Activity)mContext).setResult(RESULT_OK, intentMessage);
                ((Activity)mContext).finish();*/

                if(checkBoxSeleccion.isChecked()){


                registerMesaje.setTitle("Selección de envio");
                registerMesaje.setMessage("¿Desea elegir esta dirección de envio?");
                registerMesaje.setIcon(R.mipmap.logo_bepp_blank);
                registerMesaje.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkBoxSeleccion.setChecked(false);
                        dialog.dismiss();
                    }
                });
                registerMesaje.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // SERVICIO DE ENVIO DE DIRECCION
                                Log.i("id_receta", "3: "+id_receta);

                                Intent intentFarmacias = new Intent(mContext, MedicinasRecetasActivity.class);
                                intentFarmacias.putExtra("id_receta", id_receta);
                                mContext.startActivity(intentFarmacias);


                            }
                        }
                );
                registerMesaje.show();


                }

            }
        });

    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return addresses.get(position).getId_direccion();
    }
}

