package bepp.com.bepp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import bepp.com.bepp.R;

/**
 * Created by charlie on 31/10/17.
 */

public class TapFacturas extends Fragment {

    private static  final String TAG = "TapFacturas";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.layout_facturas, container, false);

        return view;

    }

}
