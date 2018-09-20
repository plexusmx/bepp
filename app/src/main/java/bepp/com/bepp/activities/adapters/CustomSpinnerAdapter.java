package bepp.com.bepp.activities.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.DirectorioMedicoActivity;
import bepp.com.bepp.activities.MainActivity;

/**
 * Created by charlie on 07/12/17.
 */

public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private ArrayList<String> asr;

    public CustomSpinnerAdapter(Context context,ArrayList<String> asr) {
        this.asr=asr;
        activity = context;
    }



    public int getCount()
    {

        int count = getCount();

        return count>0 ? count-1 : count ;
    }


    public Object getItem(int i)
    {
        return asr.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return  null;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {

        return  null;
    }

}