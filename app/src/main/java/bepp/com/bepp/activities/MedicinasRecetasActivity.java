package bepp.com.bepp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.adapters.SectionsPageAdapter;

public class MedicinasRecetasActivity extends AppCompatActivity {


    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Context mContext = this;
    private TextView verOfertaGenerico, verOfertaGenerales;
    int id_receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinas_recetas);


        id_receta = getIntent().getExtras().getInt("id_receta");
        Log.i("id_receta", "4: "+id_receta);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TapGenericos(), "EMITIDA POR TU MEDICO");

        adapter.addFragment(new TapGenerales(), "OPCION DE GENERICOS");
        viewPager.setAdapter(adapter);
    }



}
