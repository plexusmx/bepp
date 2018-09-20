package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;

import bepp.com.bepp.R;
import bepp.com.bepp.services.UserService;
import bepp.com.bepp.sessionManager.UserSessionManager;

public class EditEmailActivity extends AppCompatActivity {

    private ImageView cancelar;
    private EditText editTextUser;
    private TextInputLayout textInputLayoutUser;
    private UserService mAPIService;
    private Vibrator vib;
    Animation animShake;
    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);
        Toolbar principal = (Toolbar) findViewById(R.id.toolbar_edit_email);
        principal.setTitle("");
        setSupportActionBar(principal);
        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        bindUI();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelarEditIntent = new Intent(EditEmailActivity.this, ConfigurationActivity.class);
                startActivity(cancelarEditIntent);
            }
        });



        if(session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        // String name = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);


        editTextUser.setText(email);

    }



    private void bindUI() {

        // ImageView
        cancelar = (ImageView)findViewById(R.id.imagecancelar);

        // EditText
        editTextUser = (EditText) findViewById(R.id.text_user);

        // TextInputLayout
        textInputLayoutUser = (TextInputLayout)findViewById(R.id.layout_user);

        // Others
        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }
}
