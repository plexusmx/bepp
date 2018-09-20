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
import bepp.com.bepp.models.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

/**
 * Created by charlie on 11/10/17.
 */

public class MessagesAdapter extends BaseSwipeAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<Message>  messages;


    public MessagesAdapter(Context mContext, List<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_message;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {

        registerMesaje = new AlertDialog.Builder(mContext);

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_message, null);
        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerMesaje.setTitle("Borrar forma de pago");
                registerMesaje.setMessage("¿Desea eliminar esta forma de pago?");
                registerMesaje.setIcon(R.mipmap.logo_bepp_blank);
                registerMesaje.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                USER_CLIENT_RETROFIT.deleteMessage(messages.get(position).getId_mensaje()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(mContext, "response---"+response, Toast.LENGTH_SHORT).show();


                                        if(response.code() == 200){

                                            messages.remove(messages.get(position));
                                            notifyDataSetChanged();
                                            swipeLayout.close(true);




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


                                        }



                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                        Log.e("Bepp",t.getMessage(),t);


                                    }
                                });


                            }
                        }
                );
                registerMesaje.show();





                Toast.makeText(mContext, "click delete---"+messages.get(position).getId_mensaje(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {

        Message message = messages.get(position);
         TextView txtTitulo = (TextView) convertView.findViewById(R.id.txt_titulo_mensaje);
         TextView txtFecha = (TextView) convertView.findViewById(R.id.txt_fecha);
         TextView txtContenido= (TextView) convertView.findViewById(R.id.txt_contenido);

          txtTitulo.setText(message.getTitulo());
          txtFecha.setText(message.getFecha());
          txtContenido.setText(message.getContenido());

    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).getId_mensaje();
    }
}



    /*TextView txtTitulo = (TextView) convertView.findViewById(R.id.txt_titulo_mensaje);
    TextView txtFecha = (TextView) convertView.findViewById(R.id.txt_fecha);
    TextView txtContenido= (TextView) convertView.findViewById(R.id.txt_contenido);

// Populate the data into the template view using the data object
        txtTitulo.setText(message.getTitulo());
                txtFecha.setText(message.getFecha());
                txtContenido.setText(message.getContenido());

                Message

                            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, parent, false);
*/