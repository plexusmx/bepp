package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.CardAdapter;
import bepp.com.bepp.activities.adapters.ListViewAdapter;
import bepp.com.bepp.activities.adapters.PaymentCardAdapter;
import bepp.com.bepp.models.PaymentCard;
import bepp.com.bepp.openpay.Openpay;
import bepp.com.bepp.openpay.model.Card;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class MetodoPagoMainActivity extends AppCompatActivity {

    private Button btnNvaMtd_pago;
    private Vibrator vib;
    Animation animShake;
    private String sendToken;
    // User Session Manager Class
    UserSessionManager session;
    private ListView mListView;
    private Context mContext = this;

    private List<PaymentCard> tarjetas;
    private ArrayList<String> arrayList;
    private ArrayAdapter<PaymentCard> adapter;
    private CardAdapter mAdapter;
    private EditText txtInput;
    private TextView textNombreUser, txtTipo, txtNroTarjeta,txtMes,txtAno;
    private Openpay api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_pago_main);



        /**
         * The following comment is the sample usage of ArraySwipeAdapter.
         */
//        String[] adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
//                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
//                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
//                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient"};
//        mListView.setAdapter(new ArraySwipeAdapterSample<String>(this, R.layout.listview_item, R.id.position, adapterData));


        mListView = (ListView) findViewById(R.id.listview);












        // Add item to adapter
        // PaymentCard newUser = new PaymentCard();
        // adapter.add(newUser);
        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
        //JSONArray jsonArray = ...;
        //ArrayList<PaymentCard> newUsers = PaymentCard.fromJson(jsonArray)
        //adapter.addAll(newUsers);

        session = new UserSessionManager(getApplicationContext());






        // Session class instance


        btnNvaMtd_pago = (Button) findViewById(R.id.button_nva_mtd_pago);


        btnNvaMtd_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nvaMtdPagoIntent = new Intent(MetodoPagoMainActivity.this, AgregarTarjetaActivity.class);
                startActivity(nvaMtdPagoIntent);
            }
        });



        //obtenerTarjetas();

       /* String[] items = {"Casa", "CasaTia", "Local1"};


        arrayList = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txt_nombre, arrayList);
        listView.setAdapter(adapter);*/



       // Click on listItem
/*        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
            }
        });*/
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
                Toast.makeText(mContext, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
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

        //api = new Openpay("myryzrchbkxoilei0rvs", "sk_88bc8020cfa6449e8f74a7b07a5ddfdd", false);


       // List<Card> cards = api.cards().list("a9pvykxz4g5rg0fplze0", request);


        USER_CLIENT_RETROFIT.getPaymentCards().enqueue(new Callback<List<PaymentCard>>() {

            @Override
            public void onResponse(Call<List<PaymentCard>> call, Response<List<PaymentCard>> response) {
                Log.i("Bepp", "card response ::"+response.code());
                if(response.code() == 200){

                    tarjetas =  response.body();
                    for (PaymentCard card: tarjetas
                         ) {
                        Log.i("BEPP", "tarjeta-->"+card.toString());
                    }
                    //actualizarListaTarjetas();

                    mAdapter = new CardAdapter(MetodoPagoMainActivity.this, tarjetas);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if(response.code() == 500){
                    Toast.makeText(MetodoPagoMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(MetodoPagoMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<PaymentCard>> call, Throwable t) {
                Log.e("Bepp",t.getMessage(),t);
            }
        });

    }




       /* private void obtenerTarjetas(){
            Log.i("Bepp", "entrando a obtener ::"+HttpClient.getToken());

            USER_CLIENT_RETROFIT.getPaymentCards().enqueue(new Callback<List<PaymentCard>>() {

            @Override
            public void onResponse(Call<List<PaymentCard>> call, Response<List<PaymentCard>> response) {
                Log.i("Bepp", "card response ::"+response.code());
                if(response.code() == 200){

                   tarjetas =  response.body();
                    actualizarListaTarjetas();

                } else if(response.code() == 500){
                    Toast.makeText(MetodoPagoMainActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                }else if(response.code() == 403){


                    Toast.makeText(MetodoPagoMainActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<PaymentCard>> call, Throwable t) {
                Log.e("Bepp",t.getMessage(),t);
            }
        });

        }*/

       /* private void actualizarListaTarjetas(){
            PaymentCardAdapter adapter = new PaymentCardAdapter(this, tarjetas);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.list_tarjeta);
            listView.setAdapter(adapter);

        }*/











}

