package bepp.com.bepp.activities.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import bepp.com.bepp.activities.AmigoFamiliaActivity;
import bepp.com.bepp.models.Familiar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;


public class FamiliresAdapter extends BaseSwipeAdapter {


    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    List<Familiar> familiares;
    private ListView mListView;


    public FamiliresAdapter(Context mContext, List<Familiar> familiares ,ListView mListView){
        this.mContext = mContext;
        this.familiares = familiares;
        this.mListView = mListView;

    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_familiar;
    }


    @Override
    public View generateView(final int position, ViewGroup parent) {

        registerMesaje = new AlertDialog.Builder(mContext);

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_familiar, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));

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

                registerMesaje.setTitle("Borrar familiar");
                registerMesaje.setMessage("¿Desea eliminar familiar?");
                registerMesaje.setIcon(R.mipmap.logo_bepp_blank);
                registerMesaje.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                registerMesaje.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                USER_CLIENT_RETROFIT.deletefamiliar(familiares.get(position).getId_familiar()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(mContext, "response---"+response, Toast.LENGTH_SHORT).show();


                                        if(response.code() == 200){

                                            familiares.remove(familiares.get(position));
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





                Toast.makeText(mContext, "click delete---"+familiares.get(position).getId_familiar(), Toast.LENGTH_SHORT).show();
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
                Intent intentEditFact = new Intent(mContext, AmigoFamiliaActivity.class);

                intentEditFact.putExtra("id_familiar", familiares.get(position).getId_familiar());
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
    public void fillValues(int position, final View convertView) {

        final Familiar familiar = familiares.get(position);

       TextView txtParentesco = (TextView) convertView.findViewById(R.id.parentesco_familiar);
        TextView txtNombre = (TextView) convertView.findViewById(R.id.nombre_familiar);

        txtNombre.setText(familiar.getNombre());
        txtParentesco.setText(familiar.getParentesco());


    }

    @Override
    public int getCount() {
        return familiares.size();
    }

    @Override
    public Object getItem(int position) {
        return familiares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return familiares.get(position).getId_familiar();
    }
}

