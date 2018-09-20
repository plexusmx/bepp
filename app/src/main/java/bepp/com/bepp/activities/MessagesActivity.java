package bepp.com.bepp.activities;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.MessagesAdapter;
import bepp.com.bepp.models.Message;
import bepp.com.bepp.models.ResponseList;
import bepp.com.bepp.sessionManager.UserSessionManager;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bepp.com.bepp.services.UserService.USER_CLIENT_RETROFIT;

public class MessagesActivity extends AppCompatActivity {


    private Vibrator vib;
    Animation animShake;
    private String sendToken;
    // User Session Manager Class
    UserSessionManager session;
    private ListView mListView;
    private MessagesAdapter mAdapter;
    private Context mContext = this;

    private List<Message> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        session = new UserSessionManager(getApplicationContext());
        mListView = (ListView) findViewById(R.id.list_mensajes);


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


        USER_CLIENT_RETROFIT.getMessages().enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                if (response.code() == 200) {
                    messages =  response.body();
                    for (Message message: messages
                            ) {
                        Log.i("BEPP", "tarjeta-->"+message.toString());
                    }
                    //actualizarListaTarjetas();

                    mAdapter = new MessagesAdapter(MessagesActivity.this, messages);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                } else if (response.code() == 500) {
                    Toast.makeText(MessagesActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                } else if (response.code() == 403) {


                    Toast.makeText(MessagesActivity.this, "La sesión ha expirado", Toast.LENGTH_LONG).show();

                }
            }


            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("Bepp", t.getMessage(), t);

            }
        });
    }

}
