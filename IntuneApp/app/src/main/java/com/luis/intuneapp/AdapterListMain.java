package com.luis.intuneapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.luis.intuneapp.basededatos.ProductoVO;

import java.util.ArrayList;

public class AdapterListMain extends BaseAdapter {
    private Context context;
    private int referenceList;
    ArrayList<Integer> pc= new ArrayList<>();
    ArrayList<ProductoVO> list = new ArrayList<>();
    public AdapterListMain(Context context, int reference, ArrayList<ProductoVO> list, ArrayList<Integer> pc ){
        this.context = context;
        this.referenceList = reference;
        this.list = list;
        this.pc = pc;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //Editando los botones
        TextView textViewCantidad;
        Button buttonAtras, buttonAdelante;


        v = layoutInflater.inflate(R.layout.recyclerview_item, null);

        buttonAtras = v.findViewById(R.id.btnRestarCantidad);
        buttonAdelante = v.findViewById(R.id.btnSumarCantidad);
        textViewCantidad = v.findViewById(R.id.txtSumarRestarCantidad);

        textViewCantidad.setText(String.valueOf(pc.get(position)));

        TextView nombre, cantidad, precio;
        nombre = v.findViewById(R.id.txtNombreProductoVer);
        cantidad = v.findViewById(R.id.txtCantidadProductoVer);
        precio = v.findViewById(R.id.txtPrecioProductoVer);  //12/10

        nombre.setText(String.valueOf(list.get(position).getDescripcionProducto()));
        cantidad.setText(String.valueOf(list.get(position).getCantidadProducto()));
        precio.setText(String.valueOf(list.get(position).getPrecioProducto()));

        buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer cantidadProductos = pc.get(position);
                if(!((cantidadProductos-1) < 0)){
                    cantidadProductos = cantidadProductos-1;
                    textViewCantidad.setText(String.valueOf(cantidadProductos));
                    pc.set(position, cantidadProductos);
                }
            }
        });

        buttonAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer cantidadProductos = pc.get(position);
                //Integer cantidadProductos = Integer.parseInt(textViewCantidad.getText().toString());
                if(!((cantidadProductos+1) > list.get(position).getCantidadProducto())){
                    cantidadProductos += 1;
                    textViewCantidad.setText(String.valueOf(cantidadProductos));
                    pc.set(position, cantidadProductos);
                }
            }
        });

        return v;
    }
}
