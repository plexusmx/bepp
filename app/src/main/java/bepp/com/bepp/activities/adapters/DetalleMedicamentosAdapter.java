package bepp.com.bepp.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.models.Medicine;

public class DetalleMedicamentosAdapter extends ArrayAdapter<Medicine> {

    String idTarjeta;

    public DetalleMedicamentosAdapter(Context context, List<Medicine> medicines) {
        super(context, 0, medicines);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Medicine medicine = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_receta_medicamentos, parent, false);
        }


        TextView textNombreMedicina = (TextView) convertView.findViewById(R.id.text_nombre_medicina);
        TextView textMedicinaHoras = (TextView) convertView.findViewById(R.id.text_medicina_horas);
        TextView textMedicinaDias = (TextView) convertView.findViewById(R.id.text_medicina_dias);
        TextView textMedcinaIndicacion = (TextView) convertView.findViewById(R.id.text_medcina_indicacion);


        // Populate the data into the template view using the data object
        textNombreMedicina.setText(medicine.getNombre_comercial());
        textMedicinaHoras.setText(medicine.getCada());
        textMedicinaDias.setText(medicine.getPor());
        textMedcinaIndicacion.setText(medicine.getObservaciones());

        // Return the completed view to render on screen
        return convertView;
    }

}