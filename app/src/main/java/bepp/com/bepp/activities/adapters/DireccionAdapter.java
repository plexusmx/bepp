package bepp.com.bepp.activities.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.models.Address;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

/**
 * Created by charlie on 12/10/17.
 */

public class DireccionAdapter  extends BaseSwipeAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<Address> addresses;


    public DireccionAdapter(Context mContext, List<Address> addresses){
        this.mContext = mContext;
        this.addresses = addresses;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_address;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {

        registerMesaje = new AlertDialog.Builder(mContext);

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_address, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerMesaje.setTitle("Borrar direccion ");
                registerMesaje.setMessage("¿Desea eliminar esta direccion ?");
                registerMesaje.setIcon(R.mipmap.logo_bepp_blank);
                registerMesaje.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                USER_CLIENT_RETROFIT.deleteAddress(addresses.get(position).getId_direccion()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(mContext, "response---" + response, Toast.LENGTH_SHORT).show();


                                        if (response.code() == 200) {

                                            addresses.remove(addresses.get(position));
                                            notifyDataSetChanged();
                                            swipeLayout.close(true);


                                        } else if (response.code() == 500) {
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

                                        } else if (response.code() == 403) {
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


                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                        Log.e("Bepp", t.getMessage(), t);


                                    }
                                });


                            }
                        }
                );
                registerMesaje.show();


                Toast.makeText(mContext, "click delete---" + addresses.get(position).getId_direccion(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {

        Address address = addresses.get(position);

        TextView txtNombreDireccion = (TextView) convertView.findViewById(R.id.txt_nombre_direccion);
        TextView txtCalle = (TextView) convertView.findViewById(R.id.txt_calle);
        TextView txtNumExt = (TextView) convertView.findViewById(R.id.txt_num_ext);
        TextView txtNumInterior = (TextView) convertView.findViewById(R.id.txt_num_interior);
        TextView txtColonia = (TextView) convertView.findViewById(R.id.txt_colonia);
        TextView txtDelegacion = (TextView) convertView.findViewById(R.id.txt_delegacion);
        TextView txtEstado = (TextView) convertView.findViewById(R.id.txt_estado);
        TextView txtPais = (TextView) convertView.findViewById(R.id.txt_pais);

        txtNombreDireccion.setText(address.getNombre());
        txtCalle.setText(address.getCalle());
        txtNumExt.setText(address.getNroext());
        txtNumInterior.setText(address.getNorint());
        txtColonia.setText(address.getColonia());
        txtDelegacion.setText(address.getDelegacion());
        txtEstado.setText(address.getEstado());
        txtPais.setText(address.getPais());


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



/*
public class DireccionAdapter  extends ArrayAdapter<Address>{
    public DireccionAdapter( Context context, List<Address> addresses) {
        super(context, 0, addresses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Address address = getItem(position);

        if(convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_address, parent, false);
        }

        TextView txtNombreDireccion = (TextView) convertView.findViewById(R.id.txt_nombre_direccion);
        TextView txtCalle = (TextView) convertView.findViewById(R.id.txt_calle);
        TextView txtNumExt = (TextView) convertView.findViewById(R.id.txt_num_ext);
        TextView txtNumInterior = (TextView) convertView.findViewById(R.id.txt_num_interior);
        TextView txtColonia = (TextView) convertView.findViewById(R.id.txt_colonia);
        TextView txtDelegacion = (TextView) convertView.findViewById(R.id.txt_delegacion);
        TextView txtEstado = (TextView) convertView.findViewById(R.id.txt_estado);
        TextView txtPais = (TextView) convertView.findViewById(R.id.txt_pais);

        txtNombreDireccion.setText(address.getNombre());
        txtCalle.setText(address.getCalle());
        txtNumExt.setText(address.getNroext());
        txtNumInterior.setText(address.getNorint());
        txtColonia.setText(address.getColonia());
        txtDelegacion.setText(address.getDelegacion());
        txtEstado.setText(address.getEstado());
        txtPais.setText(address.getPais());

        return convertView;
    }
}
*/
