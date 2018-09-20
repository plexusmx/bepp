package bepp.com.bepp.activities.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.FacturacionMainActivity;
import bepp.com.bepp.activities.LoginActivity;
import bepp.com.bepp.activities.MetodoPagoMainActivity;
import bepp.com.bepp.activities.RegistroPacienteActivity;
import bepp.com.bepp.models.PaymentCard;
import bepp.com.bepp.openpay.Openpay;
import bepp.com.bepp.utils.MyPasswordTransformationMethod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

/**
 * Created by charlie on 16/10/17.
 */

public class CardAdapter extends BaseSwipeAdapter {


    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<PaymentCard> cards;
    Openpay api;


    public CardAdapter(Context mContext, List<PaymentCard> cards) {
        this.mContext = mContext;
        this.cards = cards;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {

        registerMesaje = new AlertDialog.Builder(mContext);


        View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item_paycard, null);
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

                                USER_CLIENT_RETROFIT.deletePaymentCard(cards.get(position).getId_pago()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {


                                        if(response.code() == 200){

                                            cards.remove(cards.get(position));
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





            }
        });
        return v;

    }

    @Override
    public void fillValues(int position, View convertView) {


        PaymentCard paymentCard = cards.get(position);
        // Lookup view for data population
        TextView textNombreUser = (TextView) convertView.findViewById(R.id.text_nombre_user);
        TextView txtTypePay = (TextView) convertView.findViewById(R.id.txt_tipo_pay);
        TextView txtNroTarjeta= (TextView) convertView.findViewById(R.id.txt_nro_tarjeta);
        TextView txtMesPay = (TextView) convertView.findViewById(R.id.txt_mes_pay);
        TextView txtAnoPay = (TextView) convertView.findViewById(R.id.txt_ano_pay);

        txtNroTarjeta.setTransformationMethod(new MyPasswordTransformationMethod());








        if(paymentCard.getTipo() == "1"){

            txtTypePay.setText("Crédito");

        } else if (paymentCard.getTipo() == "0"){
            txtTypePay.setText("Débito");
        }





        // Populate the data into the template view using the data object
        textNombreUser.setText(paymentCard.getNombre_titular());
        txtNroTarjeta.setText(paymentCard.getNro_tarjeta());
        txtMesPay.setText(paymentCard.getVencimiento_mes());
        txtAnoPay.setText(paymentCard.getVencimiento_ano());


        // Return the completed view to render on screen

    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cards.get(position).getId_pago();
    }
}
