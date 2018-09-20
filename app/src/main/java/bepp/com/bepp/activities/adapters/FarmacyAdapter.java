package bepp.com.bepp.activities.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import bepp.com.bepp.R;
import bepp.com.bepp.activities.DetalleCompraActivity;
import bepp.com.bepp.activities.DetalleCompraNoFacturaActivity;
import bepp.com.bepp.models.DetailPackage;
import bepp.com.bepp.models.Farmacy;
import bepp.com.bepp.models.MedicamentPrescription;
import bepp.com.bepp.models.PackageSend;

public class FarmacyAdapter extends BaseSwipeAdapter {

    public AlertDialog.Builder registerMesaje;
    private Context mContext;
    private TextView numCantidad, numCantidad2;
    List<PackageSend> packageSendList;
    List<Farmacy> farmacyList;
    static List<DetailPackage> packageList;
    String nombreMed = "";

    private MedicamentosFarmaciasAdapter mAdapterMed;



    private Button btnMenos, btnMas,btnFacturaSi, btnFacturaNo;
    private TextView ver_oferta_generales ,text_cantidades_medicamentos,txtclose;
    private ExpandableLayout expandableLayout0, expandableLayoutEstudios;
    private ListView mListView, mListViewMed;
    private int id_receta;
    public LinearLayout linear_productos_compra, linearLayoutMedicamentos, linearLayoutEstudios;

    Dialog myDialog;

    private List<DetailPackage> detailPackageList;
    private ListView mListViewMedicamentos;
    private MedicamentoRecetaPopAdapter mAdapterMedical;


    public FarmacyAdapter(Context mContext, List<PackageSend> packageSendList, int id_receta) {
        this.mContext = mContext;
        this.packageSendList = packageSendList;
        this.id_receta = id_receta;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_farmacia;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {


        View v = LayoutInflater.from(mContext).inflate(R.layout.item_farmacias_list, null);


        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));


        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {




        farmacyList = packageSendList.get(0).getFarmacy();
        detailPackageList = packageSendList.get(0).getFarmacy().get(position).getDetailPackageList();
        packageList = packageSendList.get(0).getFarmacy().get(0).getDetailPackageList();
       nombreMed = packageSendList.get(0).getFarmacy().get(0).getDetailPackageList().get(0).getNombre_comercial();



        myDialog = new Dialog(mContext);
        mListViewMed = (ListView) convertView.findViewById(R.id.list_farmacia_medicamentos);
        ver_oferta_generales = (TextView) convertView.findViewById(R.id.ver_oferta_generales);
        text_cantidades_medicamentos = (TextView) convertView.findViewById(R.id.text_cantidades_medicamentos);
        expandableLayout0 = (ExpandableLayout) convertView.findViewById(R.id.expandable_layout_0);
        linear_productos_compra = (LinearLayout) convertView.findViewById(R.id.productos_compra);

        TextView text_medicamento = (TextView) convertView.findViewById(R.id.text_medicamento);

        TextView text_cantidad_comp = (TextView) convertView.findViewById(R.id.text_cantidad_comp);
        TextView text_cantidad_teni = (TextView) convertView.findViewById(R.id.text_cantidad_teni);

        TextView text_precio_medi = (TextView) convertView.findViewById(R.id.text_precio);

        TextView text_nombre_farmacia = (TextView) convertView.findViewById(R.id.text_nombre_farmacia);

        text_cantidad_comp.setText(packageSendList.get(0).getFarmacy().get(0).getCantidad_medicamentos());
        text_cantidad_teni.setText(packageSendList.get(0).getMedicamentos_disponibles());

        text_nombre_farmacia.setText(packageSendList.get(0).getFarmacy().get(0).getNombre_cadena());
        text_precio_medi.setText(packageSendList.get(0).getFarmacy().get(0).getSubtotalpedido());




        mAdapterMed = new MedicamentosFarmaciasAdapter(mContext, packageSendList.get(0).getFarmacy().get(0).getDetailPackageList() );
        mListViewMed.setAdapter(mAdapterMed);

      //  mAdapterMed = new MedicamentosFarmaciasAdapter( mContext, packageSendList.get(0).getFarmacy().get(0).getDetailPackageList());
        //mListViewMed.setAdapter(mAdapterMed);
        //mAdapterMed.setMode(Attributes.Mode.Single);



        linear_productos_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (expandableLayout0.isExpanded()) {
                    expandableLayout0.collapse();

                } else {
                    expandableLayout0.expand();
                }

            }
        });


        text_cantidades_medicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
            }
        });


        ver_oferta_generales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupFactura(v);

            }
        });

    }

    @Override
    public int getCount() {
        return packageSendList.size();
    }

    @Override
    public Object getItem(int position) {
        return packageSendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return packageSendList.get(position).getId_pedido();
    }


    public void ShowPopup(View v) {

        TextView text_nombre_farmacia2 = (TextView) myDialog.findViewById(R.id.text_cantidad);
        myDialog.setContentView(R.layout.layout_cantidad_medicamentos);
        txtclose = (TextView) myDialog.findViewById(R.id.close_pop);
        btnMenos = (Button) myDialog.findViewById(R.id.button_menos);
        btnMas = (Button) myDialog.findViewById(R.id.button_mas);

        mListView = (ListView) myDialog.findViewById(R.id.list_pop_recetas_medicinas);


        mAdapterMedical = new MedicamentoRecetaPopAdapter(mContext, detailPackageList);
        mListView.setAdapter(mAdapterMedical);
        mAdapterMedical.setMode(Attributes.Mode.Single);




       /* btnMenos2 = (Button) myDialog.findViewById(R.id.button_menos2);
        btnMas2 = (Button) myDialog.findViewById(R.id.button_mas2);
        numCantidad2 = (TextView) myDialog.findViewById(R.id.text_cantidad2);*/

        txtclose.setText("Cerrar");



  /*       btnMenos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int cantidad = Integer.parseInt(numCantidad2.getText().toString().trim());
                if(cantidad > 1){
                    cantidad--;
                    numCantidad2.setText(String.valueOf(cantidad));


                }

            }
        });



        btnMas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidad = Integer.parseInt(numCantidad2.getText().toString().trim());
                if(cantidad <  2   ){
                    cantidad++;
                    numCantidad2.setText(String.valueOf(cantidad));


                }


            }
        });*/


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }



    public void showPopupFactura (View v) {

        myDialog.setContentView(R.layout.pop_up_facturacion);
        btnFacturaNo = (Button) myDialog.findViewById(R.id.btn_factura_no);
        btnFacturaSi = (Button) myDialog.findViewById(R.id.btn_factura_si);

        btnFacturaNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnFacturaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent recetasIntent = new Intent(mContext, DetalleCompraActivity.class);
                recetasIntent.putExtra("id_receta", id_receta);
                mContext.startActivity(recetasIntent);

            }
        });

        btnFacturaNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent recetasIntent = new Intent(mContext, DetalleCompraNoFacturaActivity.class);
                recetasIntent.putExtra("id_receta", id_receta);
                mContext.startActivity(recetasIntent);

            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}