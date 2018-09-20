package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.FarmacyAdapter;
import bepp.com.bepp.models.Farmacy;
import bepp.com.bepp.models.PackageSend;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT_PACIENT;

/**
 * Created by charlie on 30/01/18.
 */

public class TapGenerales extends Fragment {

    private static  final String TAG = "TapFacturas";
    private TextView ver_oferta_generales ,text_cantidades_medicamentos,txtclose;
    private TextView numCantidad, numCantidad2;
    private Button btnMenos, btnMas,btnMenos2, btnMas2;
    private ExpandableLayout expandableLayout0, expandableLayoutEstudios;
    public LinearLayout linear_productos_compra, linearLayoutMedicamentos, linearLayoutEstudios;
    private List<Farmacy> farmacyList;

    private FarmacyAdapter mAdapter;
    private List<PackageSend> packageSendList;
    private ListView mListView;
    private Context mContext;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.layout_generales, container, false);
        mListView = (ListView) view.findViewById(R.id.list_farmacias);



        final int id_receta = ((MedicinasRecetasActivity) getActivity()).id_receta;
        Log.i("id_receta", "6 : "+id_receta);


        USER_CLIENT_RETROFIT_PACIENT.infoFarmaciaReceta(id_receta).enqueue(new Callback<List<PackageSend>>() {
            @Override
            public void onResponse(Call<List<PackageSend>> call, Response<List<PackageSend>> response) {



                if(response.code() == 200){

                    packageSendList =  response.body();
                    for (PackageSend pack: packageSendList
                            ) {
                        Log.i("BEPP", "tarjeta-->"+pack.toString());
                    }

                    mAdapter = new FarmacyAdapter( getContext(), packageSendList, id_receta);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if(response.code() == 500){
                    Toast.makeText( getContext(), "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText( getContext(), "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<PackageSend>> call, Throwable t) {

            }
        });




        return view;

    }

}
