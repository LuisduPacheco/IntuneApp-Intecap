package com.luis.intuneapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luis.intuneapp.basededatos.ProductoVO;

import java.util.ArrayList;

public class AdaptadorRecyclerView extends RecyclerView.Adapter<AdaptadorRecyclerView.ViewHolder>{
    private ArrayList<ProductoVO> productoVOS = new ArrayList<>();

    public AdaptadorRecyclerView(ArrayList<ProductoVO> productoVOS){
        this.productoVOS = productoVOS;
    }

    @Override
    public AdaptadorRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdaptadorRecyclerView.ViewHolder holder, int position) {
        holder.asignarDatos(productoVOS.get(position).getDescripcionProducto(),
                String.valueOf(productoVOS.get(position).getCantidadProducto()));
    }

    @Override
    public int getItemCount() {
        return this.productoVOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombreProducto, textViewCantidadProducto;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewNombreProducto = itemView.findViewById(R.id.txtCantidadProductoVer);
            textViewCantidadProducto = itemView.findViewById(R.id.txtCantidadProductoVer);
        }
        private void asignarDatos(String nombreProducto, String cantidadProducto){
            textViewNombreProducto.setText(String.valueOf(nombreProducto));
            textViewCantidadProducto.setText(String.valueOf(cantidadProducto));
        }
    }
}
