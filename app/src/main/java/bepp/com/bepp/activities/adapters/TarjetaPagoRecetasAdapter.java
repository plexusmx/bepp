package bepp.com.bepp.activities.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.models.PaymentCard;
import bepp.com.bepp.utils.MyPasswordTransformationMethod;

import static android.app.Activity.RESULT_OK;

/**
 * Created by charlie on 24/01/18.
 */

public class TarjetaPagoRecetasAdapter extends BaseSwipeAdapter {


    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<PaymentCard> paymentCards;


    public TarjetaPagoRecetasAdapter(Context mContext, List<PaymentCard> paymentCards){
        this.mContext = mContext;
        this.paymentCards = paymentCards;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_rfc_receta;
    }


    @Override
    public View generateView(final int position, ViewGroup parent) {

        registerMesaje = new AlertDialog.Builder(mContext);

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_facturacion_receta, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));


        return v;
    }

    @Override
    public void fillValues(int position, final View convertView) {

        final PaymentCard paymentCard = paymentCards.get(position);

        TextView txtNombre = (TextView) convertView.findViewById(R.id.txt_nombre_rfc_receta);
        TextView txtNumCard = (TextView) convertView.findViewById(R.id.txt_rfc_receta);
        CheckBox checkBoxSeleccion = (CheckBox) convertView.findViewById(R.id.checkBox_seleccion);

        txtNumCard.setTransformationMethod(new MyPasswordTransformationMethod());


        txtNombre.setText(paymentCard.getNombre_titular());
        txtNumCard.setText(paymentCard.getNro_tarjeta());



        checkBoxSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMessage = new Intent();

                // put the message in Intent
                intentMessage.putExtra("NombreTarjeta", paymentCard.getNombre_titular());
                intentMessage.putExtra("NumCard", paymentCard.getNro_tarjeta());
                intentMessage.putExtra("tipo", "tarjeta");


                //THESE TWO LINES NEED TO BE CHANGED
                ((Activity)mContext).setResult(RESULT_OK, intentMessage);
                ((Activity)mContext).finish();

            }
        });

    }

    @Override
    public int getCount() {
        return paymentCards.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentCards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return paymentCards.get(position).getId_pago();
    }
}

