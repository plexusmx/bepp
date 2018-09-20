package bepp.com.bepp.activities.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.EditFacturacionActivity;
import bepp.com.bepp.activities.FacturacionMainActivity;
import bepp.com.bepp.models.FiscalData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

/**
 * Created by charlie on 12/10/17.
 */

public class FiscalDataAdapter extends BaseSwipeAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<FiscalData> fiscalDatas;
    private ListView mListView;




    public FiscalDataAdapter(Context mContext, List<FiscalData> fiscalDatas ,ListView mListView) {
        this.mContext = mContext;
        this.fiscalDatas = fiscalDatas;
        this.mListView = mListView;
    }



    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_fiscal;

    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        registerMesaje = new AlertDialog.Builder(mContext);

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_fiscal_data, null);
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


                registerMesaje.setTitle("Borrar datos fiscales");
                registerMesaje.setMessage("¿Desea eliminar datos fiscales?");
                registerMesaje.setIcon(R.mipmap.logo_bepp_blank);
                registerMesaje.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                USER_CLIENT_RETROFIT.deleteFiscalData(fiscalDatas.get(position).getId_dato()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(mContext, "response---"+response, Toast.LENGTH_SHORT).show();


                                        if(response.code() == 200){

                                            fiscalDatas.remove(fiscalDatas.get(position));
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





                Toast.makeText(mContext, "click delete---"+fiscalDatas.get(position).getId_dato(), Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentEditFact = new Intent(mContext, EditFacturacionActivity.class);

                intentEditFact.putExtra("id_dato", fiscalDatas.get(position).getId_dato());
                mContext.startActivity(intentEditFact);


                return true;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });







        return v;





    }

    @Override
    public void fillValues(int position, View convertView) {


        FiscalData fiscalData = fiscalDatas.get(position);


        TextView txtNombreRazonSocial = (TextView) convertView.findViewById(R.id.txt_nombre_razon_social);
        TextView txtRfc = (TextView) convertView.findViewById(R.id.txt_rfc);
        TextView txtCalle = (TextView) convertView.findViewById(R.id.txt_calle);
        TextView txtNumExt = (TextView) convertView.findViewById(R.id.txt_num_ext);
        TextView txtNumInterior = (TextView) convertView.findViewById(R.id.txt_num_interior);
        TextView txtColonia = (TextView) convertView.findViewById(R.id.txt_colonia);
        TextView txtDelegacion = (TextView) convertView.findViewById(R.id.txt_delegacion);
        TextView txtEstado = (TextView) convertView.findViewById(R.id.txt_estado);
        TextView txtPais = (TextView) convertView.findViewById(R.id.txt_pais);
        TextView txtMoralFisica = (TextView) convertView.findViewById(R.id.txt_moral_fisica);
        TextView txtCp = (TextView) convertView.findViewById(R.id.txt_cp);


        if(fiscalData.getTipo() == 1){

            txtMoralFisica.setText("Moral ");

        } else if (fiscalData.getTipo() == 2){
            txtMoralFisica.setText("Fiscal");
        }


        txtNombreRazonSocial.setText(fiscalData.getRazon_social());
        txtRfc.setText(fiscalData.getRfc());
        txtCalle.setText(fiscalData.getCalle());
        txtNumExt.setText(fiscalData.getNroext());
        txtNumInterior.setText(fiscalData.getNroint());
        txtColonia.setText(fiscalData.getColonia());
        txtDelegacion.setText(fiscalData.getDelegacion());
        txtEstado.setText(fiscalData.getEstado());
        txtPais.setText(fiscalData.getPais());
        txtCp.setText(fiscalData.getCp());

    }

    @Override
    public int getCount() {
        return fiscalDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return fiscalDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return fiscalDatas.get(position).getId_dato();
    }
}



/*

    FiscalData fiscalData = getItem(position);

        if(convertView == null ){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_fiscal_data, parent, false);
                }

                TextView txtNombreRazonSocial = (TextView) convertView.findViewById(R.id.txt_nombre_razon_social);
                TextView txtRfc = (TextView) convertView.findViewById(R.id.txt_rfc);
                TextView txtCalle = (TextView) convertView.findViewById(R.id.txt_calle);
                TextView txtNumExt = (TextView) convertView.findViewById(R.id.txt_num_ext);
                TextView txtNumInterior = (TextView) convertView.findViewById(R.id.txt_num_interior);
                TextView txtColonia = (TextView) convertView.findViewById(R.id.txt_colonia);
                TextView txtDelegacion = (TextView) convertView.findViewById(R.id.txt_delegacion);
                TextView txtEstado = (TextView) convertView.findViewById(R.id.txt_estado);
                TextView txtPais = (TextView) convertView.findViewById(R.id.txt_pais);
                TextView txtMoralFisica = (TextView) convertView.findViewById(R.id.txt_moral_fisica);
                TextView txtCp = (TextView) convertView.findViewById(R.id.txt_cp);


                if(fiscalData.getTipo() == 1){

                txtMoralFisica.setText("Moral ");

                } else if (fiscalData.getTipo() == 2){
                txtMoralFisica.setText("Fiscal");
                }


                txtNombreRazonSocial.setText(fiscalData.getRazon_social());
                txtRfc.setText(fiscalData.getRfc());
                txtCalle.setText(fiscalData.getCalle());
                txtNumExt.setText(fiscalData.getNroext());
                txtNumInterior.setText(fiscalData.getNroint());
                txtColonia.setText(fiscalData.getColonia());
                txtDelegacion.setText(fiscalData.getDelegacion());
                txtEstado.setText(fiscalData.getEstado());
                txtPais.setText(fiscalData.getPais());
                txtCp.setText(fiscalData.getCp());



                return convertView;*/
