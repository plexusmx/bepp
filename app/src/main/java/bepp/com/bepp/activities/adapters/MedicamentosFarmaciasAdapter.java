package bepp.com.bepp.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.models.DetailPackage;

public class MedicamentosFarmaciasAdapter  extends ArrayAdapter<DetailPackage> {



    public MedicamentosFarmaciasAdapter(Context context, List<DetailPackage> detailPackageList) {
        super(context, 0, detailPackageList);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DetailPackage detailPackage = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_medicina_famacias, parent, false);
        }
        // Lookup view for data population
        TextView textNombreMedicamento = (TextView) convertView.findViewById(R.id.text_medicamento);
        TextView textCosto = (TextView) convertView.findViewById(R.id.text_precio);






        textNombreMedicamento.setText(detailPackage.getNombre_comercial());
        textCosto.setText(detailPackage.getPrecio_producto());

        // Return the completed view to render on screen
        return convertView;
    }
}


