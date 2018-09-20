package bepp.com.bepp.activities.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.MisDoctoresMainActivity;
import bepp.com.bepp.models.DetailPackage;
import bepp.com.bepp.models.Doctor;
import bepp.com.bepp.models.MedicamentPrescription;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class MedicamentoRecetaPopAdapter extends BaseSwipeAdapter {

    public AlertDialog.Builder agregarPop, agregado, info;
    private Context mContext;
    List<DetailPackage> detailPackageList;


    public MedicamentoRecetaPopAdapter(Context mContext, List<DetailPackage> detailPackageList){
        this.mContext = mContext;
        this.detailPackageList = detailPackageList;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_pop_medicamento;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {


        View v = LayoutInflater.from(mContext).inflate(R.layout.item_receta_pop_medicamento, null);


        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });



        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {


        final DetailPackage detailPackage = detailPackageList.get(position);
        TextView textNombreMedicamento = (TextView) convertView.findViewById(R.id.text_pop_medicamento);

       Button btnMenos = (Button) convertView.findViewById(R.id.button_menos);
        Button btnMas = (Button) convertView.findViewById(R.id.button_mas);
        final TextView numCantidad = (TextView) convertView.findViewById(R.id.text_cantidad);






        // Populate the data into the template view using the data object
        textNombreMedicamento.setText(detailPackage.getNombre_comercial());
        numCantidad.setText(detailPackage.getCantidad());

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "clickinmenos");

                int cantidad = Integer.parseInt(numCantidad.getText().toString().trim());
                if(cantidad > 0){
                    cantidad--;
                    numCantidad.setText(String.valueOf(cantidad));
                }
            }
        });



        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidad = Integer.parseInt(numCantidad.getText().toString().trim());
                int max = Integer.parseInt(detailPackage.getCantidad());
                if(cantidad <  max  ){
                    cantidad++;
                    numCantidad.setText(String.valueOf(cantidad));
                }
            }
        });
    }


    @Override
    public int getCount() {
        return detailPackageList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailPackageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return detailPackageList.get(position).getId_producto();
    }
}




