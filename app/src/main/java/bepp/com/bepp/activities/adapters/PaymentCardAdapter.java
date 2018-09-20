package bepp.com.bepp.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.models.PaymentCard;

/**
 * Created by charlie on 05/10/17.
 */

public class PaymentCardAdapter extends ArrayAdapter<PaymentCard> {

     String idTarjeta;
    public PaymentCardAdapter(Context context, List<PaymentCard> paymentCards) {
        super(context, 0, paymentCards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PaymentCard paymentCard = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
       TextView textNombreUser = (TextView) convertView.findViewById(R.id.text_nombre_user);
        TextView txtTypePay = (TextView) convertView.findViewById(R.id.txt_tipo_pay);
        TextView txtNroTarjeta= (TextView) convertView.findViewById(R.id.txt_nro_tarjeta);
        TextView txtMesPay = (TextView) convertView.findViewById(R.id.txt_mes_pay);
        TextView txtAnoPay = (TextView) convertView.findViewById(R.id.txt_ano_pay);



        if(paymentCard.getTipo() == "1"){

            txtTypePay.setText("Crédito");

        } else if (paymentCard.getTipo() == "0"){
            txtTypePay.setText("Débito");
        }


        idTarjeta = paymentCard.getId_pago().toString().trim();

        // Populate the data into the template view using the data object
        textNombreUser.setText(paymentCard.getNombre_titular());
        txtNroTarjeta.setText(paymentCard.getNro_tarjeta());
       txtMesPay.setText(paymentCard.getVencimiento_mes());
        txtAnoPay.setText(paymentCard.getVencimiento_ano());

        // Return the completed view to render on screen
        return convertView;
    }
}